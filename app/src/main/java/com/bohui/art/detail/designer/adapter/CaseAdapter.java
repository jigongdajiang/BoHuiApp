package com.bohui.art.detail.designer.adapter;

import android.content.Context;

import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.CaseBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class CaseAdapter extends BaseAdapter<CaseBean> {
    public CaseAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.DESIGNER_DETAIL_CASE;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_designer_case;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, CaseBean itemData, int position) {

    }
}
