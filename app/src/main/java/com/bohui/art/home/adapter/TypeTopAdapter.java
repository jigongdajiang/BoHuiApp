package com.bohui.art.home.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.home.bean.TypeTopBean;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class TypeTopAdapter extends BaseAdapter<TypeTopBean> {
    private TypeTopBean typeTopBean;
    public TypeTopAdapter(Context context, TypeTopBean typeTopBean) {
        super(context);
        this.typeTopBean = typeTopBean;
        addItem(typeTopBean);
    }

    @Override
    public int geViewType(int position) {
        return 0x1;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_type_top;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, TypeTopBean itemData, int position) {
        holder.setText(R.id.tv_type_top,typeTopBean.getDes());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMargin(0, ResUtil.getResDimensionPixelOffset(mContext,R.dimen.sys_margin_small),0,0);
        return singleLayoutHelper;
    }
}