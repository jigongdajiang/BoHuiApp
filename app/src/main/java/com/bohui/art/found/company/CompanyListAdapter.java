package com.bohui.art.found.company;

import android.content.Context;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.bean.found.CompanyListItemBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.glideext.GlideUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class CompanyListAdapter extends BaseAdapter<CompanyListItemBean> {
    public CompanyListAdapter(Context context, List<CompanyListItemBean> datas) {
        super(context, datas);
    }

    public CompanyListAdapter(Context context) {
        super(context);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.COMPANY_LIST_ITEM;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_company_list;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, CompanyListItemBean itemData, int position) {
        ImageView iv = holder.getView(R.id.iv_company);
        GlideUtil.display(mContext,iv,itemData.getLogo());
        holder.setText(R.id.tv_company_name,itemData.getName());
        holder.setText(R.id.tv_product_num,"作品("+itemData.getNum()+")");
    }
}
