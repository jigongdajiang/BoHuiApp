package com.bohui.art.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.home.bean.Type2LevelBean;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class Type2LevelAdapter extends BaseAdapter<Type2LevelBean> {
    public Type2LevelAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x8;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_type2level;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, Type2LevelBean itemData, int position) {
        GlideUtil.displayCircle(mContext,(ImageView) holder.getView(R.id.iv_2level),itemData.getImgUrl());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        return gridLayoutHelper;
    }
}
