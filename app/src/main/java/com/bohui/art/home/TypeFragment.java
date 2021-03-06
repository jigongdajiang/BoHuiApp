package com.bohui.art.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.BannerBean;
import com.bohui.art.bean.common.BannerResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.bean.home.RecommendListItemBean;
import com.bohui.art.bean.home.RecommendMechanismBean;
import com.bohui.art.bean.home.Type2ListItemBean;
import com.bohui.art.bean.home.TypeResult;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.adapter.Art1Plus2Adapter;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.adapter.BannerAdapter;
import com.bohui.art.home.adapter.OrgGridAdapter;
import com.bohui.art.home.adapter.Type2LevelAdapter;
import com.bohui.art.home.adapter.TypeTopAdapter;
import com.bohui.art.home.art1.Art1Activity;
import com.bohui.art.home.art2.Art2Activity;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.home.mvp.HomeContact;
import com.bohui.art.home.mvp.TypeModel;
import com.bohui.art.home.mvp.TypePresenter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.framework.core.util.StrOperationUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class TypeFragment extends AbsNetBaseFragment<TypePresenter, TypeModel> implements HomeContact.ITypedView {
    @BindView(R.id.ptr)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    private TypeResult mResult;
    private DelegateAdapter mDelegateAdapter;
    public static final String TYPE = "type";
    private ClassifyLevelBean mType;

    public static TypeFragment newInstance(ClassifyLevelBean type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE, type);
        TypeFragment fragment = new TypeFragment();
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
        return R.layout.fragment_type;
    }

    @Override
    public void initView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        mDelegateAdapter = new DelegateAdapter(virtualLayoutManager);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(mDelegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml() {
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                if (adapter instanceof Type2LevelAdapter) {
                    ClassifyLevelBean bean = ((Type2LevelAdapter) adapter).getData(position);
                    if(bean != null && !bean.isBu()){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Art2Activity.TYPE,((Type2LevelAdapter) adapter).getData(position));
                        startAty(Art2Activity.class,bundle);
                    }
                } else if (adapter instanceof TypeTopAdapter) {
                    ClassifyLevelBean bean = ((TypeTopAdapter) adapter).getData(position);
                    if(bean != null){
                        if (bean.getLevel() == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Art1Activity.CLASSIFY_LEVEL1,bean);
                            startAty(Art1Activity.class,bundle);
                        } else if(bean.getLevel() == 2) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Art2Activity.TYPE,bean);
                            startAty(Art2Activity.class,bundle);
                        }
                    }
                } else if (adapter instanceof Art1Plus2Adapter
                        || adapter instanceof OrgGridAdapter
                        || adapter instanceof ArtGridAdapter) {
                    long id =0;
                    if(adapter.getData(position) instanceof  ArtCoverItemBean){
                        id = ((ArtCoverItemBean)adapter.getData(position)).getId();
                    }else if(adapter.getData(position) instanceof ArtItemBean){
                        id = ((ArtItemBean)adapter.getData(position)).getAid();
                    }
                    ArtDetailActivity.comeIn(getActivity(),id);
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
        mPresenter.getTypeInfo(mType.getId());
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        mPtrClassicFrameLayout.refreshComplete();
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void getTypeInfoSuccess(TypeResult result) {
        mPtrClassicFrameLayout.refreshComplete();
        //刷新数据
        mResult = result;
        refreshData();
    }

    private void refreshData() {
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();

        //Banner
        List<BannerBean> bannerList = mResult.getBannerList();
        if (!CollectionUtil.isEmpty(bannerList)) {
            BannerResult bannerBeans = new BannerResult();
            bannerBeans.setBannerBeans(bannerList);

            BannerAdapter bannerAdapter = new BannerAdapter(mContext);
            bannerAdapter.addItem(bannerBeans);
            bannerAdapter.setDelegateAdapter(mDelegateAdapter);
            adapters.add(bannerAdapter);
        }
        //二级子类
        List<ClassifyLevelBean> type2LevelBeans = mResult.getCateList();
        if (!CollectionUtil.isEmpty(type2LevelBeans)) {
            Type2LevelAdapter type2LevelAdapter = new Type2LevelAdapter(mContext);
            int size = type2LevelBeans.size();
            if(size%4 != 0){
                int bu = 4 - size % 4;
                for(int i=0;i<bu;i++){
                    ClassifyLevelBean levelBean = new ClassifyLevelBean();
                    levelBean.setBu(true);
                    type2LevelBeans.add(levelBean);
                }
            }
            type2LevelAdapter.setDatas(type2LevelBeans);
            adapters.add(type2LevelAdapter);
        }
        //大类推荐
//        RecommendListItemBean level1Recommend = mResult.getBoutique();
//        if (level1Recommend != null) {
//            List<ArtCoverItemBean> level1RecommendList = level1Recommend.getList();
//            if (!CollectionUtil.isEmpty(level1RecommendList)) {
//                String typeName = level1Recommend.getName();
//                long typeId = level1Recommend.getId();
//                TypeTopAdapter typeTopAdapter = new TypeTopAdapter(mContext, new ClassifyLevelBean(typeName, typeId,0,1));
//                adapters.add(typeTopAdapter);
//                int listSize = level1RecommendList.size();
//                if (listSize == 3) {
//                    Art1Plus2Adapter onePuls2adapter = new Art1Plus2Adapter(getActivity());
//                    onePuls2adapter.setDatas(level1RecommendList);
//                    adapters.add(onePuls2adapter);
//                } else {
//                    OrgGridAdapter org2ItemAdapter = new OrgGridAdapter(mContext);
//                    org2ItemAdapter.setDatas(level1RecommendList);
//                    adapters.add(org2ItemAdapter);
//                }
//            }
//        }

        //该大类下的机构推荐
//        RecommendMechanismBean recommendMechanismBean = mResult.getMechanism();
//        if (recommendMechanismBean != null) {
//            List<ArtItemBean> list = recommendMechanismBean.getList();
//            if (!CollectionUtil.isEmpty(list)) {
//                TypeTopAdapter jigouTopAdapter = new TypeTopAdapter(mContext, new ClassifyLevelBean(recommendMechanismBean.getName(), 3,0,1));
//                adapters.add(jigouTopAdapter);
//                ArtGridAdapter mechanisAdapter = new ArtGridAdapter(mContext);
//                mechanisAdapter.setDatas(list);
//                adapters.add(mechanisAdapter);
//            }
//        }
        //各二级子分类推荐
        List<Type2ListItemBean> chird = mResult.getChird();
        if (!CollectionUtil.isEmpty(chird)) {
            int size = chird.size();
            for (int i = 0; i < size; i++) {
                Type2ListItemBean type2ListItemBean = chird.get(i);
                if (!StrOperationUtil.isEmpty(type2ListItemBean.getName())) {
                    List<ArtItemBean> list = chird.get(i).getList();
                    if (!CollectionUtil.isEmpty(list)) {
                        TypeTopAdapter typeTopAdapter = new TypeTopAdapter(mContext, new ClassifyLevelBean(
                                type2ListItemBean.getName(), type2ListItemBean.getId(), type2ListItemBean.getPid(),2));
                        adapters.add(typeTopAdapter);
                        ArtGridAdapter artGridAdapter = new ArtGridAdapter(mContext);
                        artGridAdapter.setDatas(list);
                        adapters.add(artGridAdapter);
                    }
                }
            }
        }
        mDelegateAdapter.setAdapters(adapters);
    }
}
