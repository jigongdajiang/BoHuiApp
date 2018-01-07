package com.bohui.art.detail.designer.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.DesignerDetailResult;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class IntroAdapter extends BaseAdapter<DesignerDetailResult> {
    public IntroAdapter(Context context,DesignerDetailResult result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.DESIGNER_DETAIL_INTRO;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_designer_intro;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerDetailResult itemData, int position) {

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
