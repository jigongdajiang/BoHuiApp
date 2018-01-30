package com.bohui.art.bean.mine;

/**
 * @author : gaojigong
 * @date : 2018/1/8
 * @description:
 */


public class CheckVersionResult {
    private String time;//更新时间
    private String url;//更新链接
    private int status;//1有更新0无更新
    private String desc;//描述

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
