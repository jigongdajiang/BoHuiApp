package com.framework.core.http.exception;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 *  异常处理功能接口
 */


public interface IApiExceptionHandleFun {
    boolean exceptionHandle(ApiException e);
}
