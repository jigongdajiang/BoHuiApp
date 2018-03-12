package com.bohui.art.bean.mine;

import com.bohui.art.bean.found.CompanyListItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class MyAttentionCompanyResult {
    private List<CompanyListItemBean> listItemBeans;

    public List<CompanyListItemBean> getListItemBeans() {
        return listItemBeans;
    }

    public void setListItemBeans(List<CompanyListItemBean> listItemBeans) {
        this.listItemBeans = listItemBeans;
    }
}
