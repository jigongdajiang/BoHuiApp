package com.framework.core.toast;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.core.R;

/**
 * @author gaojigong
 * @version V1.0
 * @Description: 自定义Toast
 * @date 17/4/12
 */
public class EToast {
    private final int ANIMATION_DURATION = 300;
    private static EToast result;
    private TextView mTextView;
    private ViewGroup container;
    private View v;

    private int mDuration = 1500;

    private LinearLayout mContainer;
    private AlphaAnimation mFadeOutAnimation;
    private AlphaAnimation mFadeInAnimation;

    private boolean isShow = false;
    private Context mContext;
    private Handler mHandler = new Handler();
    private String TOAST_TAG = "EToast_Log";

    private EToast(Context context) {
        mContext = context;
        container = (ViewGroup) ((Activity) context)
                .findViewById(android.R.id.content);
        View viewWithTag = container.findViewWithTag(TOAST_TAG);
        if(viewWithTag == null){
            v = ((Activity) context).getLayoutInflater().inflate(
                    R.layout.etoast, container);
            v.setTag(TOAST_TAG);
        }else{
            v = viewWithTag;
        }
        mContainer = (LinearLayout) v.findViewById(R.id.mbContainer);
        mContainer.setVisibility(View.GONE);
        mTextView = (TextView) v.findViewById(R.id.mbMessage);
    }

    public static EToast makeText(Context context, CharSequence message, int duration) {
        if(result == null){
            result = new EToast(context);
        }else{
            if(!result.mContext.getClass().getName().equals(context.getClass().getName())){
                result = new EToast(context);
            }
        }
        if(duration == Toast.LENGTH_LONG){
            result.mDuration = 2500;
        }else if(duration == Toast.LENGTH_SHORT){
            result.mDuration = 1500;
        }else{
            if(duration < 1500){
                result.mDuration = 1500;
            }else{
                result.mDuration = duration;
            }
        }
        result.mTextView.setText(message);
        return result;
    }
    public void show() {
        if(isShow){
            return;
        }
        isShow = true;
        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnimation.setDuration(ANIMATION_DURATION);
        mFadeOutAnimation
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        isShow = false;
                        mContainer.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
        mContainer.setVisibility(View.VISIBLE);

        mFadeInAnimation.setDuration(ANIMATION_DURATION);

        mContainer.startAnimation(mFadeInAnimation);
        mHandler.postDelayed(mHideRunnable, mDuration);
    }

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mContainer.startAnimation(mFadeOutAnimation);
        }
    };
    public void invisible(){
        isShow = false;
        mContainer.setVisibility(View.GONE);
        mHandler.removeCallbacks(mHideRunnable);
        mFadeInAnimation.cancel();
        mFadeOutAnimation.cancel();
    }
    public void destory(){
        invisible();
        result = null;
        mContext = null;
    }
    public void setText(CharSequence s){
        if(result == null) return;
        TextView mTextView = (TextView) v.findViewById(R.id.mbMessage);
        if(mTextView == null) throw new RuntimeException("This Toast was not created with Toast.makeText()");
        mTextView.setText(s);
    }
    public void setText(int resId) {
        setText(mContext.getText(resId));
    }

    public void setDuration(int duration) {
        if(duration == Toast.LENGTH_LONG){
            this.mDuration = 2000;
        }else if(duration == Toast.LENGTH_SHORT){
            this.mDuration = 1500;
        }else{
            if(duration < 1500){
                this.mDuration = 1500;
            }else{
                this.mDuration = duration;
            }
        }
    }
}
