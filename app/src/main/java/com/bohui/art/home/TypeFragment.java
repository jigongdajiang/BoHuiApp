package com.bohui.art.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.common.helperutil.NetBaseHelperUtil;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.adapter.Art1Plus2Adapter;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.adapter.OrgGridAdapter;
import com.bohui.art.home.adapter.Type2LevelAdapter;
import com.bohui.art.home.adapter.TypeTopAdapter;
import com.bohui.art.home.art1.Art1Activity;
import com.bohui.art.home.art2.Art2Activity;
import com.bohui.art.home.bean.ArtBean;
import com.bohui.art.home.bean.Type2LevelBean;
import com.bohui.art.home.bean.TypeBean;
import com.bohui.art.home.bean.TypeTopBean;
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


public class TypeFragment extends AbsNetBaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    public static final String TYPE = "type";
    private TypeBean mType;
    public static TypeFragment newInstance(TypeBean type){
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE,type);
        TypeFragment fragment = new TypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void doBeforeOnCreateView() {
        super.doBeforeOnCreateView();
        mType = (TypeBean) getArguments().getSerializable(TYPE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    public void initView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getActivity());
        final DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        //二级子类
        List<Type2LevelBean> type2LevelBeans = new ArrayList<>();
        for (int i=0;i<8;i++){
            type2LevelBeans.add(new Type2LevelBean(RecommendFragment.imgs[i%3],mType.getType()+i,i));
        }
        Type2LevelAdapter type2LevelAdapter = new Type2LevelAdapter(mContext);
        type2LevelAdapter.setDatas(type2LevelBeans);
        delegateAdapter.addAdapter(type2LevelAdapter);

        //第一个分类(国画)的Top
        TypeTopAdapter typeTopAdapter1 = new TypeTopAdapter(mContext,new TypeTopBean(mType.getType()+"推荐",1,0));
        delegateAdapter.addAdapter(typeTopAdapter1);

        //国画数据适配器
        Art1Plus2Adapter onePuls2adapter = new Art1Plus2Adapter(getActivity());
        List<ArtBean> artBeans = new ArrayList<>();
        for(int i=0;i<3;i++){
            artBeans.add(new ArtBean());
        }
        onePuls2adapter.setDatas(artBeans);
        delegateAdapter.addAdapter(onePuls2adapter);

        //第二个分类(机构国画推荐)
        TypeTopAdapter typeTopAdapter2 = new TypeTopAdapter(mContext,new TypeTopBean("机构"+mType.getType()+"推荐",3,0));
        delegateAdapter.addAdapter(typeTopAdapter2);

        //机构推荐数据适配器
        OrgGridAdapter org2ItemAdapter = new OrgGridAdapter(mContext);
        List<ArtBean> jigoubeans = new ArrayList<>();
        for(int i=0;i<4;i++){
            jigoubeans.add(new ArtBean());
        }
        org2ItemAdapter.setDatas(jigoubeans);
        delegateAdapter.addAdapter(org2ItemAdapter);

        for (int i=0;i<8;i++){
            //第五个分类(猜你喜欢)
            TypeTopAdapter typeTopAdapter5 = new TypeTopAdapter(mContext,new TypeTopBean(type2LevelBeans.get(i).getType(),5,1));
            delegateAdapter.addAdapter(typeTopAdapter5);

            //猜你喜欢数据适配器
            ArtGridAdapter artGridAdapter = new ArtGridAdapter(mContext);
            List<ArtBean> artBeansLikes = new ArrayList<>();
            for(int j=0;j<4;j++){
                artBeansLikes.add(new ArtBean());
            }
            artGridAdapter.setDatas(artBeansLikes);
            delegateAdapter.addAdapter(artGridAdapter);
        }
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
               if(adapter instanceof  Type2LevelAdapter){
                   ((NetBaseHelperUtil)mHelperUtil).startAty(Art2Activity.class);
               }else if(adapter instanceof TypeTopAdapter){
                   TypeTopBean bean = ((TypeTopAdapter) adapter).getData(position);
                   if(bean.getGrade() == 0){
                       ((NetBaseHelperUtil)mHelperUtil).startAty(Art1Activity.class);
                   }else{
                       ((NetBaseHelperUtil)mHelperUtil).startAty(Art2Activity.class);
                   }
               }else if(adapter instanceof Art1Plus2Adapter
                       || adapter instanceof OrgGridAdapter
                       || adapter instanceof ArtGridAdapter){
                   ArtDetailActivity.comeIn(getActivity(),new Bundle());
               }
            }
        });
    }
}
