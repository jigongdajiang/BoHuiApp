package com.bohui.art.mine.upload;


import com.bohui.art.bean.mine.UploadResult;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;

import java.io.File;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2017/12/21
 * @description:
 */


public interface UpLoadContact {
    // “账户上传头像“URL、TAG
    String URL_UPLOAD_HEAD_IMG = "member/picture/modify/v2.0";
    String TAG_UPLOAD_HEAD_IMG = "headImgPictureUpload";
    interface Model extends BaseModel {
        Observable<UploadResult> upLoad(File file);
    }
    interface View extends BaseView {
        void upLoadSuccess(UploadResult uploadResult);
    }
    abstract class Presenter extends BasePresenter<Model,View> {
        public abstract void upLoad(File file);
    }
}
