package com.jimeijf.financing.common.rv.adapter;

import android.content.Context;

import com.jimeijf.financing.R;
import com.jimeijf.financing.common.rv.bean.StickyTagBean;
import com.jimeijf.grecycleview.adapter.base.BaseGroupAdapter;
import com.jimeijf.grecycleview.viewholder.BaseViewHolder;


/**
 * @author gaojigong
 * @version V1.0
 * @Description: 以年份为标准悬停头部的列表适配器
 * @date 16/11/01.
 */
public abstract class StickyNormalAdapter<T extends StickyTagBean> extends BaseGroupAdapter<T> {

    public StickyNormalAdapter(Context context) {
        super(context);
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.layout_sticky;
    }

    @Override
    public void convertSticky(BaseViewHolder holder, T itemData) {
        holder.setText(R.id.title_item_time_tv, itemData.getStickyDes());
    }

    @Override
    public long getHeaderId(int position) {
        if (null != getData(position)) {
            //防止空指针
            return getData(position).getHeaderId();
        } else {
            return -1;
        }
    }
}