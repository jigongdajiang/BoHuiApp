package com.jimeijf.core.toast;

import android.content.Context;

/**
 * @author : gaojigong
 * @date : 2017/11/14
 * @description:
 */


public interface IToastStrategy {
    void show(Context context, String str, int duration);

    void cancel();
}