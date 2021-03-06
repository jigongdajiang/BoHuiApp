package com.widget.grecycleview.listener;

import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.adapter.wrapper.BaseWrapper;
import com.widget.grecycleview.viewholder.BaseViewHolder;

import java.util.Set;

/**
 * Created by AllenCoder on 2016/8/03.
 * <p>
 * This can be useful for applications that wish to implement various forms of click and longclick and childView click
 * manipulation of item views within the RecyclerView. SimpleClickListener may intercept
 * a touch interaction already in progress even if the SimpleClickListener is already handling that
 * gesture stream itself for the purposes of scrolling.
 *
 * @see RecyclerView.OnItemTouchListener
 */
public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;
    public static String TAG = "SimpleClickListener";
    private boolean mIsPrepressed = false;
    private boolean mIsShowPress = false;
    private View mPressedView = null;
    private DelegateAdapter adapter;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        if (recyclerView == null) {
            this.recyclerView = rv;
            mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(recyclerView));
            this.adapter = (DelegateAdapter) recyclerView.getAdapter();
        }
        if (!mGestureDetector.onTouchEvent(e) && e.getActionMasked() == MotionEvent.ACTION_UP && mIsShowPress) {
            if (mPressedView != null) {
                mPressedView.setPressed(false);
                mPressedView = null;
            }
            mIsShowPress = false;
            mIsPrepressed = false;


        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        private RecyclerView recyclerView;

        @Override
        public boolean onDown(MotionEvent e) {
            mIsPrepressed = true;
            mPressedView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                /**
                 * when   click   Outside the region  ,mPressedView is null
                 */
                if (mPressedView != null && mPressedView.getBackground() != null) {
                    mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
                }
            }
            super.onDown(e);
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
                mPressedView.setPressed(true);
                mIsShowPress = true;
            }
            super.onShowPress(e);
        }

        public ItemTouchHelperGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mIsPrepressed && mPressedView != null) {
                mPressedView.setPressed(true);
                final View pressedView = mPressedView;
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(pressedView);
                Set<Integer> childClickViewIds = vh.getChildClickViewIds();
                if (childClickViewIds != null && childClickViewIds.size() > 0) {
                    for (Integer childClickViewId : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId);
                        if (inRangeOfView(childView, e) && childView.isEnabled()) {
                            int position = vh.getLayoutPosition();
//                            if(adapter instanceof BaseWrapper){
//                                BaseWrapper tempAdapter = (BaseWrapper) adapter;
//                                if(tempAdapter.getCheckItemManager().checkPosition(position)){
//                                    int dataPosition = tempAdapter.getCheckItemManager().getAfterCheckingPosition(position);
//                                    onItemChildClick(adapter,childView, dataPosition);
//                                }
//                            }else{
//                                onItemChildClick(adapter,childView, position);
//                            }
                            if(!childView.hasOnClickListeners()){
                                onItemChildClick((BaseAdapter)adapter.findAdapterByPosition(position).second,childView, adapter.findOffsetPosition(position));
                            }
                            resetPressedView(pressedView);
                            return true;
                        }
                    }

                }
                int position = vh.getLayoutPosition();
//                if(adapter instanceof BaseWrapper){
//                    BaseWrapper tempAdapter = (BaseWrapper) adapter;
//                    if(tempAdapter.getCheckItemManager().checkPosition(position)){
//                        int dataPosition = tempAdapter.getCheckItemManager().getAfterCheckingPosition(position);
//                        onItemClick(adapter,pressedView, dataPosition);
//                    }
//                }else{
//                    onItemClick(adapter,pressedView, position);
//                }
                if(!pressedView.hasOnClickListeners()){
                    onItemClick((BaseAdapter)adapter.findAdapterByPosition(position).second,pressedView, adapter.findOffsetPosition(position));
                }
                resetPressedView(pressedView);
            }
            return false;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 100);
            }

            mIsPrepressed = false;
            mPressedView = null;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (mIsPrepressed && mPressedView != null) {
                BaseViewHolder vh = (BaseViewHolder) recyclerView.getChildViewHolder(mPressedView);

                Set<Integer> longClickViewIds = vh.getItemChildLongClickViewIds();
                if (longClickViewIds != null && longClickViewIds.size() > 0) {
                    for (Integer longClickViewId : longClickViewIds) {
                        View childView = mPressedView.findViewById(longClickViewId);
                        if (inRangeOfView(childView, e) && childView.isEnabled()) {
                            int position = vh.getLayoutPosition();
//                            if(adapter instanceof BaseWrapper){
//                                BaseWrapper tempAdapter = (BaseWrapper) adapter;
//                                if(tempAdapter.getCheckItemManager().checkPosition(position)){
//                                    int dataPosition = tempAdapter.getCheckItemManager().getAfterCheckingPosition(position);
//                                    onItemChildLongClick(adapter,childView, dataPosition);
//                                }
//                            }else{
//                                onItemChildLongClick(adapter,childView, position);
//                            }
                            if(!childView.hasOnClickListeners()){
                                onItemChildLongClick((BaseAdapter)adapter.findAdapterByPosition(position).second,childView, adapter.findOffsetPosition(position));
                            }
                            mPressedView.setPressed(true);
                            mIsShowPress = true;
                            isChildLongClick = true;
                            break;
                        }
                    }
                }
                if (!isChildLongClick) {
                    int position = vh.getLayoutPosition();
//                    if(adapter instanceof BaseWrapper){
//                        BaseWrapper tempAdapter = (BaseWrapper) adapter;
//                        if(tempAdapter.getCheckItemManager().checkPosition(position)){
//                            int dataPosition = tempAdapter.getCheckItemManager().getAfterCheckingPosition(position);
//                            onItemLongClick(adapter,mPressedView, dataPosition);
//                        }
//                    }else{
//                        onItemLongClick(adapter,mPressedView, position);
//                    }
                    if(!mPressedView.hasOnClickListeners()){
                        onItemLongClick((BaseAdapter)adapter.findAdapterByPosition(position).second,mPressedView, adapter.findOffsetPosition(position));
                    }
                    mPressedView.setPressed(true);
                    mIsShowPress = true;
                }
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     */
    public abstract void onItemClick(BaseAdapter adapter,View view, int position);

    /**
     * callback method to be invoked when an item in this view has been
     * click and held
     *
     * @param view     The view whihin the AbsListView that was clicked
     * @param position The position of the view int the adapter
     * @return true if the callback consumed the long click ,false otherwise
     */
    public abstract void onItemLongClick(BaseAdapter adapter,View view, int position);

    public abstract void onItemChildClick(BaseAdapter adapter,View view, int position);

    public abstract void onItemChildLongClick(BaseAdapter adapter,View view, int position);

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view.getVisibility() != View.VISIBLE) {
            return false;
        }
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return !(ev.getRawX() < x
                || ev.getRawX() > (x + view.getWidth())
                || ev.getRawY() < y
                || ev.getRawY() > (y + view.getHeight()));
    }

}


