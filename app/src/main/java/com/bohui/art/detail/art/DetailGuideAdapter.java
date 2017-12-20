package com.bohui.art.detail.art;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.rv.ItemType;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/20
 * @description:
 */


public class DetailGuideAdapter extends BaseAdapter<String> {
    private String des;

    public DetailGuideAdapter(Context context, String des) {
        super(context);
        addItem(des);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_DETAIL_GUIDE;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_guide;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, String itemData, int position) {
        holder.setText(R.id.tv_des,itemData);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMarginTop(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.sys_margin_small));
        return singleLayoutHelper;
    }
}
