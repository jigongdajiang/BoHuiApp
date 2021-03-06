package com.bohui.art.found.designer;

import com.bohui.art.bean.found.DesignerAttrResult;
import com.bohui.art.bean.found.DesignerListParam;
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
    public Observable<DesignerAttrResult> getDesignerAttr() {
        return EasyHttp.post(DesignerListContact.URL_GET_DESIGNER_ATTR)
                .execute(DesignerAttrResult.class);
    }

    @Override
    public Observable<DesignerListResult> getDesignerList(DesignerListParam param) {
        return EasyHttp.post(DesignerListContact.URL_GET_DESIGNER_LIST)
                .params("start", String.valueOf(param.getStart()))
                .params("length", String.valueOf(param.getLength()))
                .params("name",param.getName())
                .params("goodfiled", String.valueOf(param.getGoodfiled()))
                .params("goodstyle", String.valueOf(param.getGoodstyle()))
                .execute(DesignerListResult.class);
    }
}
