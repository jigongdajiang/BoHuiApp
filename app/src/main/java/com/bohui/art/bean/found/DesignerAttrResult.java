package com.bohui.art.bean.found;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public class DesignerAttrResult {
    private List<DesignerAttrBean> areaList;
    private List<DesignerAttrBean> styleList;

    public List<DesignerAttrBean> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<DesignerAttrBean> areaList) {
        this.areaList = areaList;
    }

    public List<DesignerAttrBean> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<DesignerAttrBean> styleList) {
        this.styleList = styleList;
    }
}
