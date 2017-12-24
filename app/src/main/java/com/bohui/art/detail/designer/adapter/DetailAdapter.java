package com.bohui.art.detail.designer.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.rv.ItemType;
import com.bohui.art.detail.designer.bean.DesignerDetailResult;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class DetailAdapter extends BaseAdapter<DesignerDetailResult> {
    public DetailAdapter(Context context,DesignerDetailResult result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.DESIGNER_DETAL_DETAIL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_designer_detail;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerDetailResult itemData, int position) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
