package com.framework.core.http.exception;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 */


public abstract class AbsApiExceptionHandler implements IApiExceptionHandleFun {
    private IApiExceptionHandleFun mSuperExceptionHandler;
    public void setSuperExceptionHandler(IApiExceptionHandleFun superExceptionHandler) {
        this.mSuperExceptionHandler = superExceptionHandler;
    }
    public abstract boolean selfExceptionHandle(ApiException e);
    @Override
    public boolean exceptionHandle(ApiException e) {
        if(selfExceptionHandle(e)){
           return true;
        }
        if(mSuperExceptionHandler != null){
            return mSuperExceptionHandler.exceptionHandle(e);
        }
        return false;
    }
}
