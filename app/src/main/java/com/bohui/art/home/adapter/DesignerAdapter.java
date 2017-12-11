package com.bohui.art.home.adapter;

import android.content.Context;

import com.bohui.art.R;
import com.bohui.art.home.bean.DesignerBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class DesignerAdapter extends BaseAdapter<DesignerBean> {
    public DesignerAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x6;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_designer;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerBean itemData, int position) {

    }
}
