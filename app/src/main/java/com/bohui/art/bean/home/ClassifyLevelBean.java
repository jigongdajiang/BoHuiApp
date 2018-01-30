package com.bohui.art.bean.home;

import java.io.Serializable;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 分类数据对象
 */


public class ClassifyLevelBean implements Serializable{
    private String name;
    private long id;
    private String logo;
    private long pid;
    private boolean isChecked;

    public ClassifyLevelBean() {
    }

    public ClassifyLevelBean(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public ClassifyLevelBean(String name, long id, long pid) {
        this.name = name;
        this.id = id;
        this.pid = pid;
    }

    public ClassifyLevelBean(String logo,String name, long id) {
        this.name = name;
        this.id = id;
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "ClassifyLevelBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", logo='" + logo + '\'' +
                ", pid=" + pid +
                '}';
    }
}
