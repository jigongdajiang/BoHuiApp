package com.bohui.art.search;

import com.bohui.art.bean.home.ClassifyLevelBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 */


public class FiltrateTagBean {
    private String oneClassifyName;
    private long oneClassifyId;

    private List<ClassifyLevelBean> twoClassify;

    public String getOneClassifyName() {
        return oneClassifyName;
    }

    public void setOneClassifyName(String oneClassifyName) {
        this.oneClassifyName = oneClassifyName;
    }

    public long getOneClassifyId() {
        return oneClassifyId;
    }

    public void setOneClassifyId(long oneClassifyId) {
        this.oneClassifyId = oneClassifyId;
    }

    public List<ClassifyLevelBean> getTwoClassify() {
        return twoClassify;
    }

    public void setTwoClassify(List<ClassifyLevelBean> twoClassify) {
        this.twoClassify = twoClassify;
    }
}
