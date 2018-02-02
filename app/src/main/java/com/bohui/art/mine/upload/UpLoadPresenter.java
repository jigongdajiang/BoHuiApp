package com.bohui.art.mine.upload;

import com.bohui.art.bean.mine.UploadResult;
import com.framework.core.http.subsciber.BaseSubscriber;

import java.io.File;

/**
 * @author : gaojigong
 * @date : 2017/12/21
 * @description:
 */


public class UpLoadPresenter extends UpLoadContact.Presenter {
    @Override
    public void upLoad(long uid,File file) {
        mRxManage.add(mModel.upLoad(uid,file).subscribeWith(new BaseSubscriber<UploadResult>(mView,UpLoadContact.TAG_UPLOAD_HEAD_IMG) {
            @Override
            protected void onResultSuccess(UploadResult uploadResult) {
                mView.upLoadSuccess(uploadResult);
            }
        }));
    }
}
