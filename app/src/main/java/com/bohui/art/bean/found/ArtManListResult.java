package com.bohui.art.bean.found;

import com.bohui.art.bean.home.ArtBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ArtManListResult {
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
