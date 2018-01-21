package com.bohui.art.home.art1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bohui.art.R;
import com.bohui.art.bean.classify.ClassifyLevel2Result;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.classify.mvp.ClassifyContact;
import com.bohui.art.classify.mvp.ClassifyModel;
import com.bohui.art.classify.mvp.ClassifyPresenter;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
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
 * @date : 2017/12/15
 * @description:
 * 艺术品二级列表
 *
 * 标题 返回  类型推荐  搜索按钮
 * tab 一级类目的所有二级类目组成
 * 列表页
 */


public class Art1Activity extends AbsNetBaseActivity<ClassifyPresenter,ClassifyModel> implements ClassifyContact.View{
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.view_pager)
    ViewPager view_pager;

    private ClassifyLevelBean level1;
    public static final String CLASSIFY_LEVEL1 = "CLASSIFY_LEVEL1";

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        level1 = (ClassifyLevelBean) getIntent().getSerializableExtra(CLASSIFY_LEVEL1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_art1;
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
                        bundle.putInt(SearchActivity.SEARCH_TYPE,0);
                        ((BaseHelperUtil)mHelperUtil).startAty(SearchActivity.class,bundle);
                    }
                })
                .builder();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getClassifyLevel2(String.valueOf(level1.getId()));
    }

    @Override
    public void getClassifyLevel2Success(ClassifyLevel2Result result) {
        //创建Tab和Fragment
        if(result != null && !CollectionUtil.isEmpty(result.getTwoClass())){
            refresh(result.getTwoClass());
        }
    }

    private void refresh(List<ClassifyLevelBean> types) {
        if(!CollectionUtil.isEmpty(types)){
            List<Fragment> fragments = new ArrayList<>();
            for(int i=0; i<types.size(); i++){
                fragments.add(Art1Fragment.newInstance(types.get(i)));
            }
            BaseFragmentStateAdapter adapter = new BaseFragmentStateAdapter(getSupportFragmentManager(),fragments);
            view_pager.setAdapter(adapter);
            String[] titles = new String[types.size()];
            for (int j=0; j<titles.length;j++){
                titles[j] = types.get(j).getName();
            }
            tab.setViewPager(view_pager,titles);
        }
    }
}
