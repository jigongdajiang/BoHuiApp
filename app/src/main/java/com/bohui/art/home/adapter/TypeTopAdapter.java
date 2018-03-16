package com.bohui.art.home.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class TypeTopAdapter extends BaseAdapter<ClassifyLevelBean> {
    private ClassifyLevelBean typeTopBean;
    public TypeTopAdapter(Context context, ClassifyLevelBean typeTopBean) {
        super(context);
        this.typeTopBean = typeTopBean;
        addItem(typeTopBean);
    }

    @Override
    public int geViewType(int position) {
        return 0x2;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_type_top;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ClassifyLevelBean itemData, int position) {
        holder.setText(R.id.tv_type_top,typeTopBean.getName());
        if (itemData.getLevel() == 3){
            holder.setVisible(R.id.iv_more,false);
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMargin(0, 0,0,0);
        return singleLayoutHelper;
    }
}
