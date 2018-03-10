package com.bohui.art.bean.found;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan2LevelBean {
    private String art2LevelName;
    private String art2LevelId;
    private List<ArtManHomeItemBean> artManHomeItemBeans;

    public String getArt2LevelName() {
        return art2LevelName;
    }

    public void setArt2LevelName(String art2LevelName) {
        this.art2LevelName = art2LevelName;
    }

    public String getArt2LevelId() {
        return art2LevelId;
    }

    public void setArt2LevelId(String art2LevelId) {
        this.art2LevelId = art2LevelId;
    }

    public List<ArtManHomeItemBean> getArtManHomeItemBeans() {
        return artManHomeItemBeans;
    }

    public void setArtManHomeItemBeans(List<ArtManHomeItemBean> artManHomeItemBeans) {
        this.artManHomeItemBeans = artManHomeItemBeans;
    }
}
