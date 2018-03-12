package com.bohui.art.bean.found;

import java.io.Serializable;
import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan2LevelBean implements Serializable{
    private String name;
    private int pid;
    private int tid;
    private List<ArtManHomeItemBean> list;

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

    public List<ArtManHomeItemBean> getList() {
        return list;
    }

    public void setList(List<ArtManHomeItemBean> list) {
        this.list = list;
    }
}
