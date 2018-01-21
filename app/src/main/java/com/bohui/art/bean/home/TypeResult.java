package com.bohui.art.bean.home;

import com.bohui.art.bean.common.BannerBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 首页各一级页的返回结果
 */


public class TypeResult {
    private List<BannerBean> bannerList;
    private List<ClassifyLevelBean> cateList;
    private List<Type2ListItemBean> chird;
    private RecommendListItemBean boutique;
    private RecommendMechanismBean mechanism;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<ClassifyLevelBean> getCateList() {
        return cateList;
    }

    public void setCateList(List<ClassifyLevelBean> cateList) {
        this.cateList = cateList;
    }

    public List<Type2ListItemBean> getChird() {
        return chird;
    }

    public void setChird(List<Type2ListItemBean> chird) {
        this.chird = chird;
    }

    public RecommendListItemBean getBoutique() {
        return boutique;
    }

    public void setBoutique(RecommendListItemBean boutique) {
        this.boutique = boutique;
    }

    public RecommendMechanismBean getMechanism() {
        return mechanism;
    }

    public void setMechanism(RecommendMechanismBean mechanism) {
        this.mechanism = mechanism;
    }

    @Override
    public String toString() {
        return "TypeResult{" +
                "bannerList=" + bannerList.toString() +
                ", cateList=" + cateList.toString() +
                ", chird=" + chird.toString() +
                ", boutique=" + boutique.toString() +
                ", mechanism=" + mechanism.toString() +
                '}';
    }
}
