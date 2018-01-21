package com.bohui.art.bean.home;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/11
 * @description:
 */


public class RecommendDesignerBean {
    private String name;
    private List<DesignerItemBean> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DesignerItemBean> getList() {
        return list;
    }

    public void setList(List<DesignerItemBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RecommendDesignerBean{" +
                "name='" + name + '\'' +
                ", list=" + list.toString() +
                '}';
    }
}
