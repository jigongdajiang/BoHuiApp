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
    String URL_UPLOAD_HEAD_IMG = "common/upload";
    String TAG_UPLOAD_HEAD_IMG = "tag_upload_head_img";
    interface Model extends BaseModel {
        Observable<UploadResult> upLoad(long uid,File file);
    }
    interface View extends BaseView {
        void upLoadSuccess(UploadResult uploadResult);
    }
    abstract class Presenter extends BasePresenter<Model,View> {
        public abstract void upLoad(long uid,File file);
    }
}
