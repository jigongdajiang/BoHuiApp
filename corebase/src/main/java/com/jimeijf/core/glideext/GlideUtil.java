package com.jimeijf.core.glideext;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.lang.ref.WeakReference;

/**
 * @author : gongdaocai
 * @date : 2017/11/15
 * FileName:
 * @description:
 * Glide 加载图片工具
 * 常用加载方案处理，可在业务层进行具体业务特殊需求的封装
 */


public class GlideUtil {
    /**
     * 基础加载图片
     * @param context 这里可以是Context Activity FragmentActivity Fragment 能自动切换
     * @param imageView ImageView ImageView对象
     * @param imgSource 可以是String Uri File Integer URL byte[] T内部会自动转换
     * @param placeholderId 过度 加载中显示
     *                      请求过程中显示 如果error 和 fallback没设置，placeholder将继续被显示。
     * @param errorId 错误是显示
     *                当请求永远失败时显示error Drawable。
     *                url/model为空且fallback Drawable没设置时显示error Drawable
     *
     * @param fallbackId 地址错误imgSource为空时显示
     *                   url/model为空时显示fallback Drawable
     *                   未设置头像用户的avatar字段可能是null，适用于该场景
     */
    public static void display(Object context, ImageView imageView, Object imgSource, int placeholderId, int errorId,int fallbackId) {
        ImageView weakImage = weakImageView(imageView);
        if(null != weakImage){
            converRequestManager(context)
                    .load(imgSource)
                    .apply(RequestOptions
                            .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                            .placeholder(placeholderId)
                            .error(errorId)
                            .fallback(fallbackId))
                    .into(weakImage);
        }
    }
    public static void display(Object context, ImageView imageView, Object imgSource) {
        ImageView weakImage = weakImageView(imageView);
        if(null != weakImage){
        converRequestManager(context)
                .load(imgSource)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(imageView);
        }
    }

    /**
     * 根据参数自由配置显示情况
     * @param requestOptions
     *  占位符(Placeholders)
     *  图片变换(裁剪，过滤)(Transformations)
     *      .transform()
     *          支持多种Transformation参数例如BlurTransformation  ColorFilterTransformation
     *      .centerCrop()
     *      .centerInside()
     *      .circleCrop()
     *
     *  缓存策略(Caching Strategies)
     *      .diskCacheStrategy()
     *  编码质量、解码配置等组件选项(Component specific options)
     *      .encodeFormat()
     *      .encodeQuality()
     *  @param requestOptions
     *      设置请求完成后的显示行为 内存缓存不会应用
     *      与Glide要加载的资源类型直接关联(TranscodeType)
     *          DrawableTransitionOptions
     *          BitmapTransitionOptions asBitmap
     *      淡入效果(View fade in)
     *      交叉渐入渐出(Cross fade from the placeholder)
     *      无过渡效果(No transition)
     *
     */
    public static void display(Object context, ImageView imageView, Object imgSource, RequestOptions requestOptions, TransitionOptions transitionOptions) {
        ImageView weakImage = weakImageView(imageView);
        if(null != weakImage){
            converRequestManager(context)
                    .load(imgSource)
                    .apply(requestOptions)
                    .transition(transitionOptions)
                    .into(imageView);
        }
    }



    protected static RequestManager converRequestManager(Object context) {
        if(context == null){
            throw new IllegalStateException("context can not null");
        }
        if(context instanceof FragmentActivity){
            return Glide.with((FragmentActivity) context);
        }else if(context instanceof Activity){
            return Glide.with((Activity) context);
        }else if(context instanceof Context){
            return Glide.with((Context)context);
        }else if(context instanceof Fragment){
            return Glide.with((Fragment) context);
        }else if(context instanceof android.app.Fragment){
            return Glide.with((android.app.Fragment) context);
        }else{
            return null;
        }
    }

    /**
     * 以弱引用方式存储
     * 减少内存泄露的可能
     * @param imageView
     * @return
     */
    protected static ImageView weakImageView(ImageView imageView){
        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(imageView);
        return imageViewWeakReference.get();
    }
}
