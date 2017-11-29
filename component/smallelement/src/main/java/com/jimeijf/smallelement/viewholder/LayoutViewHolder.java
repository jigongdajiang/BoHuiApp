package com.jimeijf.smallelement.viewholder;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jimeijf.core.util.ResUtil;

/**
 * @author : gaojigong
 * @date : 2017/6/16
 * FileName:
 * @description: DialogViewHolder  功能随需求扩展
 */


public class LayoutViewHolder {
    /**
     * Views indexed with their IDs
     * 根据id的View索引列表
     */
    private final SparseArray<View> views = new SparseArray<>();
    public View convertView;

    public LayoutViewHolder(Context context, int layoutId) {
        this.convertView = LayoutInflater.from(context).inflate(layoutId,null,false);
    }

    public View getConvertView() {
        return convertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置文字显示 会根据文字是否为空决定其是否显示，以最大程度的服用布局文件
     */
    public LayoutViewHolder setTextViewText(int viewId, String text) {
        TextView view = getView(viewId);
        if(null != view) {
            if (!TextUtils.isEmpty(text)) {
                view.setText(text);
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
        return this;
    }
    /**
     * @param color em R.color.colorFFFFFF
     */
    public LayoutViewHolder setTextColor(int viewId, int color) {
        TextView view = getView(viewId);
        if(null != view){
            view.setTextColor(color);
        }
        return this;
    }


    /**
     *
     * @param size em:R.dimen.sp_10
     */
    public LayoutViewHolder setTextSize(int viewId, int size) {
        TextView view = getView(viewId);
        if(null != view) {
            if(size != 0){
                view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ResUtil.getResDimen(view.getContext(),size));
            }
        }
        return this;
    }
    public LayoutViewHolder setViewOnClickListener(int viewId, View.OnClickListener listener){
        View view = getView(viewId);
        if(null != view && null != listener){
            view.setOnClickListener(listener);
        }
        return this;
    }

    /**
     * @param background R.drawable.sel_bg
     */
    public LayoutViewHolder setViewBackoundRes(int viewId, int background) {
        View view = getView(viewId);
        if(null != view) {
            if(background != 0){
                view.setBackgroundResource(background);
            }
        }
        return this;
    }
    /**
     * @param color em R.color.colorFFFFFF
     */
    public LayoutViewHolder setViewBackoundColor(int viewId, int color) {
        View view = getView(viewId);
        if(null != view) {
            if(color != 0){
                view.setBackgroundColor(ResUtil.getResColor(view.getContext(),color));
            }
        }
        return this;
    }
    public LayoutViewHolder setImageRes(int viewId, int resId) {
        ImageView view = getView(viewId);
        if(null != view) {
            if (resId != 0) {
                view.setVisibility(View.VISIBLE);
                view.setImageResource(resId);
            }else{
                view.setVisibility(View.GONE);
            }
        }
        return this;
    }
}
