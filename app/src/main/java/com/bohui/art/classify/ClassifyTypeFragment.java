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
import com.bohui.art.classify.mvp.ClassifyContact;
import com.bohui.art.classify.mvp.ClassifyModel;
import com.bohui.art.classify.mvp.ClassifyPresenter;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.util.helperutil.NetBaseHelperUtil;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.adapter.BannerAdapter;
import com.bohui.art.home.art2.Art2Activity;
import com.bohui.art.bean.home.Type2LevelBean;
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


public class ClassifyTypeFragment extends AbsNetBaseFragment<ClassifyPresenter,ClassifyModel> implements ClassifyContact.View {
    @BindView(R.id.rv_classify_type)
    RecyclerView rv_classify_type;

    public static final String TYPE = "type";
    private ClassifyTypeBean mType;
    public static ClassifyTypeFragment newInstance(ClassifyTypeBean type){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE,type);
        ClassifyTypeFragment fragment = new ClassifyTypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void doBeforeOnCreateView() {
        super.doBeforeOnCreateView();
        mType = (ClassifyTypeBean) getArguments().getSerializable(TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classify_type;
    }

    @Override
    public void initView() {
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager);
        //Banner
        BannerAdapter bannerAdapter = new BannerAdapter(mContext);
        bannerAdapter.setBannerHeight(ResUtil.getResDimensionPixelOffset(mContext,R.dimen.dp_100));
        bannerAdapter.setShowIndicator(false);
        List<BannerBean> bannerDatas = new ArrayList<>();
        bannerDatas.add(new BannerBean("banner1","http://www.baidu.com", RecommendFragment.imgs[0]
        ));
        bannerDatas.add(new BannerBean("banner2","http://www.baidu.com",RecommendFragment.imgs[1]
        ));
        bannerDatas.add(new BannerBean("banner2","http://www.baidu.com",RecommendFragment.imgs[2]
        ));
        BannerResult bannerBeans = new BannerResult();
        bannerBeans.setBannerBeans(bannerDatas);
        bannerAdapter.addItem(bannerBeans);
        delegateAdapter.addAdapter(bannerAdapter);

        ClassTypeDeiverAdapter classTypeDeiverAdapter = new ClassTypeDeiverAdapter(mContext,mType);
        delegateAdapter.addAdapter(classTypeDeiverAdapter);
        //二级子类
        List<Type2LevelBean> type2LevelBeans = new ArrayList<>();
        for (int i=0;i<8;i++){
            type2LevelBeans.add(new Type2LevelBean(RecommendFragment.imgs[i%3],mType.getType()+i,i));
        }
        ClassifyTypeDetailAdapter classifyTypeDetailAdapter = new ClassifyTypeDetailAdapter(mContext);
        classifyTypeDetailAdapter.setDatas(type2LevelBeans);
        delegateAdapter.addAdapter(classifyTypeDetailAdapter);
        rv_classify_type.setLayoutManager(layoutManager);
        rv_classify_type.setAdapter(delegateAdapter);
        rv_classify_type.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ((NetBaseHelperUtil)mHelperUtil).startAty(Art2Activity.class);
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void doLoad() {
        mPresenter.getClassifyLevel2("1");
    }

    @Override
    public void getClassifyLevel2Success(ClassifyLevel2Result result) {

    }
}
