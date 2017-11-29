package com.widget.smallelement.title;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.widget.smallelement.viewholder.LayoutViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/6/17
 * @description: 抽象TitleBar 依赖布局  title虽然大多数差不多，但是个别还是有区别
 */


public abstract class AbsTitleBar<P extends AbsTitleBar.Builder.Params> implements ITitleBar{
    protected P mParams;

    public AbsTitleBar(P params) {
        this.mParams = params;
        if(mParams.position == 0){
            mParams.mParent.addView(mParams.mViewHolder.getConvertView(),0);
        }else if(mParams.position == 1){
            mParams.mParent.addView(mParams.mViewHolder.getConvertView());
        }else {
            mParams.mParent.addView(mParams.mViewHolder.getConvertView(),mParams.position);
        }
        applyParams();
    }

    public View getTitleBarView() {
        return mParams.mViewHolder.getConvertView();
    }

    /**
     * 动态操控某个View时通过该方法获取title中的具体View
     */
    public View getInsideView(int vId){
        return mParams.mViewHolder.getView(vId);
    }

    /**
     * 如果需要简单的去改变View的某些属性时，可通过该方法得到的ViewHolder去设置
     * 如改变文字内容。
     * @return
     */
    public LayoutViewHolder getViewHolder(){
        return mParams.mViewHolder;
    }

    public abstract static class Builder{

        public abstract AbsTitleBar builder();

        public  static class Params{
            public Context mContext;
            public ViewGroup mParent;
            public int position;//title的位置，0:第一个 一般LinearLayout时会是第一个  1:最后一个，当标题在最上面遮盖时会用这个，其它:指定位置
            public LayoutViewHolder mViewHolder;



            public Params(Context context, ViewGroup parent, int layoutId) {
                this(context,parent,layoutId,0);
            }
            public Params(Context context, ViewGroup parent, int layoutId, int position) {
                this.mContext = context;
                this.mParent = parent;
                this.position = position;
                mViewHolder = new LayoutViewHolder(context,layoutId);
            }
        }
    }
}
