package com.bohui.art.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.bohui.art.R;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.bean.home.ClassifyLevelResult;
import com.bohui.art.common.fragment.AbsMianFragment;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.home.mvp.HomeContact;
import com.bohui.art.home.mvp.HomeModel;
import com.bohui.art.home.mvp.HomePresenter;
import com.bohui.art.search.SearchActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.fragment.BaseFragmentAdapter;
import com.framework.core.fragment.BaseFragmentStateAdapter;
import com.framework.core.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 * 首页碎片容器
 * 包含推荐页和各个首页
 */


public class HomeFragment extends AbsMianFragment<HomePresenter,HomeModel> implements HomeContact.IHomeView {
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;

//    private BaseFragmentStateAdapter mAdapter;
    private ClassifyLevelResult mResult;
//    private List<ClassifyLevelBean> mTypes;
//    private List<Fragment> mFragments;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        RxViewUtil.addOnClick(mRxManager, rl_search, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Bundle bundle = new Bundle();
                bundle.putInt(SearchActivity.SEARCH_TYPE,0);
                ((BaseHelperUtil)mHelperUtil).startAty(SearchActivity.class,bundle);
            }
        });
//        mTypes = new ArrayList<>();
//        mFragments = new ArrayList<>();
//        mAdapter = new BaseFragmentStateAdapter(getChildFragmentManager(),mFragments);
//        view_pager.setAdapter(mAdapter);
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void getClassifyLevel1Success(ClassifyLevelResult result) {
        //初始化Tab，创建Fragment
        if(mResult == null || !mResult.toString().equals(result.toString())){
            //重新创建刷新
            mResult = result;
            refreshTabs();
        }
    }

    private void refreshTabs() {
        if(mResult != null && !CollectionUtil.isEmpty(mResult.getOneClass())){
            List<ClassifyLevelBean> types = new ArrayList<>();
            List<Fragment> fragments = new ArrayList<>();
//            mTypes.clear();
//            mFragments.clear();

            List<ClassifyLevelBean> results = mResult.getOneClass();
            types.addAll(results);

            fragments.add(new RecommendFragment());
            int size = types.size();
            for(int i=0;i<size;i++){
                fragments.add(TypeFragment.newInstance(types.get(i)));
            }

            ClassifyLevelBean recommendType = new ClassifyLevelBean();
            recommendType.setName("推荐");
            recommendType.setId(0);
            types.add(0,recommendType);
            String[] titles = new String[types.size()];
            for (int j=0; j<titles.length;j++){
                titles[j] = types.get(j).getName();
            }
            BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(),fragments);
            view_pager.setAdapter(adapter);
            tab.setViewPager(view_pager,titles);
        }
    }

    @Override
    protected void come() {
        mPresenter.getClassifyLevel1();
    }

    @Override
    protected void leave() {

    }
}
