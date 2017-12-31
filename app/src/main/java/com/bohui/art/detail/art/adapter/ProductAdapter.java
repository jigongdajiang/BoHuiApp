package com.bohui.art.detail.art.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.bean.BannerBean;
import com.bohui.art.common.rv.ItemType;
import com.bohui.art.common.util.BannerHelper;
import com.bohui.art.detail.art.bean.ArtDetailBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;
import com.widget.smallelement.banner.ConvenientBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/18
 * @description:
 */


public class ProductAdapter extends BaseAdapter<ArtDetailBean> {
    public ProductAdapter(Context context,ArtDetailBean bean) {
        super(context);
        addItem(bean);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_DETAIL_PRODUCT;
    }
    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_product;
    }

    private int currentNumber = 1;
    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtDetailBean itemData, int position) {
        ConvenientBanner<String> cb_banner = holder.getView(R.id.banner);
        List<BannerBean>  bannerBeans = new ArrayList<>();
        for(String imgUrl : itemData.getImgs()){
            BannerBean bannerBean = new BannerBean();
            bannerBean.setImgUrl(imgUrl);
            bannerBeans.add(bannerBean);
        }
        BannerHelper bannerHelper = new BannerHelper(cb_banner,(Activity) mContext,"");
        bannerHelper.refreshBanner(bannerBeans);
        final TextView tvNumber = holder.getView(R.id.tv_number);
        final int numbers = itemData.getImgs().size();
        showTvNumber(tvNumber,currentNumber,numbers);
        cb_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentNumber = position+1;
                showTvNumber(tvNumber,currentNumber,numbers);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        holder.addOnClickListener(R.id.rl_art_man);
    }
    private void showTvNumber(TextView tv,int cn,int numbers){
        tv.setText(cn+"/"+numbers);
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}