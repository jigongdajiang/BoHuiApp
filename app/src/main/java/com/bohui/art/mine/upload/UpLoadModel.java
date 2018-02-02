package com.bohui.art.mine.upload;


import com.bohui.art.bean.mine.UploadResult;
import com.framework.core.http.EasyHttp;
import com.framework.core.http.callback.ProgressResponseCallBack;

import java.io.File;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2017/12/21
 * @description:
 */


public class UpLoadModel implements UpLoadContact.Model {
    private ProgressResponseCallBack progressResponseCallBack;

    public void setProgressResponseCallBack(ProgressResponseCallBack progressResponseCallBack) {
        this.progressResponseCallBack = progressResponseCallBack;
    }

    @Override
    public Observable<UploadResult> upLoad(long uid,File file) {
        return EasyHttp.post(UpLoadContact.URL_UPLOAD_HEAD_IMG)
                .params("uid",String.valueOf(uid))
                .params("file",file,progressResponseCallBack)
                .execute(UploadResult.class);
    }
}
