package com.widget.smallelement.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.log.PrintLog;
import com.framework.core.util.ReflectionUtils;
import com.widget.smallelement.viewholder.LayoutViewHolder;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2017/6/15
 * @description: MyApplication.params  这是一个配套的参数，如果有横竖屏要求，则必须要有
 * <p>
 * 这里点击事件采用在Dialog层设置，为的是点击回调中能正常使用Dialog，以及能自动消失
 */


public class BasePowfullDialog extends DialogFragment implements DialogInterface.OnKeyListener {
    public static final String DIALOG_PARAMS_MEMORY_KEY = "dialog_params_key";
    public static final String DIALOG_TAG = "base_dialog_tag";//默认Tag
    public static final String LOADING_TAG = "loading_dialog_tag";
    private static final String TAG = "BaseDialog";


    private Builder.Params mParams;

    public void setParams(Builder.Params params) {
        this.mParams = params;
    }


    public BasePowfullDialog() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showDialogLife("onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDialogLife("onCreate");
        //如果重构，如横竖屏切换则从内存缓存中获取
        if (null != savedInstanceState &&
                CacheCoreFactory.getMemoryCache().load(BasePowfullDialog.Builder.Params.class, DIALOG_PARAMS_MEMORY_KEY) != null && mParams == null) {
            mParams = CacheCoreFactory.getMemoryCache().load(BasePowfullDialog.Builder.Params.class, DIALOG_PARAMS_MEMORY_KEY);
        }
        CacheCoreFactory.getMemoryCache().remove(DIALOG_PARAMS_MEMORY_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showDialogLife("onCreateView");
        //去掉默认标题区域
        if (mParams.windowFeature >= 0)
            getDialog().requestWindowFeature(mParams.windowFeature);

        //背景
        if (null != mParams.backGroundDrawable)
            getDialog().getWindow().setBackgroundDrawable(mParams.backGroundDrawable);

        //与软键盘能共存
        if(mParams.canExistWidthSoft){
            getDialog().getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                    WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        }

        //点击屏幕Dialog是否消失
        getDialog().setCanceledOnTouchOutside(mParams.canceledOnTouchOutside);

        //进出时动画
        if (mParams.dialogAnim > 0){
            //必须是style不能是anim 否则无效
            getDialog().getWindow().getAttributes().windowAnimations = mParams.dialogAnim;
        }

        getDialog().setCancelable(mParams.cancelable);

        //弹出方向
        if (mParams.gravity == Gravity.LEFT
                || mParams.gravity == Gravity.TOP
                || mParams.gravity == Gravity.RIGHT
                || mParams.gravity == Gravity.BOTTOM) {
            // 设置宽度为屏宽, 靠近屏幕底部。
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = mParams.gravity;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
            window.setAttributes(lp);
        }

        View view = mParams.layoutViewHolder.getConvertView();
        //一旦显示后，缓存的View会关联到ContentView上，也就是说会有Parent，会导致程序崩溃，这里需要先移除
        if (view != null && view.getParent() != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        showDialogLife("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        //键盘点击事件 如果制定了要在外部处理，且 明却指定点击返回取消对话框时
        if (null != mParams.onKeyListener) {
            getDialog().setOnKeyListener(mParams.onKeyListener);
        } else {
            getDialog().setOnKeyListener(this);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        showDialogLife("onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        showDialogLife("onStart");
        //dialog占屏幕的比例
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        if (mParams.dialogWidthForScreen > 0) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            width = (int) (dm.widthPixels * mParams.dialogWidthForScreen);
        }
        if(mParams.dialogHeightForScreen > 0){
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            height = (int) (dm.heightPixels * mParams.dialogHeightForScreen);
        }
        getDialog().getWindow().setLayout(width, height);

    }

    @Override
    public void onResume() {
        super.onResume();
        showDialogLife("onResume");
        mParams.currentDialog = getDialog();
    }

    @Override
    public void onPause() {
        super.onPause();
        showDialogLife("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showDialogLife("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        showDialogLife("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showDialogLife("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        showDialogLife("onDetach");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        showDialogLife("onSaveInstanceState");
        CacheCoreFactory.getMemoryCache().save(DIALOG_PARAMS_MEMORY_KEY, mParams);
    }

    /**
     * DialogFragment本身实现了两个监听
     * 如果需要监控onCancel或者onDismiss时从这里回调即可
     *
     * DialogFragment是在onActivityCreated中设置的这两个监听
     * 如果想要自己实现也可在之后重新指定为自己的
     *
     * 这里我们采用第一种方式，重写方法进行回调
     * @param dialog
     */
    @Override
    public void onCancel(DialogInterface dialog) {
        showDialogLife("onCancel");
        super.onCancel(dialog);
        if(mParams.onCancelListener != null){
            mParams.onCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        showDialogLife("onDismiss");
        super.onDismiss(dialog);
        if (mParams.onDismissListener != null) {
            mParams.onDismissListener.onDismiss(dialog);
        }
    }
    private void showDialogLife(String method) {
        PrintLog.i(TAG, method);
    }


    /**
     * 用于外部使用，因为点击事件如果回调中要使用Dialog本身会报语法错误，所以建议点击事件在构建后单独配置
     *
     * @param id
     * @param listener
     * @param autoDimss 能在处理完点击事件后自动消失
     * @return
     */
    public BasePowfullDialog setViewOnClickListener(int id, final View.OnClickListener listener, boolean autoDimss) {
        if (id > 0)
            mParams.layoutViewHolder.setViewOnClickListener(id, autoDimss ? new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    miss();
                    if (listener != null) {
                        listener.onClick(v);
                    }
                }
            } : listener);
        return this;
    }

    public BasePowfullDialog setViewOnClickListener(int id, final View.OnClickListener listener) {
        return setViewOnClickListener(id, listener, true);
    }

    /**
     * 指定点击消失
     *
     * @param id
     * @return
     */
    public BasePowfullDialog setViewClickCancel(int id) {
        if (id > 0)
            mParams.layoutViewHolder.setViewOnClickListener(id, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    miss();
                }
            });
        return this;
    }

    /**
     * 用于更新内容
     *
     * @param id
     * @param text
     * @return
     */
    public BasePowfullDialog setTextView(int id, String text) {
        if (id > 0) {
            mParams.layoutViewHolder.setTextViewText(id, text);
        }
        return this;
    }

    public BasePowfullDialog setDialogAnim(int animStyleId) {
        if (animStyleId != 0) {
            mParams.dialogAnim = animStyleId;
        }
        return this;
    }
    public BasePowfullDialog setDialogOnCancelListener(DialogInterface.OnCancelListener onCancelListener){
        if(onCancelListener != null){
            mParams.onCancelListener = onCancelListener;
        }
        return this;
    }

    public BasePowfullDialog setDialogCancelable(boolean cancelable){
        mParams.cancelable = cancelable;
        return this;
    }

    /**
     * 获取dialog中的控件，用于在外部进行操作，建议在dialog显示后进行
     *
     * @param id
     * @return
     */
    public View getInsideView(int id) {
        return mParams.layoutViewHolder.getView(id);
    }

    /**
     * 为了不重复显示dialog，在显示对话框之前移除正在显示的对话框。
     */
    public BasePowfullDialog showDialog() {
        if (showMust()) {
            mParams.fragmentManager.executePendingTransactions();
            FragmentTransaction ft = mParams.fragmentManager.beginTransaction();
            if (mParams.isCanCover) {
                this.show(ft, mParams.dialogTag);
            } else {
                DialogFragment fragment = (DialogFragment) mParams.fragmentManager.findFragmentByTag(mParams.dialogTag);
                if (fragment == null || !fragment.isAdded()) {
                    this.show(ft, mParams.dialogTag);
                }
            }
        }
        return this;
    }

    /**
     * 确保异步时还添加引起的崩溃
     *
     * @return
     */
    private boolean showMust() {
        return mParams.context != null
                && mParams.fragmentManager != null
                && mParams.context instanceof FragmentActivity
                && !isAdded()
                && !((FragmentActivity) mParams.context).isFinishing()
                && (null == getDialog() || !getDialog().isShowing());
    }

    /**
     * 防止由于异步导致的onSaveInstance崩溃
     *
     * @param transaction
     * @param tag
     * @return
     */
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        ReflectionUtils.setFieldValue(this, "mDismissed", false);
        ReflectionUtils.setFieldValue(this, "mShownByMe", true);
        transaction.add(this, tag);
        ReflectionUtils.setFieldValue(this, "mViewDestroyed", false);
        int backStackId = transaction.commitAllowingStateLoss();
        ReflectionUtils.setFieldValue(this, "mBackStackId", backStackId);
        return backStackId;
    }

    /**
     * 由外部控制消失，比如加载Dialog的消失
     */
    public void miss() {
        if (null != mParams && mParams.currentDialog != null) {
            FragmentActivity activity = getActivity();
            if (null != activity && !activity.isFinishing() && null != getDialog() && getDialog().isShowing()) {
                dismissAllowingStateLoss();
            }
        }
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if(mParams.cancelable){
            return false;
        }
        return keyCode == KeyEvent.KEYCODE_BACK;
    }

    /**
     * 链式构建器
     * 注意:链式过程中，在回调中是无法使用其最终才构建的对象的，因为这个时候不能确保最终组装完成
     */
    public static class Builder {
        Params mP;

        public Builder(int layoutId, Context context, FragmentManager fragmentManager) {
            mP = new Params(layoutId, context, fragmentManager);
        }

        public Builder setTextViewText(int id, String text) {
            if (id > 0)
                mP.layoutViewHolder.setTextViewText(id, text);
            return this;
        }
        public Builder setImageResId(int id, int resId) {
            if (id > 0)
                mP.layoutViewHolder.setImageRes(id, resId);
            return this;
        }

        public Builder setWindowFeature(int wf) {
            if (wf >= 0) {
                mP.windowFeature = wf;
            }
            return this;
        }

        public Builder setBackGroundDrawable(Drawable drawable) {
            if (null != drawable) {
                mP.backGroundDrawable = drawable;
            }
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean b) {
            mP.canceledOnTouchOutside = b;
            return this;
        }

        public Builder setDialogWidthForScreen(double d) {
            if (d > 0) {
                mP.dialogWidthForScreen = d;
            }
            return this;
        }
        public Builder setDialogHeightForScreen(double d) {
            if (d > 0) {
                mP.dialogHeightForScreen = d;
            }
            return this;
        }

        public Builder setDialogAnim(int animStyleId) {
            if (animStyleId != 0) {
                mP.dialogAnim = animStyleId;
            }
            return this;
        }

        public Builder setGravity(int gravity) {
            mP.gravity = gravity;
            return this;
        }

        public Builder setCanCover(boolean isCanCover) {
            mP.isCanCover = isCanCover;
            return this;
        }

        /**
         * 设置外部处理键盘响应事件，这里需要配合 isBackCancled  只有在指定isBackCancled=true时,该设置才会生效
         *
         * @param listener
         * @return
         */
        public Builder setDialogOnKeyListener(DialogInterface.OnKeyListener listener) {
            if (null != listener) {
                mP.onKeyListener = listener;
            }
            return this;
        }

        public Builder setDialogOnCDismissListener(DialogInterface.OnDismissListener listener) {
            if (null != listener) {
                mP.onDismissListener = listener;
            }
            return this;
        }

        public Builder setDialogOnCancelListener(DialogInterface.OnCancelListener listener) {
            if (null != listener) {
                mP.onCancelListener = listener;
            }
            return this;
        }
        public Builder setCancelable(boolean cancelable){
            mP.cancelable = cancelable;
            return this;
        }

        public Builder setDialogTag(String tag){
            mP.dialogTag = tag;
            return this;
        }

        public Builder isCanExistWidthSoft(boolean canExistWidthSoft){
            mP.canExistWidthSoft = canExistWidthSoft;
            return this;
        }
        public BasePowfullDialog builder() {
            BasePowfullDialog baseDialog = new BasePowfullDialog();
            baseDialog.setParams(mP);
            return baseDialog;
        }

        public static class Params implements Serializable {
            //用于存储最新的Dialog，因为整个Parmas是做了临时缓存的，而当横竖屏切换时，其实整个Fragment会重塑
            //所以在外界想直接操控最新的Dialog是不行的，通过参数缓存实时拥有最新的Dialog才能在外界操控，比如让其消失
            public Dialog currentDialog;
            public int layoutId;
            public Context context;
            public FragmentManager fragmentManager;
            public LayoutViewHolder layoutViewHolder;

            public int windowFeature = Window.FEATURE_NO_TITLE;
            public Drawable backGroundDrawable = new ColorDrawable(Color.TRANSPARENT);
            public boolean canceledOnTouchOutside = false;
            public double dialogWidthForScreen = 0.85;
            public double dialogHeightForScreen = 0;
            public int dialogAnim = 0;
            public int gravity;//弹出的位置
            public boolean isCanCover;//是否允许覆盖，默认不允许。
            public boolean cancelable = false;//按返回键是否可以取消，如果onKeyListener有特殊设置，可能会影响该参数的效果
            public DialogInterface.OnKeyListener onKeyListener;
            public DialogInterface.OnCancelListener onCancelListener;
            public DialogInterface.OnDismissListener onDismissListener;
            //指定Tag，与isCanCover配合，Tag相同，isCanCover=true 能叠加显示 isCanCover=false不能叠加显示
            //Tag不同 都能叠加显示
            //Loading框不能影响信息框的显示
            public String dialogTag = DIALOG_TAG;
            //是否能与软键盘共存 默认能
            public boolean canExistWidthSoft = true;

            public Params(int layoutId, Context context, FragmentManager fragmentManager) {
                this.layoutId = layoutId;
                this.context = context;
                this.fragmentManager = fragmentManager;
                this.layoutViewHolder = new LayoutViewHolder(context, layoutId);
            }
        }
    }
}
