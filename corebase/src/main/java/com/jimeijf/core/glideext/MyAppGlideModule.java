package com.jimeijf.core.glideext;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author : gaojigong
 * @date : 2017/11/16
 * @FileName:
 * @description:
 * 多种参数都能链式调用
 * 允许全局更改包括缓存位置、大小在内的应用特定设置。重写applyOptions()
 * 可以注册一些Glide components 重写registerComponents()
 * GlideApp.with(fragment)
    .load(myUrl)
    .placeholder(placeholder)
    .circleCrop()
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .transition(withCrossFade())
    .into(imageView);
 */


@GlideModule
public final class MyAppGlideModule extends AppGlideModule {}
