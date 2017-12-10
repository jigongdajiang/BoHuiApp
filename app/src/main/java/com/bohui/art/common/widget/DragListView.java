package com.bohui.art.common.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * @author : gongdaocai
 * @date : 2017/8/27
 * FileName:
 * @description:
 */


public class DragListView extends LinearLayout {
    private View mMenuView;
    private View mDragView;
    private ViewDragHelper mDragHelper;

    private int mMenuHeight;
    private int mDragViewHeight;
    private int mDragMarginTop;

    //当前菜单是打开还是关闭
    private boolean mMenuIsOpen = true;

    public DragListView(@NonNull Context context) {
        this(context, null);
    }

    public DragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragListView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        mDragHelper = ViewDragHelper.create(this, 1.0f, mCallBack);
    }

    private ViewDragHelper.Callback mCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (mDragView == child) {
                //top 能移动到的范围
                // 这里 0 ~ menuHeight
                if (top < 0) {
                    top = 0;
                }
                if (top > mMenuHeight) {
                    top = mMenuHeight;
                }
                return top;
            }
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mDragView) {
                //抬起是，当前dragView的顶部位置
                int top = mDragView.getTop();
                if (top > mMenuHeight / 2) {
                    //让dragView到指定位置
                    mDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    mMenuIsOpen = true;
                } else {
                    mDragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (mOnChangeListener != null) {
                mOnChangeListener.change(changedView,top,dy,mMenuHeight);
            }
        }
    };

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private float mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果菜单打开全部拦截
//        if(mMenuIsOpen){
//            return true;
//        }
        //down事件不能被ListView强制吸走
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                //向下时，如果没到顶部则不拦截，如果到了顶部则拦截
                if ((mMenuIsOpen && (Math.abs(ev.getY() - mDownY) > 10)) || (ev.getY() - mDownY > 10 && !canChildScrollUp())) {
                    //向下
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     * 判断View是否滚动到了最顶部,还能不能向上滚
     */
    public boolean canChildScrollUp() {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mDragView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mDragView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mDragView, -1) || mDragView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mDragView, -1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mDragView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mMenuHeight = mMenuView.getMeasuredHeight();
        if (mDragViewHeight == 0) {
            mDragViewHeight = mDragView.getMeasuredHeight();
            LayoutParams params = (LayoutParams) mDragView.getLayoutParams();
            mDragMarginTop = params.topMargin;
            params.height = mDragViewHeight + mMenuHeight;
            mDragView.setLayoutParams(params);
        }
    }

    public interface OnChangeListener {
        void change(View changedView, int top, int dy, int menuHeight);
    }

    private OnChangeListener mOnChangeListener;

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }
}
