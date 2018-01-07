package com.bohui.art.detail.designer.mvp;

import com.bohui.art.bean.detail.DesignerDetailResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class DesignerDetailModel implements DesignerDetailContact.Model {
    @Override
    public Observable<DesignerDetailResult> getDesingerDetail(String id) {
        return EasyHttp.post(DesignerDetailContact.URL_GET_DESIGNER_DETAIL)
                .params("id",id)
                .execute(DesignerDetailResult.class);
    }
}
