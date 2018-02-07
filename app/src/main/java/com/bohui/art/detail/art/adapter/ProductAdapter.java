package com.bohui.art.detail.art.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.common.util.BannerHelper;
import com.bohui.art.bean.detail.ArtDetailResult;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;
import com.widget.smallelement.banner.ConvenientBanner;
import com.widget.smallelement.banner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/18
 * @description:
 */


public class ProductAdapter extends BaseAdapter<ArtDetailResult> {
    public ProductAdapter(Context context,ArtDetailResult bean) {
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
    private OnItemClickListener onItemClickListener;
    private ConvenientBanner cb_banner;
    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtDetailResult itemData, int position) {
        //详情图片
        cb_banner = holder.getView(R.id.banner);
        List<BannerBean>  bannerBeans = new ArrayList<>();
        for(String imgUrl : itemData.getImgs()){
            BannerBean bannerBean = new BannerBean();
            bannerBean.setImage(imgUrl);
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
        cb_banner.setOnItemClickListener(onItemClickListener);
        //艺术品名称
        holder.setText(R.id.tv_name,itemData.getName() + itemData.getArtname()+"作品");
        //艺术品价格
        holder.setText(R.id.tv_price,String.valueOf(itemData.getPrice()));
        //艺术品尺寸规格
        holder.setText(R.id.tv_size,itemData.getSize());
        //剩余量
        holder.setText(R.id.tv_loss_num,itemData.getLooknum()+"人浏览");
        //艺术家头像
        ImageView iv_avr = holder.getView(R.id.iv_avr);
        GlideUtil.display(mContext,iv_avr,itemData.getPhoto());
        //艺术家名称
        holder.setText(R.id.tv_art_man_name,itemData.getArtname());
        //艺术家级别
        int level = itemData.getLevel();
        switch (level){
            case 1:
                holder.setText(R.id.tv_art_man_level,"国家级艺术家");
                break;
            case 2:
                holder.setText(R.id.tv_art_man_level,"省级艺术家");
                break;
            case 3:
                holder.setText(R.id.tv_art_man_level,"市级艺术家");
                break;
        }
        holder.addOnClickListener(R.id.rl_art_man);
    }
    private void showTvNumber(TextView tv,int cn,int numbers){
        tv.setText(cn+"/"+numbers);
    }

    public void setCurrentItem(int position){
        cb_banner.setcurrentitem(position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
