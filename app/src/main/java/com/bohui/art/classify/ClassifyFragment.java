package com.bohui.art.classify;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.classify.ClassifyLevel2Result;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.bean.home.ClassifyLevelResult;
import com.bohui.art.classify.mvp.ClassifyContact;
import com.bohui.art.classify.mvp.ClassifyModel;
import com.bohui.art.classify.mvp.ClassifyPresenter;
import com.bohui.art.common.fragment.AbsMianFragment;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.home.mvp.HomeContact;
import com.bohui.art.home.mvp.HomeModel;
import com.bohui.art.home.mvp.HomePresenter;
import com.bohui.art.search.SearchActivity;
import com.framework.core.fragment.BaseFragmentAdapter;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.viewpager.RollCtrlViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/9
 * @description:
 */


public class ClassifyFragment extends AbsMianFragment<HomePresenter,HomeModel> implements HomeContact.IHomeView {
    @BindView(R.id.rv_classify)
    RecyclerView rv;
    @BindView(R.id.vp_classify)
    RollCtrlViewPager rollCtrlViewPager;
    @BindView(R.id.rl_search)
    RelativeLayout rl_search;

    private DelegateAdapter mDelegateAdapter;
    private ClassifyTypeAdapter classifyTypeAdapter;
    private BaseFragmentAdapter mFragmentAdapter;
    private List<Fragment> mFragments;

    private ClassifyLevelResult mResult;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify;
    }

    @Override
    public void initView() {
        RxViewUtil.addOnClick(mRxManager, rl_search, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Bundle bundle = new Bundle();
                bundle.putInt(SearchActivity.SEARCH_TYPE,0);
                startAty(SearchActivity.class,bundle);
            }
        });
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        mDelegateAdapter = new DelegateAdapter(layoutManager);
        classifyTypeAdapter = new ClassifyTypeAdapter(mContext);
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        adapters.add(classifyTypeAdapter);
        mDelegateAdapter.setAdapters(adapters);
        mDelegateAdapter.notifyDataSetChanged();

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mDelegateAdapter);

        mFragments = new ArrayList<>();
        mFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(),mFragments);
        rollCtrlViewPager.setAdapter(mFragmentAdapter);


        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                rollCtrlViewPager.setCurrentItem(position);
                List<ClassifyLevelBean> datas = adapter.getDatas();
                for(int i=0;i<datas.size();i++){
                    if(position == i){
                        datas.get(i).setChecked(true);
                    }else{
                        datas.get(i).setChecked(false);
                    }
                }
                mDelegateAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void come() {
        mPresenter.getClassifyLevel1();
    }

    @Override
    protected void leave() {

    }

    @Override
    public void getClassifyLevel1Success(ClassifyLevelResult result) {
        ClassifyLevelBean beanRm = new ClassifyLevelBean();
        beanRm.setName("热门分类");
        beanRm.setId(0);
        beanRm.setChecked(true);
        result.getOneClass().add(0,beanRm);
        if(mResult == null || (result != null && !mResult.toString().equals(result.toString()))){
            mResult = result;
            refreshData();
        }
    }

    private void refreshData() {
        List<ClassifyLevelBean> oneClass = mResult.getOneClass();
        if(!CollectionUtil.isEmpty(oneClass)){
            classifyTypeAdapter.replaceAllItem(oneClass);
            rollCtrlViewPager.setOffscreenPageLimit(oneClass.size());
            mFragments.clear();
            for(ClassifyLevelBean bean :oneClass){
                mFragments.add(ClassifyTypeFragment.newInstance(bean));
            }
            mFragmentAdapter.notifyDataSetChanged();
        }
    }
}
