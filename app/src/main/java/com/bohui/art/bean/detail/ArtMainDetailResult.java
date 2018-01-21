package com.bohui.art.bean.detail;

import com.bohui.art.bean.home.ArtCoverItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/23
 * @description:
 */


public class ArtMainDetailResult {
    private List<ArtCoverItemBean> arts;
    private List<ArtManLevelBean> artTyes;

    public List<ArtCoverItemBean> getArts() {
        return arts;
    }

    public List<ArtManLevelBean> getArtTyes() {
        return artTyes;
    }
}
