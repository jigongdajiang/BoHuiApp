package com.bohui.art.common.rv;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class ItemType {
    public static int TYPE_START = 0x10;
    //分类左边布局
    public static int CLASSIFY_ITEM_TYPE = TYPE_START++;
    //分类右边项布局
    public static int CLASSIFY_ITEM_TYPE_DETAIL = TYPE_START++;
    //分类右边项分割区域布局
    public static int CLASSIFY_ITEM_TYPE_DIVIDER = TYPE_START++;
    //条形导航布局
    public static int GUIDE_ITEM_TYPE = TYPE_START++;
    //我的页面头部布局
    public static int MINE_TOP = TYPE_START++;
    //二级列表页
    public static int ART_2 = TYPE_START++;
    //艺术家列表页item布局
    public static int ART_MAN_LIST = TYPE_START++;
    //我的定制列表Item布局
    public static int My_ORDER_ITEM = TYPE_START++;
    //搜索页热门标签布局
    public static int SEARCH_TAG = TYPE_START++;
    //搜索筛选的标签布局
    public static int SEARCH_FILTRATE_TAG = TYPE_START++;
    //搜索筛选的价格布局
    public static int SEARCH_FILTRATE_PRICE = TYPE_START++;
}
