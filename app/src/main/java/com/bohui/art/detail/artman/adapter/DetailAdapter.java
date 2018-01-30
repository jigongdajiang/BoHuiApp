package com.bohui.art.detail.artman.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.ArtMainDetailResult;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class DetailAdapter extends BaseAdapter<ArtMainDetailResult> {
    public DetailAdapter(Context context,ArtMainDetailResult result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.ART_MAN_DETAIL_DETAIL;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_art_man_detail;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, ArtMainDetailResult itemData, int position) {
        //头像
        ImageView iv_avr = holder.getView(R.id.iv_avr);
        GlideUtil.display(mContext,iv_avr,itemData.getPhoto());
        //名称
        holder.setText(R.id.tv_name,itemData.getName());
        //作品数
        holder.setText(R.id.tv_art_num,"作品:  "+itemData.getNum());
        //粉丝量
        holder.setText(R.id.tv_fans_num,"粉丝:  "+itemData.getFollownum());
        //艺术格言
        holder.setText(R.id.tv_intro,itemData.getPersonalnote());
        tv_attention = holder.getView(R.id.tv_attention);
        //是否关注
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
