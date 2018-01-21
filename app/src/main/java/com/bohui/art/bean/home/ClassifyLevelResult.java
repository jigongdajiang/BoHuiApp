package com.bohui.art.bean.home;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 一级分类返回结果
 */


public class ClassifyLevelResult {
    private List<ClassifyLevelBean> oneClass;

    public List<ClassifyLevelBean> getOneClass() {
        return oneClass;
    }

    public void setOneClass(List<ClassifyLevelBean> oneClass) {
        this.oneClass = oneClass;
    }

    @Override
    public String toString() {
        return "ClassifyLevelResult{" +
                "oneClass=" + oneClass.toString() +
                '}';
    }
}
