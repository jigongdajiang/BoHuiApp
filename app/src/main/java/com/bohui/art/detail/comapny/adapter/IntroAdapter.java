package com.bohui.art.detail.comapny.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class IntroAdapter extends BaseAdapter<CompanyDetailResult> {
    public IntroAdapter(Context context, CompanyDetailResult item) {
        super(context);
        addItem(item);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.COMPANY_DETAIL_INFO;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_company_intro;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, CompanyDetailResult itemData, int position) {
        holder.setText(R.id.tv_company_intro,itemData.getDetail());
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
