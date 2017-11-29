package com.jimeijf.grecycleview.manager;

/**
 * @author : gongdaocai
 * @date : 2017/11/28
 * FileName:
 * @description:
 */


public abstract class CheckItemManager {
    public abstract boolean checkPosition(int position);

    public abstract int getAfterCheckingPosition(int position);
}
