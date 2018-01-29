package com.bohui.art.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.framework.core.log.PrintLog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2018/1/27
 * @description:
 */


public class CallUitl {
    public static void call(final Activity activity, final String serviceTel) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                String tag = "rxPermission";
                if (permission.granted) {
                    // 用户已经同意该权限
                    PrintLog.d(tag, permission.name + " is granted.");
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + serviceTel));
                    activity.startActivity(intent);
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
