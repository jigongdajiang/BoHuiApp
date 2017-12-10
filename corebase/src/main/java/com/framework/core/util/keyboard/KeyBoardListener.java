package com.framework.core.util.keyboard;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.framework.core.util.DisplayUtil;


/**
 * @author gaojigong
 * @version V1.0
 * @Description: 布局监听类，主要用于键盘弹出，关闭的监听
 *  使用的Activity要有 android:windowSoftInputMode="stateAlwaysHidden|adjustResize"方可生效
 *  不用考虑windowSoftInputMode属性与Activity是否全屏
 * @date 17/2/24
 */
public class KeyBoardListener {
    private View mRootView;//activity的根视图
    private int mRootViewVisibleHeight;//纪录根视图的显示高度
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;
    public KeyBoardListener(Activity activity) {
        //获取activity的根视图
        mRootView = activity.getWindow().getDecorView();
        final int keyBoardHeight = DisplayUtil.getScreenHeight(activity) / 3;
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        if(mOnGlobalLayoutListener == null){
            mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //获取当前根视图在屏幕上显示的大小
                    Rect r = new Rect();
                    mRootView.getWindowVisibleDisplayFrame(r);

                    int visibleHeight = r.height();
                    System.out.println(""+visibleHeight);
                    if (mRootViewVisibleHeight == 0) {
                        mRootViewVisibleHeight = visibleHeight;
                        return;
                    }

                    //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                    if (mRootViewVisibleHeight == visibleHeight) {
                        return;
                    }
                    //根视图显示高度变小超过200，可以看作软键盘显示了
                    if (mRootViewVisibleHeight - visibleHeight > keyBoardHeight) {
                        if (onSoftKeyBoardChangeListener != null) {
                            onSoftKeyBoardChangeListener.keyBoardShow(mRootViewVisibleHeight - visibleHeight);
                        }
                        mRootViewVisibleHeight = visibleHeight;
                        return;
                    }

                    //根视图显示高度变大超过200，可以看作软键盘隐藏了
                    if (visibleHeight - mRootViewVisibleHeight > keyBoardHeight) {
                        if (onSoftKeyBoardChangeListener != null) {
                            onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - mRootViewVisibleHeight);
                        }
                        mRootViewVisibleHeight = visibleHeight;
                        return;
                    }

                }
            };
        }
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    public void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);

        void keyBoardHide(int height);
    }

    public void onDestory(){
        if(mRootView != null && mOnGlobalLayoutListener != null){
            mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }
}
