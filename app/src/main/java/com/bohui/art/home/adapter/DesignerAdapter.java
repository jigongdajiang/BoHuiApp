package com.bohui.art.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.bean.found.DesignerItemBean;
import com.bohui.art.detail.artman.mvp.ArtManDetailContact;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class DesignerAdapter extends BaseAdapter<DesignerItemBean> {
    public DesignerAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return 0x6;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_designer;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerItemBean itemData, int position) {
        //头像
        ImageView iv = holder.getView(R.id.iv_designer_logo);
        GlideUtil.display(mContext,iv,itemData.getPhoto());
        //名称
        holder.setText(R.id.tv_designer_name,itemData.getName());
        //知名度
        holder.setText(R.id.tv_designer_type,"知名设计师");
        //公司
        holder.setText(R.id.tv_designer_company,itemData.getCompany());
        //认证
        holder.setVisible(R.id.iv_v, itemData.getIs_recommed() != 0);
        //擅长
        holder.setText(R.id.tv_designer_des,"擅长: "+itemData.getGood_at_style());
        //经验
        holder.setText(R.id.tv_designer_experience,itemData.getExperience()+"年设计经验");
        //案例数
        holder.setText(R.id.tv_designer_case_number,"案例数: "+"100");
    }
}
