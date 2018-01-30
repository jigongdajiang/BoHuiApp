package com.bohui.art.bean.detail;

/**
 * @author : gaojigong
 * @date : 2017/12/24
 * @description:
 */


public class CaseBean {
    private String area;//规格
    private String img;//封面图
    private String name;//名称
    private long oid;//作品id
    private String url;//作品链接地址
    private String looknum;//浏览量

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLooknum() {
        return looknum;
    }

    public void setLooknum(String looknum) {
        this.looknum = looknum;
    }
}
