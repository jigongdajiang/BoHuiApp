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
}
