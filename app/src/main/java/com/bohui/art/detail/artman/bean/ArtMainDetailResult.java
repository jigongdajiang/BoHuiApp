package com.bohui.art.detail.artman.bean;

import com.bohui.art.home.bean.ArtBean;
import com.bohui.art.home.bean.Type2LevelBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class ArtMainDetailResult {
    private List<ArtBean> arts;
    private List<Type2LevelBean> artTyes;

    public List<ArtBean> getArts() {
        return arts;
    }

    public List<Type2LevelBean> getArtTyes() {
        return artTyes;
    }
}
