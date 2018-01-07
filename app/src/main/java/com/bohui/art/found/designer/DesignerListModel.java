package com.bohui.art.found.designer;

import com.bohui.art.bean.found.DesignerListResult;
import com.framework.core.http.EasyHttp;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class DesignerListModel implements DesignerListContact.Model {
    @Override
    public Observable<DesignerListResult> getDesignerList() {
        return EasyHttp.post(DesignerListContact.URL_GET_DESIGNER_LIST)
                .execute(DesignerListResult.class);
    }
}
