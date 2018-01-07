package com.bohui.art.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.common.util.BannerHelper;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;
import com.widget.smallelement.banner.ConvenientBanner;


/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class BannerAdapter extends BaseAdapter<BannerResult> {
    private BannerHelper bannerHelper;
    private boolean isVertical;//控制横竖
    private int bannerHeight;//从外部控制高度
    private int bannerMargin;
    private int turingTime;
    private boolean showIndicator = true;
    public BannerAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x1;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.header_banner;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public void setBannerHeight(int bannerHeight) {
        this.bannerHeight = bannerHeight;
    }

    public void setBannerMargin(int bannerMargin) {
        this.bannerMargin = bannerMargin;
    }

    public void setTuringTime(int turingTime) {
        this.turingTime = turingTime;
    }

    public void setShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, BannerResult itemData, int position) {
        View bannerHeader = holder.getConvertView();
        ConvenientBanner<BannerBean> cb_banner= bannerHeader.findViewById(R.id.cb_banner);
        if(bannerHeight > 0){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cb_banner.getLayoutParams();
            params.height = bannerHeight;
            cb_banner.setLayoutParams(params);
        }
        cb_banner.setIsVertical(isVertical);
        bannerHelper = new BannerHelper(cb_banner, (Activity) mContext, "");
        bannerHelper.setVertical(isVertical);
        if(turingTime > 0){
            bannerHelper.setTuringTime(turingTime);
        }
        bannerHelper.refreshBanner(itemData.getBannerBeans());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        singleLayoutHelper.setMargin(0,bannerMargin,0,0);
        return singleLayoutHelper;
    }
}
