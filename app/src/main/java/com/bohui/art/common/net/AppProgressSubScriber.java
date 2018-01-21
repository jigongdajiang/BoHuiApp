package com.bohui.art.common.net;

import android.content.Context;
import android.content.DialogInterface;

import com.framework.core.base.BaseView;
import com.framework.core.http.exception.ApiException;
import com.framework.core.http.subsciber.BaseSubscriber;
import com.framework.core.http.subsciber.ProgressCancelListener;
import com.widget.smallelement.dialog.BasePowfullDialog;

import java.lang.ref.WeakReference;


/**
 * @author : gaojigong
 * @date : 2017/12/12
 * @description:
 * 接入了Loading框的自动处理
 * 如果某个接口需要显示Loading框给该对象传入IBasePwProgressDialog的实现对象即可
 * 如果不需要则不用传，将自动不会显示Loading框
 */


public abstract class AppProgressSubScriber<T> extends BaseSubscriber<T> implements ProgressCancelListener {
    private WeakReference<IBasePwProgressDialog> progressDialogWeakReference;
    private WeakReference<BasePowfullDialog> mDialogWeakReference;
    private boolean isCancel;//对话框取消是否取消网络请求

    public AppProgressSubScriber() {
    }

    public AppProgressSubScriber(Context context) {
        super(context);
    }

    public AppProgressSubScriber(BaseView baseView, String apiTag) {
        super(baseView, apiTag);
    }

    public AppProgressSubScriber(Context context, BaseView baseView, String apiTag) {
        super(context, baseView, apiTag);
    }

    public AppProgressSubScriber(Context context, IBasePwProgressDialog pwProgressDialog) {
        super(context);
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        init(true);
    }

    public AppProgressSubScriber(BaseView baseView, String apiTag, IBasePwProgressDialog pwProgressDialog) {
        super(baseView, apiTag);
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        init(true);
    }

    public AppProgressSubScriber(Context context, BaseView baseView, String apiTag, IBasePwProgressDialog pwProgressDialog) {
        super(context, baseView, apiTag);
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        init(true);
    }

    public AppProgressSubScriber(IBasePwProgressDialog pwProgressDialog, boolean isCancel) {
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        this.isCancel = isCancel;
        init(this.isCancel);
    }

    public AppProgressSubScriber(Context context, IBasePwProgressDialog pwProgressDialog, boolean isCancel) {
        super(context);
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        this.isCancel = isCancel;
        init(this.isCancel);
    }

    public AppProgressSubScriber(BaseView baseView, String apiTag, IBasePwProgressDialog pwProgressDialog, boolean isCancel) {
        super(baseView, apiTag);
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        this.isCancel = isCancel;
        init(this.isCancel);
    }

    public AppProgressSubScriber(Context context, BaseView baseView, String apiTag, IBasePwProgressDialog pwProgressDialog, boolean isCancel) {
        super(context, baseView, apiTag);
        if(progressDialogWeakReference == null){
            this.progressDialogWeakReference = new WeakReference<>(pwProgressDialog);
        }
        this.isCancel = isCancel;
        init(this.isCancel);
    }

    /**
     * 初始化
     * @param isCancel 对话框是否可以取消
     */
    private void init(boolean isCancel) {
        if (progressDialogWeakReference == null || progressDialogWeakReference.get() == null) return;
        BasePowfullDialog dialog = progressDialogWeakReference.get().getLoadingDialog();
        if(dialog == null){
            return;
        }
        mDialogWeakReference = new WeakReference<>(dialog);
        dialog.setDialogCancelable(isCancel);
        if (isCancel) {
            dialog.setDialogOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    AppProgressSubScriber.this.onCancelProgress();
                }
            });
        }
    }
    /**
     * 展示进度框
     */
    private void showProgress() {
        if (mDialogWeakReference != null && mDialogWeakReference.get() != null) {
            mDialogWeakReference.get().showDialog();
        }
    }

    /**
     * 取消进度框
     */
    private void dismissProgress() {
        if (mDialogWeakReference != null && mDialogWeakReference.get() != null) {
            mDialogWeakReference.get().miss();
        }
    }

    @Override
    public void onStart() {
        String name = Thread.currentThread().getName();
        showProgress();
    }

    @Override
    public void onComplete() {
        dismissProgress();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgress();
        super.onError(e);
    }

    @Override
    public void onCancelProgress() {
        if (!isDisposed()) {
            dispose();
        }
    }
}
