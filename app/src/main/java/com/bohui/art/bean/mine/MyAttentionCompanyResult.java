package com.bohui.art.bean.mine;

import com.bohui.art.bean.found.CompanyListItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class MyAttentionCompanyResult {
    private List<CompanyListItemBean> mechanismList;

    public List<CompanyListItemBean> getMechanismList() {
        return mechanismList;
    }

    public void setMechanismList(List<CompanyListItemBean> mechanismList) {
        this.mechanismList = mechanismList;
    }
}
