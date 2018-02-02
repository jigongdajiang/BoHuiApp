package com.bohui.art.bean.mine;

/**
 * @author : gaojigong
 * @date : 2017/12/21
 * @description:
 */


public class UploadResult {
    private String path;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
