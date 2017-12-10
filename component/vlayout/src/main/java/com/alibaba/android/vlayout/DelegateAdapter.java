/*
 * MIT License
 *
 * Copyright (c) 2016 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.alibaba.android.vlayout;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.support.v7.widget.RecyclerView.NO_ID;

/**
 * Adapter delegates its responsibility to sub adapters
 * 对子Adapter进行了统一管理，一种负责的装饰器
 * 正常Adapter的模板方法，都会对应执行到子adapters的对应方法
 * @author villadora
 * @since 1.0.0
 */
public class DelegateAdapter extends VirtualLayoutAdapter<RecyclerView.ViewHolder> {

    //索引，子Adapter在整个Adapter集合的索引，也是mIndexAry的key值，线程安全
    @Nullable
    private AtomicInteger mIndexGen;

    //索引，子Adapter在整个Adapter集合的索引，也是mIndexAry的key值，线程不安全的
    private int mIndex = 0;

    //是否只有一致的ItemType，影响到本类中ItemType的规则
    //如果 每种子Adapter返回的ItemType都是一样的时，可以将此设置为true，这样可以通过ItemType作为Adapter的索引key存储
    //如果不一样，比如说其中有一个子Adapter可能会显示多种布局，这时候应该为false
    //为了保证布局和数据一一对应，也就是支持多类型混合显示的方案，一般使用默认的false即可
    private final boolean mHasConsistItemType;

    //以ItemType为key存储所有的Adapter
    private SparseArray<Adapter> mItemTypeAry = new SparseArray<>();

    //Pair<AdapterDataObserver, Adapter>一体的集合
    @NonNull
    protected final List<Pair<AdapterDataObserver, Adapter>> mAdapters = new ArrayList<>();

    //各种adapter所持的总数据和
    private int mTotal = 0;

    //以索引为key 存储Pair<AdapterDataObserver, Adapter>
    private final SparseArray<Pair<AdapterDataObserver, Adapter>> mIndexAry = new SparseArray<>();

    /**
     * Delegate Adapter merge multi sub adapters, default is thread-unsafe
     *
     * @param layoutManager layoutManager
     */
    public DelegateAdapter(VirtualLayoutManager layoutManager) {
        this(layoutManager, false, false);
    }

    /**
     * @param layoutManager      layoutManager
     * @param hasConsistItemType whether sub adapters itemTypes are consistent
     */
    public DelegateAdapter(VirtualLayoutManager layoutManager, boolean hasConsistItemType) {
        this(layoutManager, hasConsistItemType, false);
    }

