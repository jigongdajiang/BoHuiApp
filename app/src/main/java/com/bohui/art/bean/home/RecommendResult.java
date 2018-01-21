package com.bohui.art.bean.home;

import com.bohui.art.bean.common.BannerBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/7
 * @description:
 * 首页推荐页返回的结果
 */


public class RecommendResult {
    private List<BannerBean> bannerList;
    private List<BannerBean> adList;
    private List<RecommendListItemBean> remomerList;
    private List<ArtItemBean> guessLike;
    private RecommendDesignerBean designer;
    private RecommendMechanismBean mechanism;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<BannerBean> getAdList() {
        return adList;
    }

    public void setAdList(List<BannerBean> adList) {
        this.adList = adList;
    }

    public List<RecommendListItemBean> getRemomerList() {
        return remomerList;
    }

    public void setRemomerList(List<RecommendListItemBean> remomerList) {
        this.remomerList = remomerList;
    }

    public List<ArtItemBean> getGuessLike() {
        return guessLike;
    }

    public void setGuessLike(List<ArtItemBean> guessLike) {
        this.guessLike = guessLike;
    }

    public RecommendDesignerBean getDesigner() {
        return designer;
    }

    public void setDesigner(RecommendDesignerBean designer) {
        this.designer = designer;
    }

    public RecommendMechanismBean getMechanism() {
        return mechanism;
    }

    public void setMechanism(RecommendMechanismBean mechanism) {
        this.mechanism = mechanism;
    }

    @Override
    public String toString() {
        return "RecommendResult{" +
                "bannerList=" + bannerList.toString() +
                ", adList=" + adList.toString() +
                ", remomerList=" + remomerList.toString() +
                ", guessLike=" + guessLike.toString() +
                ", designer=" + designer.toString() +
                ", mechanism=" + mechanism.toString() +
                '}';
    }
}
