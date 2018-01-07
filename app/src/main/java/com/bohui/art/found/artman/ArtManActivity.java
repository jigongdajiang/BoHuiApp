package com.bohui.art.found.artman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bohui.art.R;
import com.bohui.art.bean.found.ArtManLevelResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.bean.home.TypeBean;
import com.bohui.art.found.artman.mvp.ArtManLevelModel;
import com.bohui.art.found.artman.mvp.ArtManLevelPresenter;
import com.bohui.art.found.artman.mvp.ArtManListContact;
import com.bohui.art.search.SearchActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.framework.core.base.BaseHelperUtil;
import com.framework.core.fragment.BaseFragmentStateAdapter;
import com.framework.core.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class ArtManActivity extends AbsNetBaseActivity<ArtManLevelPresenter,ArtManLevelModel> implements ArtManListContact.IArtManLevelView {
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    private BaseFragmentStateAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_art_man;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("国画推荐")
                .setRightImage1(R.mipmap.ic_search)
                .setRightImage1ClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //进入搜索页
                        Bundle bundle = new Bundle();
                        bundle.putInt(SearchActivity.SEARCH_TYPE,1);
                        ((BaseHelperUtil)mHelperUtil).startAty(SearchActivity.class,bundle);
                    }
                })
                .builder();
        List<TypeBean> types = new ArrayList<>();
        types.add(new TypeBean(1,"山水"));
        types.add(new TypeBean(2,"花鸟"));
        types.add(new TypeBean(3,"人物"));
        types.add(new TypeBean(4,"机构国画"));
        refresh(types);
    }
    private void refresh(List<TypeBean> types) {
        List<Fragment> fragments = new ArrayList<>();
        if(!CollectionUtil.isEmpty(types)){
            for(int i=0; i<types.size(); i++){
                fragments.add(ArtManListFragment.newInstance(types.get(i)));
            }
            mAdapter = new BaseFragmentStateAdapter(getSupportFragmentManager(),fragments);
            view_pager.setAdapter(mAdapter);
            String[] titles = new String[types.size()];
            for (int j=0; j<titles.length;j++){
                titles[j] = types.get(j).getType();
            }
            tab.setViewPager(view_pager,titles);
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtManLevel();
    }

    @Override
    public void getArtManLevelSuccess(ArtManLevelResult result) {

    }
}