    /**
     * @param layoutManager      layoutManager
     *
     * @param hasConsistItemType whether sub adapters itemTypes are consistent
     *                           子适配器itemtype是否一致
     *
     * @param threadSafe         tell whether your adapter is thread-safe or not
     *                           是否线程安全
     */
    DelegateAdapter(VirtualLayoutManager layoutManager, boolean hasConsistItemType, boolean threadSafe) {
        super(layoutManager);
        //如果线程安全会创建 线程安全的索引自增对象
        if (threadSafe) {
            mIndexGen = new AtomicInteger(0);
        }

        mHasConsistItemType = hasConsistItemType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //如果是一致类型，则从mItemTypeAry中获取真实的子Adapter，并调用其onCreateViewHolder
        if (mHasConsistItemType) {
            Adapter adapter = mItemTypeAry.get(viewType);
            if (adapter != null) {
                 return adapter.onCreateViewHolder(parent, viewType);
            }

            return null;
        }

        // 如果不是一致的，也就是类型经过了托尔运算
        // 反向托尔函数，用来来解析出原来的真实的itemType和position
        long w = (int) (Math.floor(Math.sqrt(8.0 * viewType + 1) - 1) / 2);
        long t = (w * w + w) / 2;

        //得到索引
        int index = (int) (viewType - t);
        //得到ItemType
        int subItemType = (int) (w - index);
        //根据索引得到Adapter
        Adapter adapter  = findAdapterByIndex(index);
        if (adapter == null) {
            return null;
        }

        return adapter.onCreateViewHolder(parent, subItemType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Pair<AdapterDataObserver, Adapter> pair = findAdapterByPosition(position);
        if (pair == null) {
            return;
        }
        //使得子Adapter中只关注自己内部的数据序列即可
        pair.second.onBindViewHolder(holder, position - pair.first.mStartPosition);
        pair.second.onBindViewHolderWithOffset(holder, position - pair.first.mStartPosition, position);
    }

    @Override
    public int getItemCount() {
        return mTotal;
    }

    /**
     * Big integer of itemType returned by delegated adapter may lead to failed
     *
     * @param position item position
     * @return integer represent item view type
     */
    @Override
    public int getItemViewType(int position) {
        Pair<AdapterDataObserver, Adapter> p = findAdapterByPosition(position);
        if (p == null) {
            return RecyclerView.INVALID_TYPE;
        }
        //使得子Adapter中只关注自己内部的数据序列即可
        int subItemType = p.second.getItemViewType(position - p.first.mStartPosition);

        if (subItemType < 0) {
            // negative integer, invalid, just return
            return subItemType;
        }
        //如果类型一致，也就是说子类的ItemType都是一样的那么可以将Type作为Adapter的一种索引Key存储
        if (mHasConsistItemType) {
            mItemTypeAry.put(subItemType, p.second);
            return subItemType;
        }


        int index = p.first.mIndex;

        //真实的itemType和Adapter索引进行托尔运算后作为DelegateAdapter的ItemType标示
        return (int) getCantor(subItemType, index);
    }


    @Override
    public long getItemId(int position) {
        Pair<AdapterDataObserver, Adapter> p = findAdapterByPosition(position);

        if (p == null) {
            return NO_ID;
        }

        //使得子Adapter中只关注自己内部的数据序列即可
        long itemId = p.second.getItemId(position - p.first.mStartPosition);

        if (itemId < 0) {
            return NO_ID;
        }

        int index = p.first.mIndex;
        /*
         * Now we have a pairing function problem, we use cantor pairing function for itemId.
         * 现在我们有一个配对函数问题,我们使用托尔配对函数。
         * 我们外部得到的真正的ItemId将不再单纯的是子Adapter的itemId
         */
        return getCantor(index, itemId);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        // do nothing
        //覆盖这个方法，使外部调用无效
    }

    //调用对应的子Adapter所对应对应该方法
    @SuppressWarnings("unchecked")
    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        int position = holder.getPosition();
        if (position > 0) {
            Pair<AdapterDataObserver, Adapter> pair = findAdapterByPosition(position);
            if (pair != null) {
                pair.second.onViewRecycled(holder);
            }
        }
    }


