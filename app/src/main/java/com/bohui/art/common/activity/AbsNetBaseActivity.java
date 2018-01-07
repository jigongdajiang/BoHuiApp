package com.bohui.art.common.activity;


import com.bohui.art.common.util.helperutil.AbsBaseHelperUtil;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.common.net.mvp.BaseLoadingView;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.http.exception.ApiException;
import com.widget.smallelement.dialog.BasePowfullDialog;

/**
 * @author : gaojigong
 * @date : 2017/11/30
 * @description:
 *
 *  具备网络请求功能的基Activity
 *
 *  主要处理网络请求回来后的一些通用逻辑
 *  1.异常逻辑处理
 */


public abstract class AbsNetBaseActivity<P extends BasePresenter, M extends BaseModel> extends AbsBaseActivity<P,M> implements BaseLoadingView {
    @Override
    protected AbsHelperUtil createHelperUtil() {
        return new NetBaseHelperUtil(this);
    }

    @Override
    public boolean handleException(String apiName,ApiException e) {
        if(childInterceptException(apiName,e)){
            //子类拦截的将不会走统一处理
            return true;
        }
        if(commonHandlerException(e)){
            //公共处理的，不会在子类中再处理
            return true;
        }
        //子类去处理
        return childHandlerException(apiName,e);
    }
    protected boolean childInterceptException(String apiName, ApiException e){
        return false;
    }

    private boolean commonHandlerException(ApiException e) {
        switch (e.getCode()){
            case 20001:
                return true;
            case 20004:
                return true;
        }
        return false;
    }

    protected boolean childHandlerException(String apiName, ApiException e) {
        return false;
    }

    @Override
    public BasePowfullDialog getLoadingDialog() {
        if(mHelperUtil != null && mHelperUtil instanceof AbsBaseHelperUtil){
            return ((AbsBaseHelperUtil)mHelperUtil).getLoadingDialog();
        }
        return null;
    }
}
