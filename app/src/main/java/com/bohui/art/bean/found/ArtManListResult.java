package com.bohui.art.bean.found;

import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.ArtItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ArtManListResult {
    private String artManAvr;
    private List<ArtItemBean> artBeans;

    public String getArtManAvr() {
        return artManAvr;
    }

    public void setArtManAvr(String artManAvr) {
        this.artManAvr = artManAvr;
    }

    public List<ArtItemBean> getArtBeans() {
        return artBeans;
    }

    public void setArtBeans(List<ArtItemBean> artBeans) {
        this.artBeans = artBeans;
    }
}
