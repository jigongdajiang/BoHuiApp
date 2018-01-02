package com.bohui.art.start.reg;

import com.bohui.art.bean.start.RegResult;
import com.bohui.art.bean.start.VerCodeResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class RegModel implements RegContact.Model {
    @Override
    public Observable<VerCodeResult> getCode(String phone) {
        return EasyHttp.post(RegContact.URL_REG_GET_CODE)
                .params("phone",phone)
                .execute(VerCodeResult.class);
    }

    @Override
    public Observable<RegResult> reg(String phone, String code) {
        return EasyHttp.post(RegContact.URL_REG)
                .params("phone",phone)
                .params("code",code)
                .execute(RegResult.class);
    }
}
