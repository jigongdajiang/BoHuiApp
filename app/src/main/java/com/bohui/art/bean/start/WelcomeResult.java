package com.bohui.art.bean.start;

import java.util.List;

/**
 * @author : gaojigong
 * @date : 2018/1/2
 * @description:
 */


public class WelcomeResult {
    private List<String> bootPage;

    public List<String> getBootPage() {
        return bootPage;
    }

    public void setBootPage(List<String> bootPage) {
        this.bootPage = bootPage;
    }

    @Override
    public String toString() {
        return "WelcomeResult{" +
                "bootPage=" + bootPage +
                '}';
    }
}
