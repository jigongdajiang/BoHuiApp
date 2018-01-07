package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtBean;
import com.bohui.art.bean.home.Type2LevelBean;

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
