package com.widget.grecycleview.adapter.tree;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.factory.ItemHelperFactory;
import com.widget.grecycleview.item.TreeItem;
import com.widget.grecycleview.item.TreeItemGroup;
import com.widget.grecycleview.manager.ItemManager;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baozi on 2017/4/20.
 * 树级结构recycleradapter.
 * item之间有子父级关系,
 */

public class TreeRecyclerAdapter extends BaseAdapter<TreeItem> {

    private TreeRecyclerType type = TreeRecyclerType.SHOW_DEFUTAL;

    private boolean isCloseOther = false;
    private List<TreeItemGroup> currentExpandItems = new ArrayList<>();
    private TreeItem lastItem;

    public TreeRecyclerAdapter(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onBindViewHolderClick(final BaseViewHolder holder, View view) {
        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    //检查item的position,这个item是否可以点击
                    if (getCheckItemManager().checkPosition(layoutPosition)) {
                        //获得处理后的position
                        int itemPosition = getCheckItemManager().getAfterCheckingPosition(layoutPosition);
                        //拿到BaseItem
                        TreeItem item = getDatas().get(itemPosition);
                        //展开,折叠和item点击不应该同时响应事件.
                        //必须是TreeItemGroup才能展开折叠,并且type不能为 TreeRecyclerViewType.SHOW_ALL
                        if (type != TreeRecyclerType.SHOW_ALL && item instanceof TreeItemGroup) {
                            //展开,折叠
                            expandOrCollapse(((TreeItemGroup) item));
                        } else {
                            TreeItemGroup itemParentItem = item.getParentItem();
                            //判断上一级是否需要拦截这次事件，只处理当前item的上级，不关心上上级如何处理.
                            if (itemParentItem != null && itemParentItem.onInterceptClick(item)) {
                                return;
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void setDatas(List<TreeItem> items) {
        if (null == items) {
            return;
        }
        getDatas().clear();
        assembleItems(items);
    }
    public void setDatas(TreeItemGroup treeItemGroup) {
        if (null == treeItemGroup) {
            return;
        }
        ArrayList<TreeItem> arrayList = new ArrayList<>();
        arrayList.add(treeItemGroup);
        setDatas(arrayList);
    }
    /**
     * 对初始的一级items进行遍历,将每个item的childs拿出来,进行組合。
     * @param items
     */
    private void assembleItems(List<TreeItem> items) {
        if (type != null) {
            List<TreeItem> datas = getDatas();
            datas.addAll(ItemHelperFactory.getChildItemsWithType(items, type));
        } else {
            super.setDatas(items);
        }
    }


    @Override
    public int geLayoutId(int viewType) {
        //通过反托尔运算得到position
        // 如果不是一致的，也就是类型经过了托尔运算
        // 反向托尔函数，用来来解析出原来的真实的itemType和position
        long w = (int) (Math.floor(Math.sqrt(8.0 * viewType + 1) - 1) / 2);
        long t = (w * w + w) / 2;

        //得到索引
        int position = (int) (viewType - t);
        //得到ItemType
        int trueType = (int) (w - position);
        return getData(position).getLayoutId();
    }
    @Override
    public int geViewType(int position) {
        int type = getData(position).initViewType();
        return (int) getCantor(type,position);
    }
    /**
     * 托尔函数
     */
    private static long getCantor(long k1, long k2) {
        return (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
    }
    @Override
    public final void bindViewHolder(BaseViewHolder holder, TreeItem item, int position) {
        item.onBindViewHolder(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final int spanCount = gridLayoutManager.getSpanCount();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int i = getItemCount();
                    if (i == 0 || position >= i) {
                        return spanCount;
                    }
                    int checkPosition = getCheckItemManager().getAfterCheckingPosition(position);
                    int itemSpanSize = getItemSpanSize(checkPosition);
                    if (itemSpanSize == 0) {
                        return spanCount;
                    }
                    return itemSpanSize;
                }
            });
        }
    }

    public int getItemSpanSize(int position) {
        return getData(position).getSpanSize();
    }

    public void setCloseOther(boolean closeOther) {
        isCloseOther = closeOther;
    }
    private void expandOrCollapse(TreeItemGroup treeItemGroup) {
        if(isCloseOther){
            //收起当前已经展开的同级别及其所有已展开的孩子
            if(null == lastItem || lastItem.equals(treeItemGroup)){
                boolean expand = treeItemGroup.isExpand();
                treeItemGroup.setExpand(!expand);
                if(!expand){
                    currentExpandItems.add(treeItemGroup);
                }
            }else{
                List<TreeItemGroup> hasClosed = new ArrayList<>();
                for(TreeItemGroup currentExpandItem : currentExpandItems){
                    if(null != currentExpandItem && currentExpandItem.getClass().getName().equals(treeItemGroup.getClass().getName()))
                    {
                        hasClosed.addAll(closeItem(currentExpandItem));
                    }
                }
                currentExpandItems.removeAll(hasClosed);
                if(hasClosed.size() >0 && hasClosed.contains(treeItemGroup)){
                    hasClosed.clear();
                    return;
                }
                boolean expand = treeItemGroup.isExpand();
                if(!expand){
                    treeItemGroup.setExpand(true);
                    currentExpandItems.add(treeItemGroup);
                }
            }
            lastItem = treeItemGroup;
        }else{
            boolean expand = treeItemGroup.isExpand();
            treeItemGroup.setExpand(!expand);
        }
    }
    private List<TreeItemGroup> closeItem(TreeItemGroup currentExpandItem) {
        List<TreeItemGroup> hasClose = new ArrayList<>();
        List<TreeItem> childs = currentExpandItem.getAllChilds();
        if(null != childs && childs.size()>0){
            for(TreeItem item : childs){
                if(item instanceof TreeItemGroup){
                    if(((TreeItemGroup) item).isExpand()){
                        ((TreeItemGroup) item).setExpand(false);
                        hasClose.add((TreeItemGroup) item);
                    }
                }
            }
        }
        currentExpandItem.setExpand(false);
        hasClose.add(currentExpandItem);
        return hasClose;
    }
    /**
     * 需要设置在setdata之前,否则type不会生效
     *
     * @param type
     */
    public void setType(TreeRecyclerType type) {
        this.type = type;
    }

    @Override
    public void addItem(TreeItem item) {
        if (null == item) {
            return;
        }
        if (item instanceof TreeItemGroup) {
            getDatas().add(item);
        } else {
            TreeItemGroup itemParentItem = item.getParentItem();
            if (itemParentItem != null) {
                List childs = itemParentItem.getChilds();
                if (childs != null) {
                    int i = getDatas().indexOf(itemParentItem);
                    getDatas().add(i + itemParentItem.getChilds().size(), item);
                } else {
                    childs = new ArrayList();
                    itemParentItem.setChilds(childs);
                }
                childs.add(item);
            }
        }
        notifyDataChanged();
    }

    @Override
    public void addItem(int position, TreeItem item) {
        getDatas().add(position, item);
        if (item != null && item.getParentItem() != null) {
            item.getParentItem().getChilds().add(item);
        }
        notifyDataChanged();
    }

    @Override
    public void addItems(List<TreeItem> items) {
        getDatas().addAll(items);
        notifyDataChanged();
    }

    @Override
    public void addItems(int position, List<TreeItem> items) {
        getDatas().addAll(position, items);
        notifyDataChanged();
    }

    @Override
    public void removeItem(TreeItem item) {
        if (null == item) {
            return;
        }
        getDatas().remove(item);
        TreeItemGroup itemParentItem = item.getParentItem();
        if (itemParentItem != null) {
            List childs = itemParentItem.getChilds();
            if (childs != null) {
                childs.remove(item);
            }
        }
        notifyDataChanged();
    }

    @Override
    public void removeItem(int position) {
        TreeItem t = getDatas().get(position);
        TreeItemGroup parentItem = t.getParentItem();
        if (parentItem != null && parentItem.getChilds() != null) {
            parentItem.getChilds().remove(t);
        }
        getDatas().remove(position);
        notifyDataChanged();
    }

    @Override
    public void removeItems(List<TreeItem> items) {
        getDatas().removeAll(items);
        notifyDataChanged();
    }

    @Override
    public void clear() {
        getDatas().clear();
        notifyDataChanged();
    }

    @Override
    public void replaceItem(int position, TreeItem item) {
        TreeItem t = getDatas().get(position);
        if (t instanceof TreeItemGroup) {
            getDatas().set(position, item);
        } else {
            TreeItemGroup parentItem = t.getParentItem();
            if (parentItem != null && parentItem.getChilds() != null) {
                List childs = parentItem.getChilds();
                int i = childs.indexOf(t);
                childs.set(i, item);
            }
            getDatas().set(position, item);
        }
        notifyDataChanged();
    }

    @Override
    public void replaceAllItem(List<TreeItem> items) {
        if (items != null) {
            setDatas(items);
            notifyDataChanged();
        }
    }

    private void notifyDataChanged() {
        notifyDataSetChanged();
    }

}
