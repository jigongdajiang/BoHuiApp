package com.bohui.art.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.common.activity.BaseWebActivity;
import com.bohui.art.common.bean.BannerBean;
import com.bumptech.glide.Glide;
import com.framework.core.glideext.GlideUtil;
import com.widget.smallelement.banner.ConvenientBanner;
import com.widget.smallelement.banner.holder.CBViewHolderCreator;
import com.widget.smallelement.banner.holder.Holder;
import com.widget.smallelement.banner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gaojigong
 * @version V1.0
 * @Description: banner工具类
 * @date 17/3/4
 */
public class BannerHelper {
    private ConvenientBanner mBanner;
    private Activity mContext;
    private CBViewHolderCreator mCbViewHolderCreator;
    private List<BannerBean> mBannerDatas;

    private static final int TURINGTIME = 5 * 1000;
    private String show;

    public BannerHelper(ConvenientBanner banner, final Activity context, final String show) {
        this.mBanner = banner;
        this.mContext = context;
        this.show = show;
        mCbViewHolderCreator = new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView(show);
            }
        };
        mBanner.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                if (mBannerDatas != null && mBannerDatas.size() > 0) {
                    BannerBean bannerBean = mBannerDatas.get(position);
                    if (bannerBean != null && !TextUtils.isEmpty(bannerBean.getUrl())) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, BaseWebActivity.class);
                        intent.putExtra(BaseWebActivity.WEB_URL_CONTENT, bannerBean.getUrl());
                        mContext.startActivity(intent);
                    }
                }
            }
        });
        mBannerDatas = new ArrayList<>();
    }

    public void refreshBanner(List<BannerBean> bannerBeans) {
        if (mBannerDatas == null || mBannerDatas.size() == 0) {
            mBannerDatas = new ArrayList<>();
            BannerBean defaultBannerBean = new BannerBean();
            mBannerDatas.add(defaultBannerBean);
        }
        if (mBanner != null) {
            mBannerDatas.clear();
            mBannerDatas.addAll(bannerBeans);

                mBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                        .setPageIndicator(new int[]{R.drawable.shape_banner_ind_unsel, R.drawable.shape_banner_ind_sel})
                        .setPages(mCbViewHolderCreator, mBannerDatas); //选中.
            onResume();
        }
    }

    public void onResume() {
        if (null != mBanner && !mBanner.isTurning()) {
            if (null != mBannerDatas && mBannerDatas.size() > 1) {
                mBanner.startTurning(TURINGTIME);
                mBanner.getViewPager().setCanLoop(true);
            } else {
                mBanner.getViewPager().setCanLoop(false);
            }
        }
    }

    public void onPause() {
        if (null != mBanner && mBanner.isTurning()) {
            mBanner.stopTurning();
        }
    }

    /**
     * 加载轮播图 图片
     */
    private class LocalImageHolderView implements Holder<BannerBean> {
        private ImageView imageView;
        String show;

        LocalImageHolderView(String show) {
            this.show = show;
        }

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, BannerBean bean) {
            if (TextUtils.isEmpty(bean.getImgUrl())) {
                Glide.with(mContext)
                        .load(R.drawable.img_nothing)
                        .into(imageView);
                return;
            }
            GlideUtil.display(mContext,imageView,bean.getImgUrl(),R.drawable.img_nothing,R.drawable.img_nothing,R.drawable.img_nothing);
        }
    }
}
