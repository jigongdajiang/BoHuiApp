package com.bohui.art.mine.upload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bohui.art.R;
import com.bohui.art.bean.mine.UploadResult;
import com.bohui.art.common.app.AppParams;
import com.bohui.art.common.app.SharePreferenceKey;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseView;
import com.framework.core.cache.core.CacheCoreFactory;
import com.framework.core.glideext.GlideApp;
import com.framework.core.http.callback.ProgressResponseCallBack;
import com.framework.core.http.exception.ApiException;
import com.framework.core.log.PrintLog;
import com.framework.core.util.StrOperationUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/1
 * @description: 账户页面 图片加载统一处理
 */


public class UpLoadUtil extends AbsHelperUtil implements
        PopupWindowPhotoHelper.OnTakePhotoListener,
        PopupWindowPhotoHelper.UpLoadImgListener,
        UpLoadContact.View {

    private PopupWindowPhotoHelper photoHelper;
    private RxPermissions rxPermissions;
    private ImageView im_account_touxiang;
    private UpLoadPresenter upLoadPresenter;
    private UpLoadModel upLoadModel;

    public UpLoadUtil(Object baseContext, ImageView im_account_touxiang) {
        super(baseContext);
        this.im_account_touxiang = im_account_touxiang;
        if (upLoadPresenter == null) {
            upLoadPresenter = new UpLoadPresenter();
            upLoadPresenter.mContext = getContext();
        }
        if (upLoadModel == null) {
            upLoadModel = new UpLoadModel();
            upLoadModel.setProgressResponseCallBack(new ProgressResponseCallBack() {
                @Override
                public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {

                }
            });
        }
        upLoadPresenter.setMV(upLoadModel, this);
    }

    public void changeAvr() {
        //先检查权限是否有读写权限
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        String tag = "rxPermission";
                        if (permission.granted) {
                            // 用户已经同意该权限
                            photoHelper = PopupWindowPhotoHelper.getInstance(mBaseContext);
                            photoHelper.setOnTakePhotoListener(UpLoadUtil.this);
                            photoHelper.setUpLoadImgListener(UpLoadUtil.this);
                            //显示弹窗
                            photoHelper.showPhotoPop(im_account_touxiang);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            PrintLog.d(tag, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            PrintLog.d(tag, permission.name + " is denied.");
                        }
                    }
                });
    }

    @Override
    public void onTakePhoto() {
        //只有选择拍照是才会回调此方法
        //检查是否有相机操作权限
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }
        rxPermissions.requestEach(Manifest.permission.CAMERA).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                String tag = "rxPermission";
                if (permission.granted) {
                    // 用户已经同意该权限
                    photoHelper.takePhoto();
                    AppParams.getInstance().hasStartCarma = true;
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    PrintLog.d(tag, permission.name + " is denied. More info should be provided.");
                } else {
                    // 用户拒绝了该权限，并且选中『不再询问』
                    PrintLog.d(tag, permission.name + " is denied.");
                }
            }
        });
    }

    /**
     * 相册回调
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //从相机回来
        if (resultCode == Activity.RESULT_OK) {
            if (photoHelper != null) {
                photoHelper.doPhoto(requestCode, data);
            }
        }
        if (((requestCode == PopupWindowPhotoHelper.SELECT_PIC_BY_TACK_PHOTO || requestCode == PopupWindowPhotoHelper.SELECT_PIC_BY_PICK_PHOTO) && resultCode != Activity.RESULT_OK)
                || requestCode == PopupWindowPhotoHelper.PHOTO_RESOULT) {
            // 说明取消了拍照和相册选择,或者是已经完成了裁剪
            if (AppParams.getInstance().hasStartCarma) {
                AppParams.getInstance().hasStartCarma = false;
            }
        }
    }


    @Override
    public void upLoad(File[] files) {
        upLoadPresenter.upLoad(files[0]);
    }

    @Override
    public void upLoadSuccess(UploadResult uploadResult) {
        String path = uploadResult.getPath();
        if (!StrOperationUtil.isEmpty(path)) {
            refreshImgAvr(path);
            if (upLoadSuccessListener != null) {
                upLoadSuccessListener.uploadSuccess(path);
            }
        }
    }

    public interface UpLoadSuccessListener {
        void uploadSuccess(String path);
    }

    private UpLoadSuccessListener upLoadSuccessListener;

    public void setUpLoadSuccessListener(UpLoadSuccessListener upLoadSuccessListener) {
        this.upLoadSuccessListener = upLoadSuccessListener;
    }

    public void refreshImgAvr(final String path) {
        if (!StrOperationUtil.isEmpty(path)) {
            SimpleTarget target = new SimpleTarget<BitmapDrawable>() {
                @Override
                public void onResourceReady(BitmapDrawable resource, Transition transition) {
                    im_account_touxiang.setImageDrawable(resource);
                    //图片真正加载成功
                    CacheCoreFactory.getPreferenceCache(getContext()).save(SharePreferenceKey.ACCOUNT_HEAD_URL, path);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                }
            };
            GlideApp.with(getActivity()).load(path).transform(new CircleCrop()).error(R.mipmap.ic_default_logo).into(target);
        }
    }

    @Override
    public boolean handleException(String apiName, ApiException e) {
        if (isBaseActivity() && change2Activity() instanceof BaseView) {
            ((BaseView) change2Activity()).handleException(apiName, e);
        } else if (isBaseFragment() && change2Fragment() instanceof BaseView) {
            ((BaseView) change2Fragment()).handleException(apiName, e);
        }
        return false;
    }
}