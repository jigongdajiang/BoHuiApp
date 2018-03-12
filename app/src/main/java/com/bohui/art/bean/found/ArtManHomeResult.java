package com.bohui.art.bean.found;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/3/10
 * @description:
 */


public class ArtManHomeResult {
    private List<ArtMan1LevelBean> artistList;

    public List<ArtMan1LevelBean> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<ArtMan1LevelBean> artistList) {
        this.artistList = artistList;
    }

    @Override
    public String toString() {
        return "ArtManHomeResult{" +
                "artistList=" + artistList +
                '}';
    }
}
