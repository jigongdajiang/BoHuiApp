package com.framework.core.util.strformat;

import android.text.Html;

import java.text.DecimalFormat;

/**
 * des:金钱
 * Created by xsf
 * on 2016.06.11:48
 */
public class NumberFormatUtil {
    public static String numberFomat(String format,double d){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(d);
    }
    /***
     * 保留两位小数
     * example:1.01
     */
    public static String NF2Point(double d) {
        return numberFomat("#.##",d);
    }
    /***
     * 每三位，分割，保留两位小数
     */
    public static String NF2Point3Split(double d) {
        return numberFomat("###,###.##",d);
    }

    /**
     * 得到￥ 0.0
     */
    public static String getCnMonery(String source){
        return Html.fromHtml("&yen").toString()+" "+ source;
    }
    public static String getCnMonery(double source){
        return Html.fromHtml("&yen").toString()+" "+ source;
    }
    public static String getCnMonery(float source){
        return Html.fromHtml("&yen").toString()+" "+ source;
    }
    public static String getCnMonery(int source){
        return Html.fromHtml("&yen").toString()+" "+ source;
    }
    public static String getCnMonery(long source){
        return Html.fromHtml("&yen").toString()+" "+ source;
    }
}
