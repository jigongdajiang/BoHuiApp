package com.bohui.art.classify;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.home.Type2LevelBean;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class ClassifyTypeDetailAdapter extends BaseAdapter<Type2LevelBean> {
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
    public void bindViewHolder(BaseViewHolder holder, Type2LevelBean itemData, int position) {
        holder.setText(R.id.tv_classify_type_detail,itemData.getType());
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
