package com.jimeijf.grecycleview.manager;

/**
 * @author : gaojigong
 * @date : 2017/11/28
 * @description:
 */


public abstract class CheckItemManager {
    public abstract boolean checkPosition(int position);

    public abstract int getAfterCheckingPosition(int position);
}
