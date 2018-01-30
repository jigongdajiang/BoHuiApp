package com.bohui.art.detail.designer.adapter;

import android.content.Context;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.detail.DesignerDetailBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.bohui.art.bean.detail.DesignerDetailResult;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class IntroAdapter extends BaseAdapter<DesignerDetailBean> {
    public IntroAdapter(Context context,DesignerDetailBean result) {
        super(context);
        addItem(result);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.DESIGNER_DETAIL_INTRO;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.detail_designer_intro;
    }

    @Override
    public void bindViewHolder(BaseViewHolder holder, DesignerDetailBean itemData, int position) {
        holder.setText(R.id.tv_cn,itemData.getCompany()+"   "+itemData.getExperience()+"年工作经验");
        StringBuffer area = new StringBuffer();
        List<String> areas = itemData.getGood_at_field();
        if(!CollectionUtil.isEmpty(areas)){
            for(int i=0;i<areas.size();i++){
                if(i != areas.size()-1){
                    area.append(areas.get(i)+"-");
                }else{
                    area.append(areas.get(i));
                }
            }
        }
        holder.setText(R.id.tv_area,area.toString());

        StringBuffer style = new StringBuffer();
        List<String> styles = itemData.getGood_at_style();
        if(!CollectionUtil.isEmpty(styles)){
            for(int i=0;i<styles.size();i++){
                if(i!=styles.size()-1){
                    style.append(styles.get(i)+"-");
                }else{
                    style.append(styles.get(i));
                }
            }
        }
        holder.setText(R.id.tv_style,style.toString());
        holder.setText(R.id.tv_intro,itemData.getIntroduction());
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }
}
