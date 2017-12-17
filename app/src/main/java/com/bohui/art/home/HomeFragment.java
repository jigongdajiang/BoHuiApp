package com.bohui.art.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.home.bean.TypeBean;
import com.bohui.art.search.SearchActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.framework.core.base.BaseHelperUtil;
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
 */


public class HomeFragment extends AbsNetBaseFragment {
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;

    private BaseFragmentStateAdapter mAdapter;
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
        List<TypeBean> types = new ArrayList<>();
        types.add(new TypeBean(1,"国画"));
        types.add(new TypeBean(2,"油画"));
        types.add(new TypeBean(3,"版画"));
        types.add(new TypeBean(4,"书法"));
        types.add(new TypeBean(5,"壁画"));
        types.add(new TypeBean(6,"其它"));
        refresh(types);
    }
    private void refresh(List<TypeBean> types){
        TypeBean recommendType = new TypeBean(0,"推荐");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        if(!CollectionUtil.isEmpty(types)){
            for(int i=0; i<types.size(); i++){
                fragments.add(TypeFragment.newInstance(types.get(i)));
            }
            mAdapter = new BaseFragmentStateAdapter(getChildFragmentManager(),fragments);
            view_pager.setAdapter(mAdapter);
            types.add(0,recommendType);
            String[] titles = new String[types.size()];
            for (int j=0; j<titles.length;j++){
                titles[j] = types.get(j).getType();
            }
            tab.setViewPager(view_pager,titles);
        }
    }
}
