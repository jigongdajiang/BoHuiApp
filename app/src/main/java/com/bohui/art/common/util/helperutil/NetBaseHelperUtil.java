package com.bohui.art.common.util.helperutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.bohui.art.R;
import com.bohui.art.bean.mine.CheckVersionResult;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.dialog.DialogFactory;
import com.bohui.art.mine.setting.UpdateHelper;
import com.bohui.art.mine.setting.mvp.SettingContact;
import com.bohui.art.start.MainActivity;
import com.bohui.art.start.reg.RegActivity;
import com.bohui.art.start.stop.StopServiceActivity;
import com.framework.core.app.AtyManager;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.JsonUtils;
import com.framework.core.util.ResUtil;

import org.json.JSONObject;

/**
 * @author : gaojigong
 * @date : 2017/11/30
 * @description:
 */


public class NetBaseHelperUtil extends AbsBaseHelperUtil {
    public NetBaseHelperUtil(Object baseContext) {
        super(baseContext);
    }

    public boolean handleException(String tag, ApiException e) {
        switch (e.getCode()) {
            case ApiException.SERVERERROR.FORCE_RE_LOGIN: // 重新登录 被挤下线 退出登录后 进入未登录首页
                if (SettingContact.TAG_CHECK_VERSION.equals(tag)) {
                    return true;
                }
                forceRelogin(e);
                return true;
            case ApiException.SERVERERROR.FORCE_UPDATE: //强制升级
                forceUpdate(e);
                return true;
            case ApiException.SERVERERROR.RE_LOGIN:// 重新登录 退出登录后  进入注册页
                if (SettingContact.TAG_CHECK_VERSION.equals(tag)) {
                    return true;
                }
                reLogin(e);
                return true;
            case ApiException.SERVERERROR.SIGNATURE_FAILURE:
                showMsgDialg(e.getDisplayMessage());
                return true;
            case ApiException.ERROR.NETWORD_ERROR://无网络
                netWordError(e);
                return true;
            case ApiException.ERROR.TIMEOUT_ERROR://网络
            case ApiException.ERROR.UNKNOWNHOST_ERROR:
            case ApiException.ERROR.HTTP_ERROR:
            case ApiException.ERROR.SERVER_NULL_JSON:
            case ApiException.HTTP_ERROR.BADREQUEST:
            case ApiException.HTTP_ERROR.UNAUTHORIZED:
            case ApiException.HTTP_ERROR.FORBIDDEN:
            case ApiException.HTTP_ERROR.NOT_FOUND:
            case ApiException.HTTP_ERROR.METHOD_NOT_ALLOWED:
            case ApiException.HTTP_ERROR.REQUEST_TIMEOUT:
            case ApiException.HTTP_ERROR.INTERNAL_SERVER_ERROR:
            case ApiException.HTTP_ERROR.BAD_GATEWAY:
            case ApiException.HTTP_ERROR.SERVICE_UNAVAILABLE:
            case ApiException.HTTP_ERROR.GATEWAY_TIMEOUT:
                netError(e);
                return true;
            case ApiException.HTTP_ERROR.STOP_SERVICE:
                if (SettingContact.TAG_CHECK_VERSION.equals(tag)) {
                    return true;
                }
                stopService();
                return true;
            case ApiException.ERROR.UNKNOWN://所有未知异常不做处理，防止奇怪的弹窗
                return true;
            case ApiException.SERVERERROR.REQUEST_AGING://重复请求多次 不做处理
                return true;
            case ApiException.SERVERERROR.REQUEST_MORE://频繁请求多次 不做处理
                return true;
            default://默认不做处理，需要在下层单独处理
                return false;

        }
    }

    private void stopService() {
        if (!AtyManager.getInstance().isContainActivity(StopServiceActivity.class)) {
            startAty(StopServiceActivity.class);
        }
    }

    private void netError(ApiException e) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final Activity context = getActivity();
        if (context == null || fragmentManager == null) {
            return;
        }
        showMsgDialg("网络错误，请稍后重试");
    }

    private void forceUpdate(ApiException e) {
        JSONObject jsonObject = e.getErrorData();
        if (!JsonUtils.joEmtity(jsonObject)) {
            String json = jsonObject.toString();
            CheckVersionResult versionUpdate = (CheckVersionResult) JsonUtils.fromJson(json, CheckVersionResult.class);
            if (null != versionUpdate) {
                versionUpdate.setStatus(2);
                UpdateHelper updateHelper = new UpdateHelper(getActivity(), versionUpdate);
                updateHelper.handleUpdate();

            }
        }
    }

    private void reLogin(ApiException e) {
        final Activity context = getActivity();
        //退出登录
        AppFuntion.staticLogout(context);
        //跳转到注册页
        Bundle bundle = new Bundle();
        bundle.putBoolean("logout", true);
        startAty(RegActivity.class, bundle);
    }

    private void forceRelogin(ApiException e) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final Activity context = getActivity();
        if (context == null || fragmentManager == null) {
            return;
        }
        AppFuntion.staticLogout(context);
        DialogFactory
                .createDefalutMessageDialog(context, getSupportFragmentManager(),
                        ResUtil.getResString(context, R.string.prompt),
                        e.getDisplayMessage(),
                        null,
                        ResUtil.getResString(context, R.string.ok),
                        null,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //退出登录
                                AppFuntion.staticLogout(context);
                                //进入未登录首页
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("logout", true);
                                startAty(MainActivity.class, bundle);
                            }
                        },
                        0).showDialog();
    }

    private void netWordError(ApiException e) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        final Activity context = getActivity();
        if (context == null || fragmentManager == null) {
            return;
        }
        DialogFactory
                .createDefalutMessageDialog(context, fragmentManager,
                        ResUtil.getResString(context, R.string.prompt),
                        e.getDisplayMessage(),
                        ResUtil.getResString(context, R.string.cancel),
                        ResUtil.getResString(context, R.string.network),
                        null,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            }
                        },
                        0).showDialog();
    }
}
