package com.bohui.art.bean.found;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtMan1LevelBean {
    private String artManLevel1Name;
    private String artManLevel1Id;
    private List<ArtMan2LevelBean> artMan2LevelBeans;

    public String getArtManLevel1Name() {
        return artManLevel1Name;
    }

    public void setArtManLevel1Name(String artManLevel1Name) {
        this.artManLevel1Name = artManLevel1Name;
    }

    public String getArtManLevel1Id() {
        return artManLevel1Id;
    }

    public void setArtManLevel1Id(String artManLevel1Id) {
        this.artManLevel1Id = artManLevel1Id;
    }

    public List<ArtMan2LevelBean> getArtMan2LevelBeans() {
        return artMan2LevelBeans;
    }

    public void setArtMan2LevelBeans(List<ArtMan2LevelBean> artMan2LevelBeans) {
        this.artMan2LevelBeans = artMan2LevelBeans;
    }
}
