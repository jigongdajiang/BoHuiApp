package com.bohui.art.bean.home;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class RecommendMechanismBean {
    private String name;
    private List<ArtItemBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArtItemBean> getList() {
        return list;
    }

    public void setList(List<ArtItemBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RecommendMechanismBean{" +
                "name='" + name + '\'' +
                ", list=" + list.toString() +
                '}';
    }
}
