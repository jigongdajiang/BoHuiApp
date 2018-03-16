package com.bohui.art.bean.home;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 */


public class Type2ListItemBean {
    private String name;
    private long id;
    private List<ArtItemBean> list;
    private long pid;

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

    public List<ArtItemBean> getList() {
        return list;
    }

    public void setList(List<ArtItemBean> list) {
        this.list = list;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "Type2ListItemBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", list=" + list.toString() +
                '}';
    }
}
