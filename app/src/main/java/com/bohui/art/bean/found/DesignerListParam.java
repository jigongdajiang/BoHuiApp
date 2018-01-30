package com.bohui.art.bean.found;

import com.bohui.art.bean.common.PageParam;

/**
 * @author : gaojigong
 * @date : 2018/1/30
 * @description:
 */


public class DesignerListParam extends PageParam {
    private String name;//设计师姓名
    private long goodfiled;//设计师擅长领域默认0
    private long goodstyle;//设计师擅长风格默认0

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getGoodfiled() {
        return goodfiled;
    }

    public void setGoodfiled(long goodfiled) {
        this.goodfiled = goodfiled;
    }

    public long getGoodstyle() {
        return goodstyle;
    }

    public void setGoodstyle(long goodstyle) {
        this.goodstyle = goodstyle;
    }
}
