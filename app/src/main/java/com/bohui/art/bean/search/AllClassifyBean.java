package com.bohui.art.bean.search;

import com.bohui.art.bean.home.ClassifyLevelBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/2/3
 * @description:
 */


public class AllClassifyBean {
    private long id;//一级id
    private String name;//一级名字
    private String logo;
    private long pid;
    private List<ClassifyLevelBean> list;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public List<ClassifyLevelBean> getList() {
        return list;
    }

    public void setList(List<ClassifyLevelBean> list) {
        this.list = list;
    }
}
