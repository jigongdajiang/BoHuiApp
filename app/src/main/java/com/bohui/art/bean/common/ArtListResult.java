package com.bohui.art.bean.common;

import com.bohui.art.bean.home.ArtItemBean;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/21
 * @description:
 */


public class ArtListResult {
    private List<ArtItemBean> paintingList;

    public List<ArtItemBean> getPaintingList() {
        return paintingList;
    }

    public void setPaintingList(List<ArtItemBean> paintingList) {
        this.paintingList = paintingList;
    }
}
