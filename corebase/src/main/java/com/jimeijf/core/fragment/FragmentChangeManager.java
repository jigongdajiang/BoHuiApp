package com.jimeijf.core.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : gongdaocai
 * @date : 2017/11/15
 * FileName:
 * @description:
 * 管理Fragment的类
 * 一个Activity或Fragment对应一个管理器
 * 主要针对的是少量
 */


public class FragmentChangeManager {
    /**Fragment管理器*/
    private FragmentManager mFragmentManager;
    /**Fragment容器View的id*/
    private int mContainerViewId;
    /**Fragment对应的Tag与mFragments意义对象*/
    private List<String> mFragmentTags;
    /**Fragment切换数组*/
    private List<Fragment> mFragments;
    /** 当前选中的Tab */
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, List<Fragment> fragments, List<String> fragmentTags) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        this.mFragmentTags = fragmentTags;
        initFragments();
    }

    private void initFragments() {
        if(mFragments == null){
            mFragments = new ArrayList<>();
        }
        if(mFragmentTags == null){
            mFragmentTags = new ArrayList<>();
        }
        if(mFragmentManager == null){
            throw new IllegalStateException("FragmentManager necessary!");
        }
        if(mContainerViewId == 0){
            throw new IllegalStateException("ContainerViewId necessary!");
        }
        //默认Tag规则
        if (mFragmentTags.size() == 0 || mFragmentTags.size() != mFragments.size()){
            //Tag与Fragment不对应，充值Tag
            //Tag Fragment的名字+顺序
            for (int i=0;i<mFragments.size();i++) {
                Fragment fragment = mFragments.get(i);
                String tag = fragment.getClass().getSimpleName()+String.valueOf(i);
                mFragmentTags.add(tag);
                //添加并隐藏
                mFragmentManager.beginTransaction().add(mContainerViewId,fragment,tag).hide(fragment).commit();
            }
        }
        //默认显示第一个
        if(mFragments.size() > 0){
            mFragmentManager.beginTransaction().show(mFragments.get(0)).commit();
            mCurrentTab = 0;
        }
    }

    /**
     * 添加新Fragment，但是不显示
     * @param fragment
     * @param tag
     */
    public void addFragment(Fragment fragment,String tag){
        if(!fragment.isAdded()){
            mFragments.add(fragment);
            mFragmentTags.add(tag);
            mFragmentManager.beginTransaction().add(mContainerViewId,fragment,tag).hide(fragment).commit();
        }
    }
    public void addFragment(Fragment fragment){
        String tag = fragment.getClass().getSimpleName()+mFragments.size();
        addFragment(fragment,tag);
    }
    public void removeFragment(int position){
        if(position < 0 || position>=mFragments.size()){
            return;
        }
        if(mCurrentTab == position){
            //如果移除的是正在显示的
            if(mCurrentTab == 0){
                //应该显示移除后的下一个也就是新集合的第一个
                mCurrentTab = 0;
            }else if(mCurrentTab == mFragments.size()-1){
                //最后一个，应该显示上一个
                mCurrentTab = mCurrentTab-1;
            }else{
                //如果是中间则显示前一个
                mCurrentTab = mCurrentTab - 1;
            }
        }else if(position < mCurrentTab){
            mCurrentTab = mCurrentTab-1;
        }
        Fragment fragment = mFragments.get(position);
        mFragmentManager.beginTransaction().remove(fragment).commit();
        mFragmentTags.remove(position);
        mFragments.remove(position);
        showFragment(mCurrentTab);
    }

    /**
     * 显示指定位置的Fragment
     * 外部只能通过该方法决定显示
     * @param position
     */
    public void showFragment(int position) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == position) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        mCurrentTab = position;
    }

    public int getSize() {
        return mFragments != null ? mFragments.size() : 0;
    }

    public int getCurrentPosition(){
        return mCurrentTab;
    }
    public Fragment getCurrentFragment(){
        return mFragments.size() > 0 ? mFragments.get(mCurrentTab) : null;
    }
}
