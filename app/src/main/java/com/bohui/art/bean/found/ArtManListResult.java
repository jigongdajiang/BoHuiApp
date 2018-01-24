package com.bohui.art.bean.found;

import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.ArtItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ArtManListResult {
    private List<ArtManItemBean> artistList;

    public List<ArtManItemBean> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<ArtManItemBean> artistList) {
        this.artistList = artistList;
    }
}
