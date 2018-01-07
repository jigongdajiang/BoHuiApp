package com.bohui.art.common.widget.rv.adapter;

import android.content.Context;

import com.bohui.art.R;
import com.bohui.art.common.widget.rv.bean.StickyTagBean;
import com.widget.grecycleview.adapter.base.BaseGroupAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;


/**
 * @author gaojigong
 * @version V1.0
 * @Description: 以年份为标准悬停头部的列表适配器
 * @date 16/11/01.
 */
public abstract class StickyNormalAdapter<T extends StickyTagBean> extends BaseGroupAdapter<T> {
    public StickyNormalAdapter(Context context) {
        super(context, R.layout.item_sticky);
    }
    @Override
    public void convertSticky(BaseViewHolder holder, T itemData) {
        holder.setText(R.id.tv_sticky, itemData.getStickyDes());
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