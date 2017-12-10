package com.bohui.art.start;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.bohui.art.R;
import com.bohui.art.classify.ClassifyFragment;
import com.bohui.art.common.activity.AbsBaseActivity;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.helperutil.NetBaseHelperUtil;
import com.bohui.art.found.FoundFragment;
import com.bohui.art.home.HomeFragment;
import com.bohui.art.mine.MineFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.listener.TabEntity;
import com.framework.core.fragment.FragmentChangeManager;
import com.framework.core.util.ResUtil;
import com.framework.core.util.StatusBarCompatUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/11/29
 * @description:
 */


public class MainActivity extends AbsNetBaseActivity {
    @BindView(R.id.tab)
    CommonTabLayout mTabLayout;//新发现标签
    @BindView(R.id.container)
    FrameLayout mContainer;//Fragment

    //fragment相关
    private List<Fragment> fragments;
    private List<String> fTags;
    private FragmentChangeManager mFragmentChangeManager;
    //当前的位置
    private int currentPosition;
    // 双击返回键 退出时 时间记录间隔
    long exitTime = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initFragments();
        initTab();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fTags = new ArrayList<>();

        HomeFragment homeFragment = new HomeFragment();
        fragments.add(homeFragment);
        fTags.add(HomeFragment.class.getSimpleName());

        ClassifyFragment investFragment = new ClassifyFragment();
        Bundle bundle = new Bundle();
        investFragment.setArguments(bundle);
        fragments.add(investFragment);
        fTags.add(ClassifyFragment.class.getSimpleName());

        FoundFragment foundFragment = new FoundFragment();
        fragments.add(foundFragment);
        fTags.add(FoundFragment.class.getSimpleName());

        MineFragment mineFragment = new MineFragment();
        fragments.add(mineFragment);
        fTags.add(MineFragment.class.getSimpleName());

        mFragmentChangeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.container, fragments, fTags);
    }

    private void initTab() {
        String[] mTitles = new String[]{
                ResUtil.getResString(mContext, R.string.main_tab_home),
                ResUtil.getResString(mContext, R.string.main_tab_classify),
                ResUtil.getResString(mContext, R.string.main_tab_found),
                ResUtil.getResString(mContext, R.string.main_tab_mine)
        };
        int[] mIconUnselectIds = {
                R.mipmap.ic_home_normal,
                R.mipmap.ic_classify_normal,
                R.mipmap.ic_found_normal,
                R.mipmap.ic_mine_normal,
        };
        int[] mIconSelectIds = {
                R.mipmap.ic_home_select,
                R.mipmap.ic_classify_select,
                R.mipmap.ic_found_select,
                R.mipmap.ic_mine_select,
        };
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        changeTab(0);
                        break;
                    case 1:
                        changeTab(1);
                        break;
                    case 2:
                        changeTab(2);
                        break;
                    case 3:
                        changeTab(3);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 切换Tab
     *
     * @param position
     */
    private void changeTab(int position) {
        if (position < mFragmentChangeManager.getSize()) {
            mFragmentChangeManager.showFragment(position);
            mTabLayout.setCurrentTab(position);
            currentPosition = position;
        }
    }

    /**
     * 按返回键的操作
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                if (null != mHelperUtil && mHelperUtil instanceof NetBaseHelperUtil) {
                    ((NetBaseHelperUtil) mHelperUtil).toastShort("再按一次退出程序");
                }
                exitTime = System.currentTimeMillis();
            } else {
                AppFuntion.exitApp(mContext);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
