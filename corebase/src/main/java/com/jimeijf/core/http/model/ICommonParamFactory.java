package com.jimeijf.core.http.model;

import android.content.Context;

/**
 * @author : gaojigong
 * @date : 2017/11/9
 * @description:
 * 公共参数提供器
 */


public interface ICommonParamFactory {
    HttpParams createCommomParams(Context context);
}
