package com.bohui.art.mine.mvp;

import com.bohui.art.mine.mvp.bean.MineInfoResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class MineModel implements MineContact.Model {
    @Override
    public Observable<MineInfoResult> getUserInfo(String uid) {
        return EasyHttp.post(MineContact.URL_MINE_GET_USERINFO)
                .params("uid",uid)
                .execute(MineInfoResult.class);
    }
}