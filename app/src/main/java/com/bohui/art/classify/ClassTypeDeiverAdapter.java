package com.bohui.art.classify;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class ClassTypeDeiverAdapter  extends BaseAdapter<ClassifyTypeBean> {
    private  ClassifyTypeBean classifyTypeBean;

    public ClassTypeDeiverAdapter(Context context, ClassifyTypeBean classifyTypeBean) {
        super(context);
        addItem(classifyTypeBean);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.CLASSIFY_ITEM_TYPE_DIVIDER;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_classify_type_divider;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ClassifyTypeBean itemData, int position) {
        holder.setText(R.id.tv_classify_type_divider_des,itemData.getType());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
