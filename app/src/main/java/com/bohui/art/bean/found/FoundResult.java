package com.bohui.art.bean.found;

import com.bohui.art.bean.common.BannerBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/27
 * @description:
 */


public class FoundResult {
    private List<BannerBean> bannerList;
    private String coopDescUrl;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public String getCoopDescUrl() {
        return coopDescUrl;
    }

    public void setCoopDescUrl(String coopDescUrl) {
        this.coopDescUrl = coopDescUrl;
    }
}
