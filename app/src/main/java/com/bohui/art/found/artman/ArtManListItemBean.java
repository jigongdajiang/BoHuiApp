package com.bohui.art.found.artman;

import com.bohui.art.home.bean.ArtBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ArtManListItemBean {
    private String artManAvr;
    private List<ArtBean> artBeans;

    public String getArtManAvr() {
        return artManAvr;
    }

    public void setArtManAvr(String artManAvr) {
        this.artManAvr = artManAvr;
    }

    public List<ArtBean> getArtBeans() {
        return artBeans;
    }

    public void setArtBeans(List<ArtBean> artBeans) {
        this.artBeans = artBeans;
    }
}
