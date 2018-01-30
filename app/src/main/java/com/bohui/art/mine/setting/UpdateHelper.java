package com.bohui.art.mine.setting;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.mine.CheckVersionResult;
import com.bohui.art.common.app.AppParams;
import com.bohui.art.common.widget.dialog.DialogFactory;
import com.framework.core.http.EasyHttp;
import com.framework.core.http.callback.DownloadProgressCallBack;
import com.framework.core.http.exception.ApiException;
import com.framework.core.log.PrintLog;
import com.framework.core.thinker.service.DownLoadPatchService;
import com.framework.core.toast.ToastShow;
import com.framework.core.util.AppInfoUtil;
import com.framework.core.util.FileUtil;
import com.framework.core.util.StrOperationUtil;
import com.framework.core.util.strformat.TimeFromatUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.widget.smallelement.dialog.BasePowfullDialog;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/27
 * @description:
 */


public class UpdateHelper {
    private FragmentActivity activity;
    private CheckVersionResult updateBean;
    public UpdateHelper(FragmentActivity activity, CheckVersionResult updateBean) {
        this.activity = activity;
        this.updateBean = updateBean;
    }

    public void handleUpdate() {
        if (updateBean.getStatus() == 1) {//1:普通更新
            normaUpdate();
        } else if ("2".equals(updateBean.getStatus())) {//2:强制更新
            forceUpdate();
        } else if ("3".equals(updateBean.getStatus())) {//3:补丁更新
            patchUpdate();
        }
    }

    private void patchUpdate() {
        final String patchUrl = updateBean.getUrl();
        if (!TextUtils.isEmpty(patchUrl)) {
            //请求读写权限
            RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    String tag = "rxPermission";
                    if (permission.granted) {
                        // 用户已经同意该权限
                        //启动下载服务下载补丁文件到本地，然后去更新补丁
                        DownLoadPatchService.startDownLoadPatchService(activity, patchUrl);
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
    }

    private void forceUpdate() {
        String content = updateBean.getDesc();
        String title = "版本更新";
        final String url = updateBean.getUrl();
        BasePowfullDialog basePowfullDialog = DialogFactory
                .createDefalutMessageDialog(
                        activity,
                        activity.getSupportFragmentManager(),
                        title,
                        content,
                        null,
                        "更新",
                        null,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissionForUpdateNormal(url);
                            }
                        },
                        0);
        TextView btn_dialog_left = (TextView) basePowfullDialog.getInsideView(R.id.tv_dialog_message);
        btn_dialog_left.setGravity(Gravity.LEFT);
        basePowfullDialog.showDialog();
    }

    private void normaUpdate() {
        BasePowfullDialog basePowfullDialog = DialogFactory.createDefalutMessageDialog(activity,
                activity.getSupportFragmentManager(),
                "版本更新",
                Html.fromHtml(updateBean.getDesc()).toString(), "取消", "更新", null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestPermissionForUpdateNormal(updateBean.getUrl());
                    }
                }, 0);
        TextView btn_dialog_left = (TextView) basePowfullDialog.getInsideView(R.id.tv_dialog_message);
        btn_dialog_left.setGravity(Gravity.LEFT);
        basePowfullDialog.showDialog();
    }

    private void requestPermissionForUpdateNormal(final String url) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                String tag = "rxPermission";
                if (permission.granted) {
                    // 用户已经同意该权限
                    startDownLoadAok(url);
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

    private void startDownLoadAok(String url) {
        if (url == null || url.trim().length() == 0) {
            ToastShow.showLong(activity, "更新失败，链接错误。");
            return;
        }
        String savePath = FileUtil.getCacheDir(activity) + "update/" + TimeFromatUtil.getDateByFormat(updateBean.getTime(),"YYYY-MM-dd-HH-mm-ss") + "/";
        String fileName = StrOperationUtil.md5(url) + ".apk";
        final String filepath = savePath + fileName;
        File file = new File(filepath);
        if (file.exists()) {
            installApk(file,activity);
            return;
        }
        final BasePowfullDialog updateDialog = new BasePowfullDialog.Builder(R.layout.dialog_update_progress, activity, activity.getSupportFragmentManager())
                .setCanCover(true)
                .builder();
        final TextView progress_message = (TextView) updateDialog.getInsideView(R.id.progress_message);
        final ProgressBar progressBar = (ProgressBar) updateDialog.getInsideView(R.id.progress);
        final TextView progress_percent = (TextView) updateDialog.getInsideView(R.id.progress_percent);
        final TextView progress_number = (TextView) updateDialog.getInsideView(R.id.progress_number);
        final String tag = "downLoadApk";
        EasyHttp.downLoad(url)
                .savePath(savePath)//默认在：/storage/emulated/0/Android/data/包名/files/1494647767055
                .saveName(fileName)//默认名字是时间戳生成的
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        PrintLog.d(tag, progress + "% ");
                        progressBar.setProgress(progress);
                        progress_percent.setText(progress + "% ");
                        progress_number.setText(100 + "");
                        if (done) {
                            progress_message.setText("下载完成");
                        }
                    }

                    @Override
                    public void onStart() {
                        PrintLog.d("======" + Thread.currentThread().getName());
                        updateDialog.showDialog();
                    }

                    @Override
                    public void onComplete(String path) {
                        updateDialog.miss();
                        File file = new File(filepath);
                        if (file.exists()) {
                            installApk(file,activity);
                        }
                    }

                    @Override
                    public void onError(final ApiException e) {
                        PrintLog.d(tag, "======" + Thread.currentThread().getName());
                        updateDialog.miss();
                    }
                });
    }

    public static final int INSTALL_UNKNOWN_APK_CODE = 0x9009;
    public static void installApk(final File file,final FragmentActivity fragmentActivity) {
        AppParams.getInstance().apkFilePath = file.getAbsolutePath();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //>8.0
            boolean haveInstallPermission = fragmentActivity.getPackageManager().canRequestPackageInstalls();
            if(haveInstallPermission){
                AppInfoUtil.install(file, fragmentActivity);
            }else{
                DialogFactory.createDefalutMessageDialog(fragmentActivity,
                        fragmentActivity.getSupportFragmentManager(),
                        "提示",
                        "安装应用需要打开未知来源权限，请去设置中开启权限", "取消", "去设置", null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                                fragmentActivity.startActivityForResult(intent, INSTALL_UNKNOWN_APK_CODE);
                            }
                        }, 0).showDialog();
            }
        } else {
            AppInfoUtil.install(file, fragmentActivity);
        }
    }
}