    //调用对应的子Adapter所对应对应该方法
    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getPosition();
        if (position > 0) {
            Pair<AdapterDataObserver, Adapter> pair = findAdapterByPosition(position);
            if (pair != null) {
                pair.second.onViewAttachedToWindow(holder);
            }
        }
    }

    //调用对应的子Adapter所对应对应该方法
    @SuppressWarnings("unchecked")
    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        int position = holder.getPosition();
        if (position > 0) {
            Pair<AdapterDataObserver, Adapter> pair = findAdapterByPosition(position);
            if (pair != null) {
                pair.second.onViewDetachedFromWindow(holder);
            }
        }
    }


    /**
     * You can not set layoutHelpers to delegate adapter
     * 通过这个这个方法不能手工的在外部调用
     */
    @Deprecated
    @Override
    public void setLayoutHelpers(List<LayoutHelper> helpers) {
        throw new UnsupportedOperationException("DelegateAdapter doesn't support setLayoutHelpers directly");
    }


    /**
     * 设置子适配器
     * @param adapters
     */
    public void setAdapters(@Nullable List<Adapter> adapters) {
        //清空
        clear();

        //创建空集合
        if (adapters == null) {
            adapters = Collections.emptyList();
        }

        //创建LayoutHelper集合
        List<LayoutHelper> helpers = new LinkedList<>();

        //有稳定的Id序列
        //表明数据集合中的每一项是否可以代表一个惟一的标识符的类型。
        boolean hasStableIds = true;
        mTotal = 0;

        Pair<AdapterDataObserver, Adapter> pair;
        for (Adapter adapter : adapters) {
            // every adapter has an unique index id
            // 每个适配器都有一个惟一的索引id，注意这个索引Id不一定就是Adapter序列中的位置
            AdapterDataObserver observer = new AdapterDataObserver(mTotal, mIndexGen == null ? mIndex++ : mIndexGen.incrementAndGet());
            adapter.registerAdapterDataObserver(observer);
            hasStableIds = hasStableIds && adapter.hasStableIds();
            LayoutHelper helper = adapter.onCreateLayoutHelper();

            helper.setItemCount(adapter.getItemCount());
            mTotal += helper.getItemCount();
            helpers.add(helper);
            pair = Pair.create(observer, adapter);
            //索引集合添加
            mIndexAry.put(observer.mIndex, pair);
            //List添加
            mAdapters.add(pair);
        }
        //如果DelegateAdapter没有Observers
        if (!hasObservers()) {
            super.setHasStableIds(hasStableIds);
        }
        super.setLayoutHelpers(helpers);
    }

    /**
     * Add adapters in <code>position</code>
     * 指定位置添加适配器
     * 本质是重新修改适配器集合
     * @param position the index where adapters added
     * @param adapters adapters
     */
    public void addAdapters(int position, @Nullable List<Adapter> adapters) {
        if (adapters == null || adapters.size() == 0) {
            return;
        }
        if (position < 0) {
            position = 0;
        }

        if (position > mAdapters.size()) {
            position = mAdapters.size();
        }

        List<Adapter> newAdapter = new ArrayList<>();
        Iterator<Pair<AdapterDataObserver, Adapter>> itr = mAdapters.iterator();
        while (itr.hasNext()) {
            Pair<AdapterDataObserver, Adapter> pair = itr.next();
            Adapter theOrigin = pair.second;
            newAdapter.add(theOrigin);
        }
        for (Adapter adapter : adapters) {
            newAdapter.add(position, adapter);
            position++;
        }
        setAdapters(newAdapter);
    }

    /**
     * Append adapters to the end
     * 向后添加适配器集合
     * @param adapters adapters will be appended
     */
    public void addAdapters(@Nullable List<Adapter> adapters) {
        addAdapters(mAdapters.size(), adapters);
    }

    /**
     * 指定位置添加一个适配器
     * @param position
     * @param adapter
     */
    public void addAdapter(int position, @Nullable Adapter adapter) {
        addAdapters(position, Collections.singletonList(adapter));
    }

    /**
     * 添加一个集合到后面
     * @param adapter
     */
    public void addAdapter(@Nullable Adapter adapter) {
        addAdapters(Collections.singletonList(adapter));
    }

    /**
     * 移除第一个adapter
     */
    public void removeFirstAdapter() {
        if (mAdapters != null && !mAdapters.isEmpty()) {
            Adapter targetAdatper = mAdapters.get(0).second;
            removeAdapter(targetAdatper);
        }
    }

    /**
     * 移除最后一个Adapter
     */
    public void removeLastAdapter() {
        if (mAdapters != null && !mAdapters.isEmpty()) {
            Adapter targetAdatper = mAdapters.get(mAdapters.size() - 1).second;
            removeAdapter(targetAdatper);
        }
    }

    /**
     * 移除某个位置的Adapter
     * @param adapterIndex
     */
    public void removeAdapter(int adapterIndex) {
        if (adapterIndex >= 0 && adapterIndex < mAdapters.size()) {
            Adapter targetAdatper = mAdapters.get(adapterIndex).second;
            removeAdapter(targetAdatper);
        }
    }

    /**
     * 移除目标Adapter
     * @param targetAdapter
     */
    public void removeAdapter(@Nullable Adapter targetAdapter) {
        if (targetAdapter == null) {
            return;
        }
        removeAdapters(Collections.singletonList(targetAdapter));
    }

    /**
     * 移除某些Adapter
     * @param targetAdapters
     */
    public void removeAdapters(@Nullable List<Adapter> targetAdapters) {
        if (targetAdapters == null || targetAdapters.isEmpty()) {
            return;
        }
        //得到原来的Helpers
        List<LayoutHelper> helpers = new LinkedList<>(super.getLayoutHelpers());
        for (int i = 0, size = targetAdapters.size(); i < size; i++) {
            Adapter one = targetAdapters.get(i);
            //原始Adapter的迭代器
            Iterator<Pair<AdapterDataObserver, Adapter>> itr = mAdapters.iterator();
            while (itr.hasNext()) {
                Pair<AdapterDataObserver, Adapter> pair = itr.next();
                Adapter theOther = pair.second;
                //匹配到要移除的Adapter
                if (theOther.equals(one)) {
                    //解掉观察器的注册
                    theOther.unregisterAdapterDataObserver(pair.first);
                    //根据索引得到对应的位置，Helper位置与Adapter位置完全对应的
                    final int position = findAdapterPositionByIndex(pair.first.mIndex);
                    if (position >= 0 && position < helpers.size()) {
                        //移除Helper
                        helpers.remove(position);
                    }
                    //移除Adapter
                    itr.remove();
                    break;
                }
            }
        }

        List<Adapter> newAdapter = new ArrayList<>();
        //移除后的最新Adapter集合
        Iterator<Pair<AdapterDataObserver, Adapter>> itr = mAdapters.iterator();
        while (itr.hasNext()) {
            newAdapter.add(itr.next().second);
        }
        //重新设置
        setAdapters(newAdapter);
    }

    /**
     * 清楚所有
     */
    public void clear() {
        mTotal = 0;
        mIndex = 0;
        if (mIndexGen != null) {
            mIndexGen.set(0);
        }
        mLayoutManager.setLayoutHelpers(null);

        //取消Observer注册
        for (Pair<AdapterDataObserver, Adapter> p : mAdapters) {
            p.second.unregisterAdapterDataObserver(p.first);
        }


        mItemTypeAry.clear();
        mAdapters.clear();
        mIndexAry.clear();
    }

    /**
     * 得到所有的Adapter数量
     * @return
     */
    public int getAdaptersCount(){
        return mAdapters == null ? 0 : mAdapters.size();
    }

    /**
     *
     * @param absoultePosition
     * @return
     *  the relative position in sub adapter by the absoulte position in DelegaterAdapter. Return -1 if no sub adapter founded.
     *  通过整个列表中列表项的绝对位置，得到这个位置在子Adapter中的相对位置
     */
    public int findOffsetPosition(int absoultePosition) {
        Pair<AdapterDataObserver, Adapter> p = findAdapterByPosition(absoultePosition);
        if (p == null) {
            return -1;
        }
        int subAdapterPosition = absoultePosition - p.first.mStartPosition;
        return subAdapterPosition;
    }

    /**
     * 通过列表Item所在的真实位置，得到其对应的Pair<AdapterDataObserver, Adapter>
     * @param position
     * @return
     */
    @Nullable
    public Pair<AdapterDataObserver, Adapter> findAdapterByPosition(int position) {
        final int count = mAdapters.size();
        if (count == 0) {
            return null;
        }

        int s = 0, e = count - 1, m;
        Pair<AdapterDataObserver, Adapter> rs = null;

        // binary search range
        while (s <= e) {
            m = (s + e) / 2;
            rs = mAdapters.get(m);
            int endPosition = rs.first.mStartPosition + rs.second.getItemCount() - 1;

            if (rs.first.mStartPosition > position) {
                e = m - 1;
            } else if (endPosition < position) {
                s = m + 1;
            } else if (rs.first.mStartPosition <= position && endPosition >= position) {
                break;
            }

            rs = null;
        }

        return rs;
    }


    /**
     * 根据索引标示，得到Adapter在整个Adapter序列中的位置
     */
    public int findAdapterPositionByIndex(int index) {
        Pair<AdapterDataObserver, Adapter> rs = mIndexAry.get(index);
        return rs == null ? -1 : mAdapters.indexOf(rs);
    }

    /**
     * 根据adapter所在的索引位置得到Adapter
     * @param index
     * @return
     */
    public Adapter findAdapterByIndex(int index) {
        Pair<AdapterDataObserver, Adapter> rs = mIndexAry.get(index);
        return rs.second;
    }

    /**
     * 重构观察器，适应综合管理
     */
    protected class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        //起始位置:本观察器对应的Adapter的数据在整个列表中的起始位置
        int mStartPosition;

        //索引:该观察器及其对应adapter在整个列表中所有Adapter中的索引
        int mIndex = -1;

        public AdapterDataObserver(int startPosition, int index) {
            this.mStartPosition = startPosition;
            this.mIndex = index;
        }

        public void updateStartPositionAndIndex(int startPosition, int index) {
            this.mStartPosition = startPosition;
            this.mIndex = index;
        }

        public int getStartPosition() {
            return mStartPosition;
        }

        public int getIndex() {
            return mIndex;
        }

        /**
         * 更新列表时要更新LayoutHelper
         * @return
         */
        private boolean updateLayoutHelper(){
            if (mIndex < 0) {
                return false;
            }

            final int idx = findAdapterPositionByIndex(mIndex);
            if (idx < 0) {
                return false;
            }

            Pair<AdapterDataObserver, Adapter> p = mAdapters.get(idx);
            List<LayoutHelper> helpers = new LinkedList<>(getLayoutHelpers());
            LayoutHelper oldhelper = helpers.get(idx);

            LayoutHelper newHelper = p.second.onCreateLayoutHelper();
            if(newHelper != oldhelper){
                helpers.remove(idx);
                helpers.add(idx,newHelper);
            }
            if (newHelper.getItemCount() != p.second.getItemCount()) {
                // if itemCount changed;
                // 如果本Adapter的对应的数据集发生了变化，LayoutHelper维护区域的item个数对应更新
                newHelper.setItemCount(p.second.getItemCount());
                // 总的数据项其实数据重置
                mTotal = mStartPosition + p.second.getItemCount();

                for (int i = idx + 1; i < mAdapters.size(); i++) {
                    Pair<AdapterDataObserver, Adapter> pair = mAdapters.get(i);
                    // update startPosition for adapters in following
                    // 对应更新的起始位置之后的所有Adapter的观察期的起始位置都应该更新
                    pair.first.mStartPosition = mTotal;
                    // 总的数据项更新
                    mTotal += pair.second.getItemCount();
                }

                // set helpers to refresh range
                // 更新DelegateAdapter维护的Helpers集合
                DelegateAdapter.super.setLayoutHelpers(helpers);
            }
            return true;
        }

        @Override
        public void onChanged() {
            if (!updateLayoutHelper()) {
                return;
            }
            notifyDataSetChanged();
        }

        //根据其实位置更新实际位置
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            if (!updateLayoutHelper()) {
                return;
            }
            notifyItemRangeRemoved(mStartPosition + positionStart, itemCount);
        }

        //根据其实位置更新实际位置
        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            if (!updateLayoutHelper()) {
                return;
            }
            notifyItemRangeInserted(mStartPosition + positionStart, itemCount);
        }

        //根据其实位置更新实际位置
        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            if (!updateLayoutHelper()) {
                return;
            }
            notifyItemMoved(mStartPosition + fromPosition, mStartPosition + toPosition);
        }

        //根据其实位置更新实际位置
        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            if (!updateLayoutHelper()) {
                return;
            }
            notifyItemRangeChanged(mStartPosition + positionStart, itemCount);
        }
    }

    /**
     * 比传统的Adapter多了个对LayoutHelper的支持
     * 这将是这套框架中最基础的Adapter
     * @param <VH>
     */
    public static abstract class Adapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
        public abstract LayoutHelper onCreateLayoutHelper();

        protected void onBindViewHolderWithOffset(VH holder, int position, int offsetTotal) {

        }
    }

    /**
     * 托尔函数
     */
    private static long getCantor(long k1, long k2) {
        return (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
    }

}
