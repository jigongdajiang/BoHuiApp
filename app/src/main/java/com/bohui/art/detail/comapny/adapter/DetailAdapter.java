package com.bohui.art.detail.comapny.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.detail.CompanyDetailResult;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class DetailAdapter extends BaseAdapter<CompanyDetailResult> {
    public DetailAdapter(Context context,CompanyDetailResult item) {
        super(context);
        addItem(item);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.COMPANY_DETAIL_INFO;
    }

    @Override
    public int geLayoutId(int positn) {
        return R.layout.detail_company_detail;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, CompanyDetailResult itemData, int position) {
        //头像
        ImageView iv_avr = holder.getView(R.id.iv_avr);
        GlideUtil.display(mContext,iv_avr,itemData.getLogo());
        //名称
        holder.setText(R.id.tv_name,itemData.getName());
        //作品数
        holder.setText(R.id.tv_art_num,"作品:  "+itemData.getNum());
        //地址
        holder.setText(R.id.tv_fans_num,"地址:  "+itemData.getAddress());
        //艺术格言
        holder.setText(R.id.tv_intro,itemData.getDesc());
        //是否关注
        tv_attention = holder.getView(R.id.tv_attention);
        changeAttentionText(itemData.getIsfollow());
        holder.addOnClickListener(R.id.tv_attention);
    }
    private TextView tv_attention;
    public void changeAttentionText(int isFollow){
        tv_attention.setText(isFollow == 0?"未关注":"已关注");
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
