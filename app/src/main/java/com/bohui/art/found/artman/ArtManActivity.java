package com.bohui.art.found.artman;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bohui.art.R;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.home.art1.Art1Fragment;
import com.bohui.art.home.bean.TypeBean;
import com.flyco.tablayout.SlidingTabLayout;
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


public class ArtManActivity extends AbsNetBaseActivity {
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
}
