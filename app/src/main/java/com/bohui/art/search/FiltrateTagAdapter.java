package com.bohui.art.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;
import com.widget.smallelement.flowtag.FlowLayout;
import com.widget.smallelement.flowtag.TagAdapter;
import com.widget.smallelement.flowtag.TagFlowLayout;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 */


public class FiltrateTagAdapter extends BaseAdapter<FiltrateTagBean> {
    private FiltrateTagBean bean;

    public FiltrateTagAdapter(Context context, FiltrateTagBean bean) {
        super(context);
        addItem(bean);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.SEARCH_FILTRATE_TAG;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_filtrate_tag;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, FiltrateTagBean itemData, int position) {
        holder.setText(R.id.tv_des,itemData.getTabDes());
        final TagFlowLayout tagFlowLayout = holder.getView(R.id.tag);
        tagFlowLayout.setAdapter(new TagAdapter<String>(itemData.getTags())
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_flitrate_tag_text,
                        tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //进入搜索结果页
                return false;
            }
        });
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return singleLayoutHelper;
    }
}
