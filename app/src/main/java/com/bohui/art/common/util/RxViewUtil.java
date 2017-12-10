package com.bohui.art.common.util;

import android.view.View;

import com.framework.core.rxcore.RxManager;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/1
 * @description: 点击时间统一处理
 */


public class RxViewUtil {
    public static void addOnClick(RxManager rxManager, View view, Consumer consumer) {
        rxManager.add(
                RxView.clicks(view)
                        .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe(consumer));
    }
}
