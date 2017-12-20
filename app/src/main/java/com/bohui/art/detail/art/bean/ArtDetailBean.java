package com.bohui.art.detail.art.bean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/20
 * @description:
 */


public class ArtDetailBean {
    private List<String> imgs;
    private String detailUrl;//详情地址
    private String intro;

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
