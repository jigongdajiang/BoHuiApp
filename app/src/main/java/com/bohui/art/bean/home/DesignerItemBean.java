package com.bohui.art.bean.home;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 */


public class DesignerItemBean {
    private String cover;
    private String did;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "DesignerItemBean{" +
                "cover='" + cover + '\'' +
                ", did='" + did + '\'' +
                '}';
    }
}
