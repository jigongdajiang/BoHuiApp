package com.framework.core.base;


import com.framework.core.http.exception.ApiException;

/**
 * @author : gaojigong
 * @date : 2017/11/13
 * @description:
 */

public interface BaseView {
    boolean handleException(String apiName, ApiException e);
}
