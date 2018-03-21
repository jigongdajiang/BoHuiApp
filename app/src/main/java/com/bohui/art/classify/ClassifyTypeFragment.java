package com.bohui.art.classify;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.classify.ClassifyLevel2Result;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.classify.mvp.ClassifyContact;
import com.bohui.art.classify.mvp.ClassifyModel;
import com.bohui.art.classify.mvp.ClassifyPresenter;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.adapter.BannerAdapter;
import com.bohui.art.home.art1.Art1Activity;
import com.bohui.art.home.art2.Art2Activity;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.framework.core.util.ResUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/13
 * @description:
 */


public class ClassifyTypeFragment extends AbsNetBaseFragment<ClassifyPresenter, ClassifyModel> implements ClassifyContact.View {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    public static final String TYPE = "type";
    private ClassifyLevelBean mType;
    private ClassifyLevel2Result mResult;

    private DelegateAdapter mDelegateAdapter;

    public static ClassifyTypeFragment newInstance(ClassifyLevelBean type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE, type);
        ClassifyTypeFragment fragment = new ClassifyTypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void doBeforeOnCreateView() {
        super.doBeforeOnCreateView();
        mType = (ClassifyLevelBean) getArguments().getSerializable(TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify_type;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        mDelegateAdapter = new DelegateAdapter(layoutManager);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mDelegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml() {
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if(adapter instanceof  ClassifyTypeDetailAdapter){
                    ClassifyLevelBean levelBean = ((ClassifyTypeDetailAdapter) adapter).getData(position);
                    Bundle bundle = new Bundle();
                    if(levelBean.getPid() == 0){
                        bundle.putSerializable(Art1Activity.CLASSIFY_LEVEL1, levelBean);
                        startAty(Art1Activity.class, bundle);
                    }else{
                        bundle.putSerializable(Art2Activity.TYPE,levelBean);
                        startAty(Art2Activity.class,bundle);
                    }
                }
            }
        });
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                doLoad();
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel, this);
    }

    @Override
    protected void doLoad() {
        mPresenter.getClassifyLevel2("" + mType.getId());
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        mPtrClassicFrameLayout.refreshComplete();
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void getClassifyLevel2Success(ClassifyLevel2Result result) {
        mPtrClassicFrameLayout.refreshComplete();
        mResult = result;
        refreshData();
    }

    private void refreshData() {
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();
        //Banner
        List<BannerBean> bannerBeans = mResult.getBannerList();
        if(!CollectionUtil.isEmpty(bannerBeans)){
            BannerAdapter bannerAdapter = new BannerAdapter(mContext);
            bannerAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_100));
            bannerAdapter.setShowIndicator(false);
            BannerResult bannerResult = new BannerResult();
            bannerResult.setBannerBeans(bannerBeans);
            bannerAdapter.addItem(bannerResult);
            adapters.add(bannerAdapter);
        }
        List<ClassifyLevelBean> classifyLevelBeans = mResult.getTwoClass();
        if (!CollectionUtil.isEmpty(classifyLevelBeans)) {
            //二级分类
            ClassTypeDeiverAdapter classTypeDeiverAdapter = new ClassTypeDeiverAdapter(mContext, mType);
            adapters.add(classTypeDeiverAdapter);
            ClassifyTypeDetailAdapter classifyTypeDetailAdapter = new ClassifyTypeDetailAdapter(mContext);
            classifyTypeDetailAdapter.setDatas(classifyLevelBeans);
            adapters.add(classifyTypeDetailAdapter);
        }
        mDelegateAdapter.setAdapters(adapters);
    }
}
