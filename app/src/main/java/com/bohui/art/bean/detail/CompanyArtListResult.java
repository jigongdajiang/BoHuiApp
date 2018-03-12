package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/12
 * @description:
 */


public class CompanyArtListResult {
    private List<ArtItemBean> mechanismList;

    public List<ArtItemBean> getMechanismList() {
        return mechanismList;
    }

    public void setMechanismList(List<ArtItemBean> mechanismList) {
        this.mechanismList = mechanismList;
    }
}
