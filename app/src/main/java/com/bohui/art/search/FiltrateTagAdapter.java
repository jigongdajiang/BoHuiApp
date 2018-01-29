package com.bohui.art.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.common.widget.rv.ItemType;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;
import com.widget.smallelement.flowtag.FlowLayout;
import com.widget.smallelement.flowtag.TagAdapter;
import com.widget.smallelement.flowtag.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 */


public class FiltrateTagAdapter extends BaseAdapter<FiltrateTagBean> {
    private FiltrateTagBean bean;
    private MyTagAdapter tagAdapter;
    private TagFlowLayout tagFlowLayout;

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
    public void bindViewHolder(BaseViewHolder holder, final FiltrateTagBean itemData, int position) {
        holder.setText(R.id.tv_des,itemData.getOneClassifyName());
        tagFlowLayout = holder.getView(R.id.tag);
        tagAdapter = new MyTagAdapter(itemData.getTwoClassify(),tagFlowLayout,itemData);
        tagFlowLayout.setAdapter(tagAdapter);
        tagFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                //根据选中的位置得到对应的选中对象的集合
                if(selectPosSet.size() > 0){
                    if(mOnSelectListener != null){
                        mOnSelectListener.onParentHasSelected(itemData,true);
                    }
                }else{
                    if(mOnSelectListener != null){
                        mOnSelectListener.onParentHasSelected(itemData,false);
                    }
                }

            }
        });
        tagFlowLayout.getSelectedList().clear();

    }
    public interface OnSelectListener{
        void onParentHasSelected(FiltrateTagBean oneClass,boolean has);
        void onSelectedOne(ClassifyLevelBean twoClass);
        void unSelectOne(ClassifyLevelBean twoClass);
    }
    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
    }
    private class MyTagAdapter extends TagAdapter<ClassifyLevelBean>{
        private ViewGroup tagFlowLayout;
        private FiltrateTagBean itemData;

        public MyTagAdapter(List<ClassifyLevelBean> datas, ViewGroup tagFlowLayout, FiltrateTagBean itemData) {
            super(datas);
            this.tagFlowLayout = tagFlowLayout;
            this.itemData = itemData;
        }

        @Override
        public View getView(FlowLayout parent, int position, ClassifyLevelBean twoClass)
        {
            TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_flitrate_tag_text,
                    tagFlowLayout, false);
            tv.setText(twoClass.getName());
            return tv;
        }

        @Override
        public void onSelected(int position, View view) {
            super.onSelected(position, view);
            if(mOnSelectListener != null){
                mOnSelectListener.onSelectedOne(itemData.getTwoClassify().get(position));
            }
        }

        @Override
        public void unSelected(int position, View view) {
            super.unSelected(position, view);
            if(mOnSelectListener != null){
                mOnSelectListener.unSelectOne(itemData.getTwoClassify().get(position));
            }
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return singleLayoutHelper;
    }
    public void reset(){
        tagFlowLayout.onChanged();
    }
}
