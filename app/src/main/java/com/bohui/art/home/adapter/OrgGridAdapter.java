package com.bohui.art.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.DisplayUtil;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class OrgGridAdapter extends BaseAdapter<ArtCoverItemBean> {
    public OrgGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x5;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_art_1;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtCoverItemBean itemData, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        params.height = DisplayUtil.getScreenWidth(mContext)/2 - 1;
        imageView.setLayoutParams(params);
        GlideUtil.display(mContext,imageView,itemData.getCover());
        holder.setText(R.id.tv_des,itemData.getName());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setHGap(2);
        gridLayoutHelper.setVGap(2);
        gridLayoutHelper.setAutoExpand(true);
        return gridLayoutHelper;
    }
}
