package com.bohui.art.start.reg;

import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public interface RegContact {
    String URL_REG_GET_CODE = "";
    String TAG_REG_GET_CODE = "tag_reg_get_cde";
    String URL_REG = "";
    String TAG_REG = "tag_reg";
    interface Model extends BaseModel{
        Observable<VerCodeResult> getCode(String phone);
        Observable<RegResult> reg(String phone,String code);
    }
    interface View extends BaseLoadingView{
        void getCodeSuccess(VerCodeResult result);
        void regSuccess(RegResult result);
    }
    abstract class Presenter extends BasePresenter<Model,View>{
        public abstract void getCode(String phone);
        public abstract void reg(String phone,String code);
    }
}
