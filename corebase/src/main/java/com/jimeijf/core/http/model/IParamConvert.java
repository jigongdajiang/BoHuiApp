package com.jimeijf.core.http.model;

/**
 * @author : gongdaocai
 * @date : 2017/11/9
 * FileName:
 * @description:
 * 参数转义接口
 * 有些情况我们的请求体可能多样化，如json等
 * 为了保证正常的使用习惯需要将最终的请求的参数进行转义，也可以在这里实现加密
 */


public interface IParamConvert<T> {
    T convertParams(HttpParams params);
}
