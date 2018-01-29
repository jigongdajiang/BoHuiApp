package com.bohui.art.bean.classify;

import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.home.ClassifyLevelBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 */


public class ClassifyLevel2Result {
    private List<BannerBean> bannerList;
    private List<ClassifyLevelBean> twoClass;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<ClassifyLevelBean> getTwoClass() {
        return twoClass;
    }

    public void setTwoClass(List<ClassifyLevelBean> twoClass) {
        this.twoClass = twoClass;
    }

    @Override
    public String toString() {
        return "ClassifyLevel2Result{" +
                "bannerList=" + bannerList!=null ? bannerList.toString(): "" +
                ", twoClass=" + twoClass != null ? twoClass.toString() : "" +
                '}';
    }
}
