package com.bohui.art.search.mvp;

import com.bohui.art.bean.search.AllClassifyResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/2/3
 * @description:
 */


public class GetAllClassifyModel implements GetAllClassifyContact.Model {
    @Override
    public Observable<AllClassifyResult> getAllClassify() {
        return EasyHttp.post(GetAllClassifyContact.URL_GET_ALL_CLASSIFY)
                .execute(AllClassifyResult.class);
    }
}
