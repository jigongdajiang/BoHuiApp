package com.jimeijf.core.http.model;

import android.content.Context;

/**
 * @author : gaojigong
 * @date : 2017/11/9
 * @description:
 * 公共头部提供器
 */


public interface ICommonHeaderFactory {
    HttpHeaders createCommonHeaders(Context context);
}
