package com.bohui.art.bean.found;

import java.io.Serializable;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan1LevelBean implements Serializable{
    private String name;
    private int pid;
    private int tid;
    private List<ArtMan2LevelBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public List<ArtMan2LevelBean> getList() {
        return list;
    }

    public void setList(List<ArtMan2LevelBean> list) {
        this.list = list;
    }
}
