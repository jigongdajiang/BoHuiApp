package com.bohui.art.bean.home;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/20
 * @description:
 * 推荐列表数据
 * 推荐一级分类数据实体
 */


public class RecommendListItemBean {
    private String name;
    private long id;
    private List<ArtCoverItemBean> list;

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

    public List<ArtCoverItemBean> getList() {
        return list;
    }

    public void setList(List<ArtCoverItemBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RecommendListItemBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", list=" + list.toString() +
                '}';
    }
}
