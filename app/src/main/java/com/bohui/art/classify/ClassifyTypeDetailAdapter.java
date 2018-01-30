package com.bohui.art.classify;

import android.content.Context;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class ClassifyTypeDetailAdapter extends BaseAdapter<ClassifyLevelBean> {
    public ClassifyTypeDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.CLASSIFY_ITEM_TYPE_DETAIL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_classify_type_detail;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ClassifyLevelBean itemData, int position) {
        ImageView iv_classify_type_detail = holder.getView(R.id.iv_classify_type_detail);
        GlideUtil.display(mContext,iv_classify_type_detail,itemData.getLogo());
        holder.setText(R.id.tv_classify_type_detail,itemData.getName());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setMarginTop(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_10));
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setHGap(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_20));
        gridLayoutHelper.setVGap(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_20));
        return gridLayoutHelper;
    }
}
