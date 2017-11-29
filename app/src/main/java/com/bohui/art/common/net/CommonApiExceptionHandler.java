package com.bohui.art.common.net;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.framework.core.http.exception.AbsApiExceptionHandler;
import com.framework.core.http.exception.ApiException;
import com.bohui.art.common.widget.dialog.DialogFactory;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 */


public class CommonApiExceptionHandler extends AbsApiExceptionHandler {
    private Object mContext;

    public CommonApiExceptionHandler(Object context) {
        if(context == null || (!(context instanceof AppCompatActivity) && !(context instanceof Fragment))){
            throw new IllegalArgumentException("context must AppCompatActivity or Fragment");
        }
        this.mContext = context;
    }

    @Override
    public boolean selfExceptionHandle(ApiException e) {
        int code = e.getCode();
        switch (code){
            case ApiResultErrorCode.RELOGIN:
                return handleReloginException(e);
            default:
                showMsgDialog(e);
                return true;
        }
    }

    private boolean handleReloginException(ApiException e) {
        return false;
    }

    private void showMsgDialog(ApiException e) {
        if(mContext instanceof AppCompatActivity){
            DialogFactory.createDefalutMessageDialog(
                    (AppCompatActivity)mContext,
                    ((AppCompatActivity)mContext).getSupportFragmentManager(),
                    e.getMessage())
                    .showDialog();
        }else{
            DialogFactory.createDefalutMessageDialog(
                    ((Fragment)mContext).getActivity(),
                    ((Fragment)mContext).getChildFragmentManager(),
                    e.getMessage())
                    .showDialog();
        }
    }
}
