package com.bohui.art.bean.start;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class SplashResult {
    private String cover;
    private String linkurl;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }

    @Override
    public String toString() {
        return "SplashResult{" +
                "cover='" + cover + '\'' +
                ", linkurl='" + linkurl + '\'' +
                '}';
    }
}
