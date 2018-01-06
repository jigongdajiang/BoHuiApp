package com.bohui.art.common.fragment;


import com.bohui.art.common.helperutil.NetBaseHelperUtil;
import com.framework.core.base.AbsHelperUtil;
import com.framework.core.base.BaseModel;
import com.framework.core.base.BasePresenter;
import com.framework.core.base.BaseView;
import com.framework.core.http.exception.ApiException;

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


public abstract class AbsNetBaseFragment<P extends BasePresenter, M extends BaseModel> extends AbsBaseFragment<P,M> implements BaseView {
    @Override
    protected AbsHelperUtil createHelperUtil() {
        return new NetBaseHelperUtil(this);
    }

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    protected boolean isFragmentVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isFragmentVisible = true;
            onVisible();
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onInvisible();
        }
    }
    /**
     * 可见时
     */
    protected void onVisible() {
        lazyLoad();
    }

    public void lazyLoad(){
        //可见，View初始化成功，添加好了
        if(!isFragmentVisible || !hasCreateView){
            return;
        }
        doLoad();
    }

    protected void doLoad(){}

    /**
     * 不可见时
     */
    protected void onInvisible() {

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
            case 20000:
                return true;
            case 20004:
                return true;
        }
        return false;
    }

    protected boolean childHandlerException(String apiName, ApiException e) {
        return false;
    }
}
