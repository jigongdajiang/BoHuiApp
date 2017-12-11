package com.bohui.art.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.home.adapter.Art1Plus2Adapter;
import com.bohui.art.home.adapter.ArtGridAdapter;
import com.bohui.art.home.adapter.OrgGridAdapter;
import com.bohui.art.home.adapter.Type2LevelAdapter;
import com.bohui.art.home.adapter.TypeTopAdapter;
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
    String imgs[] = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042545&di=053c45cd7e7da1412e81221e88c9824d&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fc2cec3fdfc03924589eab7228c94a4c27d1e25bb.jpg"
            ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042545&di=7f002625cee037a453bec91218d26416&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2Fa9d3fd1f4134970a7f507e029ecad1c8a7865dff.jpg"
            ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512897042544&di=4558d62428b5a41ca639f10455457620&imgtype=0&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F9c16fdfaaf51f3dedda391499feef01f3a29798d.jpg"

    };
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
            type2LevelBeans.add(new Type2LevelBean(imgs[i%3],mType.getType()+i,i));
        }
        Type2LevelAdapter type2LevelAdapter = new Type2LevelAdapter(mContext);
        type2LevelAdapter.setDatas(type2LevelBeans);
        delegateAdapter.addAdapter(type2LevelAdapter);

        //第一个分类(国画)的Top
        TypeTopAdapter typeTopAdapter1 = new TypeTopAdapter(mContext,new TypeTopBean(mType.getType()+"推荐",1));
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
        TypeTopAdapter typeTopAdapter2 = new TypeTopAdapter(mContext,new TypeTopBean("机构"+mType.getType()+"推荐",3));
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
            TypeTopAdapter typeTopAdapter5 = new TypeTopAdapter(mContext,new TypeTopBean(type2LevelBeans.get(i).getType(),5));
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
                String a = "a";
            }
        });
    }
}
