package com.bohui.art.common.widget.title;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bohui.art.R;
import com.framework.core.util.ResUtil;
import com.framework.core.util.StatusBarCompatUtil;
import com.widget.smallelement.title.AbsTitleBar;


/**
 * @author gaojigong
 * @version V1.0
 * @Description: 标题公共类
 * @date 2017/6/173.
 */
public class DefaultTitleBar extends AbsTitleBar<DefaultTitleBar.DefaultBuilder.DefaultParams> {
    public DefaultTitleBar(DefaultTitleBar.DefaultBuilder.DefaultParams params) {
        super(params);
    }

    @Override
    public void applyParams() {
        mParams.mViewHolder.setViewBackoundColor(R.id.title_default, mParams.backgroundColor);

        mParams.mViewHolder.getView(R.id.title_bottom_line).setVisibility(mParams.bottomViewVisible ? View.VISIBLE : View.GONE);
        mParams.mViewHolder.getView(R.id.title_bottom_line).setBackgroundColor(ResUtil.getResColor(mParams.mContext, mParams.bottomViewColor));

        mParams.mViewHolder.setTextViewText(R.id.tv_title_left, mParams.leftText);
        mParams.mViewHolder.setTextSize(R.id.tv_title_left, mParams.leftTextSize);
        mParams.mViewHolder.setTextColor(R.id.tv_title_left, mParams.leftTextColor);

        mParams.mViewHolder.setImageRes(R.id.iv_title_left, mParams.leftImageResId);
        if (mParams.leftImageShow) {
            mParams.mViewHolder.getView(R.id.iv_title_left).setVisibility(View.VISIBLE);
        } else {
            mParams.mViewHolder.getView(R.id.iv_title_left).setVisibility(View.GONE);
        }

        mParams.mViewHolder.setTextViewText(R.id.tv_title_title, mParams.title);
        mParams.mViewHolder.setTextSize(R.id.tv_title_title, mParams.titleTextSize);
        mParams.mViewHolder.setTextColor(R.id.tv_title_title, mParams.titleTextColor);

        mParams.mViewHolder.setTextViewText(R.id.tv_title_subtitle, mParams.subTitle);
        mParams.mViewHolder.setTextSize(R.id.tv_title_subtitle, mParams.subTitleTextSize);
        mParams.mViewHolder.setTextColor(R.id.tv_title_subtitle, mParams.subTitleTextColor);

        mParams.mViewHolder.setImageRes(R.id.img_title_center, mParams.centerImage);

        mParams.mViewHolder.setTextViewText(R.id.tv_title_right, mParams.rightText);
        mParams.mViewHolder.setTextSize(R.id.tv_title_right, mParams.rightTextSize);
        mParams.mViewHolder.setTextColor(R.id.tv_title_right, mParams.rightTextColor);
        mParams.mViewHolder.setViewBackoundRes(R.id.tv_title_right, mParams.rightTextBackground);

        mParams.mViewHolder.setImageRes(R.id.iv_title_right_1, mParams.rightImageResId1);
        mParams.mViewHolder.setImageRes(R.id.iv_title_right_2, mParams.rightImageResId2);
        if (null == mParams.leftClickListner) {
            //默认左侧区域有控件显示时才会设置返回监听
            if (mParams.leftImageShow || 0 != mParams.leftImageResId || !TextUtils.isEmpty(mParams.leftText)) {
                mParams.mViewHolder.setViewOnClickListener(R.id.rl_title_left_area, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mParams.mContext && mParams.mContext instanceof Activity) {
                            ((Activity) mParams.mContext).onBackPressed();
                        }
                    }
                });
            }
        } else {
            mParams.mViewHolder.setViewOnClickListener(R.id.rl_title_left_area, mParams.leftClickListner);
        }
        if (mParams.mViewHolder.getView(R.id.tv_title_title).getVisibility() == View.VISIBLE
                || mParams.mViewHolder.getView(R.id.tv_title_subtitle).getVisibility() == View.VISIBLE
                || mParams.mViewHolder.getView(R.id.img_title_center).getVisibility() == View.VISIBLE) {
            if (null != mParams.centerAreaClickLinstener) {
                mParams.mViewHolder.setViewOnClickListener(R.id.rl_title_center_area, mParams.centerAreaClickLinstener);
            }
        }
        if (null != mParams.rightTextClickListner) {
            mParams.mViewHolder.setViewOnClickListener(R.id.tv_title_right, mParams.rightTextClickListner);
        }
        if (null != mParams.rightImage1ClickListner) {
            mParams.mViewHolder.setViewOnClickListener(R.id.iv_title_right_1, mParams.rightImage1ClickListner);
        }
        if (null != mParams.rightImage2ClickListner) {
            mParams.mViewHolder.setViewOnClickListener(R.id.iv_title_right_2, mParams.rightImage2ClickListner);
        }
        if (mParams.supportImmersion) {
            if (null != mParams.mContext && mParams.mContext instanceof Activity) {
                Activity activity = (Activity) mParams.mContext;
                StatusBarCompatUtil.Builder builder = new StatusBarCompatUtil.Builder(activity);
                if (mParams.immersionColorId == 0) {
                    //沉浸式
                    View titleBar = mParams.mViewHolder.getConvertView();
                    builder.setSupportType(0).setPaddingChangedView(titleBar);
                } else {
                    //着色状态栏
                    builder.setSupportType(1).setColor(mParams.immersionColorId);
                }
                if (mParams.darkModeStartBar != 0) {
                    if(1 == mParams.darkModeStartBar){
                        builder.setChangeIconType(1).setImmerseForIconColor(R.color.sys_status_bar_color);
                    }else if(2 == mParams.darkModeStartBar){
                        builder.setChangeIconType(2);
                    }
                }
                builder.builder().apply();
            }

        }
    }

    public static class DefaultBuilder extends AbsTitleBar.Builder {

        private final DefaultParams mParams;

        public DefaultBuilder(Context context, ViewGroup parent, int position) {
            mParams = new DefaultParams(context, parent, R.layout.title_default, position);
        }

        public DefaultBuilder(Context context, ViewGroup parent) {
            this(context, parent, 0);
        }

        public DefaultBuilder(Context context, int position) {
            ViewGroup parent;
            if (null != context && context instanceof Activity) {
                parent = (ViewGroup) ((ViewGroup) ((Activity) context).findViewById(android.R.id.content)).getChildAt(0);
            } else {
                parent = new FrameLayout(context);
            }
            mParams = new DefaultParams(context, parent, R.layout.title_default, position);
        }

        public DefaultBuilder(Context context) {
            this(context, 0);
        }

        public DefaultBuilder setBackgroundColor(int color) {
            mParams.backgroundColor = color;
            return this;
        }

        public DefaultBuilder setBottomViewVisible(boolean visible) {
            mParams.bottomViewVisible = visible;
            return this;
        }

        public DefaultBuilder setBottomViewColor(int color) {
            mParams.bottomViewColor = color;
            return this;
        }

        public DefaultBuilder setLeftText(String leftText) {
            mParams.leftText = leftText;
            return this;
        }

        public DefaultBuilder setLeftTextSize(int size) {
            mParams.leftTextSize = size;
            return this;
        }

        public DefaultBuilder setLeftTextColor(int color) {
            mParams.leftTextColor = color;
            return this;
        }

        public DefaultBuilder setLeftImageResourceId(int lefImageResId) {
            mParams.leftImageResId = lefImageResId;
            return this;
        }

        public DefaultBuilder setLeftImageShow(boolean leftImageShow) {
            mParams.leftImageShow = leftImageShow;
            return this;
        }

        public DefaultBuilder setTitle(String title) {
            mParams.title = title;
            return this;
        }

        public DefaultBuilder setTitleSize(int size) {
            mParams.titleTextSize = size;
            return this;
        }

        public DefaultBuilder setTitleColor(int color) {
            mParams.titleTextColor = color;
            return this;
        }

        public DefaultBuilder setSubTitle(String subTitle) {
            mParams.subTitle = subTitle;
            return this;
        }

        public DefaultBuilder setSubTitleTextSize(int size) {
            mParams.subTitleTextSize = size;
            return this;
        }

        public DefaultBuilder setSubTitleTextColor(int color) {
            mParams.subTitleTextColor = color;
            return this;
        }

        public DefaultBuilder setCenterImage(int img) {
            mParams.centerImage = img;
            return this;
        }

        public DefaultBuilder setRightText(String rightText) {
            mParams.rightText = rightText;
            return this;
        }

        public DefaultBuilder setRightTextSize(int size) {
            mParams.rightTextSize = size;
            return this;
        }

        public DefaultBuilder setRightTextColor(int color) {
            mParams.rightTextColor = color;
            return this;
        }

        public DefaultBuilder setRightTextBackground(int background) {
            mParams.rightTextBackground = background;
            return this;
        }

        public DefaultBuilder setRightImage1(int rightmageResId1) {
            mParams.rightImageResId1 = rightmageResId1;
            return this;
        }

        public DefaultBuilder setRightImage2(int rightmageResId2) {
            mParams.rightImageResId2 = rightmageResId2;
            return this;
        }

        public DefaultBuilder setSupportImmersion(boolean supportImmersion) {
            mParams.supportImmersion = supportImmersion;
            return this;
        }

        public DefaultBuilder setImmersionColorId(int immersionColorId) {
            mParams.immersionColorId = immersionColorId;
            return this;
        }

        public DefaultBuilder setDarkModeStartBar(int  dark) {
            mParams.darkModeStartBar = dark;
            return this;
        }

        public DefaultBuilder setLeftClickListner(View.OnClickListener leftClickListner) {
            mParams.leftClickListner = leftClickListner;
            return this;
        }

        public DefaultBuilder setCenterClickListner(View.OnClickListener centerClickListner) {
            mParams.centerAreaClickLinstener = centerClickListner;
            return this;
        }

        public DefaultBuilder setRightTextClickListner(View.OnClickListener rightTextClickListner) {
            mParams.rightTextClickListner = rightTextClickListner;
            return this;
        }

        public DefaultBuilder setRightImage1ClickListner(View.OnClickListener rightImage1ClickListner) {
            mParams.rightImage1ClickListner = rightImage1ClickListner;
            return this;
        }

        public DefaultBuilder setRightImage2ClickListner(View.OnClickListener rightImage2ClickListner) {
            mParams.rightImage2ClickListner = rightImage2ClickListner;
            return this;
        }

        @Override
        public DefaultTitleBar builder() {
            DefaultTitleBar defaultTitleBar = new DefaultTitleBar(mParams);
            return defaultTitleBar;
        }

        /**
         * 只关注内容参数，动态改变属性时刻通过getView得到具体View去操作，简单操作时刻通过viewHolder去操作
         */
        public static class DefaultParams extends AbsTitleBar.Builder.Params {
            public int backgroundColor = R.color.title_bar_bg_color;//底部分割线是否可见

            public boolean bottomViewVisible = false;//底部分割线是否可见
            public int bottomViewColor = R.color.title_bar_bottom_divider_color;//底部分割线默认颜色

            public String leftText;//左侧文字，为空时不显示
            public int leftTextSize = R.dimen.title_bar_lr_font_size;//左边文字大小，不指定时采用默认
            public int leftTextColor = R.color.title_bar_main_text_color;//左边文字颜色

            public boolean leftImageShow = true;//左按钮是否显示
            public int leftImageResId = R.mipmap.ic_back_white;//左侧按钮图片

            public String title;//中间标题，为空不显示
            public int titleTextSize = R.dimen.title_bar_title_font_size;//中间标题大小
            public int titleTextColor = R.color.title_bar_main_text_color;//中间标题颜色

            public String subTitle;//子标题，为空不显示
            public int subTitleTextSize = R.dimen.title_bar_lr_font_size;//子标题大小
            public int subTitleTextColor = R.color.title_bar_main_text_color;//子标题颜色

            public int centerImage = 0;//中间图片标题，为0时不显示

            public String rightText;//右边按钮文字，为空不显示
            public int rightTextSize = R.dimen.title_bar_lr_font_size;//右边文字大小
            public int rightTextColor = R.color.title_bar_main_text_color;//右边文字颜色
            public int rightTextBackground = 0;//右边文字背景，为0时不设置，一般是selector文件

            public int rightImageResId1;//右边第一个图片按钮的资源文件，为0时不显示
            public int rightImageResId2;//右边第二个图片按钮的资源文件，为0时不显示

            public boolean supportImmersion = true;//是否支持沉浸式
            public int immersionColorId;//是否有着色栏颜色，为0时不设置
            public int darkModeStartBar = 1;//0 不改  1 深色图标 2浅色图标

            public View.OnClickListener leftClickListner;//左边区域统一一个点击事件，一般为返回
            public View.OnClickListener centerAreaClickLinstener;//中间标题区域的点击事件
            public View.OnClickListener rightTextClickListner;//右边文字的点击事件
            public View.OnClickListener rightImage1ClickListner;//右边第一个图片的点击事件
            public View.OnClickListener rightImage2ClickListner;//右边第二个图片的点击事件

            public DefaultParams(Context context, ViewGroup parent, int layoutId) {
                super(context, parent, layoutId);
            }

            public DefaultParams(Context context, ViewGroup parent, int layoutId, int position) {
                super(context, parent, layoutId, position);
            }
        }
    }
}
