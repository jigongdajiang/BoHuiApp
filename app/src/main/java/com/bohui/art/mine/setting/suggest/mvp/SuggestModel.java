package com.bohui.art.mine.setting.suggest.mvp;

import com.bohui.art.bean.mine.SuggestSubmitResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class SuggestModel implements SuggestContanct.Model {
    @Override
    public Observable<SuggestSubmitResult> suggestSubmit() {
        return EasyHttp.post(SuggestContanct.URL_SUGGEST)
                .execute(SuggestSubmitResult.class);
    }
}
