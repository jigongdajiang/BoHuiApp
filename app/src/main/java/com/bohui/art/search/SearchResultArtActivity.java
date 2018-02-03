package com.bohui.art.search;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bohui.art.R;
import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.bean.home.ClassifyLevelBean;
import com.bohui.art.bean.search.AllClassifyBean;
import com.bohui.art.bean.search.AllClassifyResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.net.AppProgressSubScriber;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.rv.adapter.NormalWrapAdapter;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.detail.art.ArtDetailActivity;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.home.art2.Art2Activity;
import com.bohui.art.search.mvp.GetAllClassifyContact;
import com.bohui.art.search.mvp.GetAllClassifyModel;
import com.bohui.art.search.mvp.GetAllClassifyPresenter;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.framework.core.http.EasyHttp;
import com.framework.core.http.exception.ApiException;
import com.framework.core.util.CollectionUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.listener.RvClickListenerIml;
import com.widget.smallelement.dialog.BasePowfullDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description: 艺术品搜索结果页
 */


public class SearchResultArtActivity extends AbsNetBaseActivity<ArtListPresenter, ArtListModel> implements ArtListContact.View,GetAllClassifyContact.View {
    @BindView(R.id.ll_sequence)
    LinearLayout ll_sequence;
    @BindView(R.id.rl_common)
    RelativeLayout rl_common;
    @BindView(R.id.tv_common)
    TextView tv_common;
    @BindView(R.id.rl_fans)
    RelativeLayout rl_fans;
    @BindView(R.id.tv_fans)
    TextView tv_fans;
    @BindView(R.id.rl_price)
    RelativeLayout rl_price;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.rl_filtrate)
    RelativeLayout rl_filtrate;
    @BindView(R.id.tv_filtrate)
    TextView tv_filtrate;

    //综合弹出View
    @BindView(R.id.ll_common_list)
    LinearLayout ll_common_list;
    @BindView(R.id.ll_select)
    LinearLayout ll_select;
    @BindView(R.id.tv_xinpin)
    TextView tv_xinpin;
    @BindView(R.id.tv_recommend)
    TextView tv_recommend;
    @BindView(R.id.v_shadow)
    View v_shadow;

    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @BindView(R.id.rv)
    RecyclerView rv;

    private String mSearchKey;//模糊查询串

    private Art2Adapter adapter;

    private List<DelegateAdapter.Adapter> typeTagAdapters;
    private DelegateAdapter filtrateDelegateAdapter;

    private BasePowfullDialog filtrateDialog;

    private GetAllClassifyModel getAllClassifyModel;
    private GetAllClassifyPresenter getAllClassifyPresenter;
    private ArtListParam param = new ArtListParam();
    private boolean isRefresh;
    private boolean isRequesting;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mSearchKey = getIntent().getStringExtra(SearchActivity.SEARCH_KEY);
        param.setName(mSearchKey);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result_art;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setLeftText(mSearchKey)
                .builder();
        ll_sequence.setVisibility(View.VISIBLE);
        initSequence();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        adapter = new Art2Adapter(mContext);
        adapter.setDelegateAdapter(delegateAdapter);
        NormalWrapAdapter wrapper = new NormalWrapAdapter(mContext,adapter);
        adapter.setWrapper(wrapper);
        wrapper.setDelegateAdapter(delegateAdapter);
        delegateAdapter.addAdapter(wrapper);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
        rv.addOnItemTouchListener(new RvClickListenerIml(){
            @Override
            public void onItemClick(BaseAdapter adapter, View view, int position) {
                ArtItemBean itemBean = (ArtItemBean) adapter.getData(position);
                if(itemBean != null){
                    ArtDetailActivity.comeIn(SearchResultArtActivity.this,itemBean.getAid());
                }
            }
        });
        ptrClassicFrameLayout.setAutoLoadMoreEnable(false);
        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestFirstPage();
            }
        });
        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                requestNextPage();
            }
        });
    }

    /**
     * 初始化筛选对话框
     */
    private void initSequence() {
        initFiltrate();
        RxViewUtil.addOnClick(mRxManager, rl_filtrate, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                //弹出筛选对话框
                if(filtrateDialog != null){
                    filtrateDialog.showDialog();
                }
            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_common, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                if (ll_common_list.getVisibility() == View.GONE) {
                    //显示
                    ll_common_list.setVisibility(View.VISIBLE);
                    ll_select.setVisibility(View.VISIBLE);
                    ll_select.setAnimation(AnimationUtils.loadAnimation(mContext, com.widget.smallelement.R.anim.dd_menu_in));
                    v_shadow.setVisibility(View.VISIBLE);
                    v_shadow.setAnimation(AnimationUtils.loadAnimation(mContext, com.widget.smallelement.R.anim.dd_mask_in));
                } else {
                    hideCommon();
                }
            }
        });
        RxViewUtil.addOnClick(mRxManager, v_shadow, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                hideCommon();
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_xinpin, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                param.setYouxian(1);
                requestFirstPage();
                hideCommon();
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_recommend, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                param.setYouxian(2);
                requestFirstPage();
                hideCommon();
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_fans, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                param.setLooksort(0);
                requestFirstPage();
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_price, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                param.setPricesort(0);
                requestFirstPage();
            }
        });

    }

    @NonNull
    private void initFiltrate() {
        getAllClassifyModel = new GetAllClassifyModel();
        getAllClassifyPresenter = new GetAllClassifyPresenter();
        getAllClassifyPresenter.setMV(getAllClassifyModel,this);
        getAllClassifyPresenter.getAllClassify();
    }

    private void hideCommon() {
        //隐藏
        ll_select.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(mContext, com.widget.smallelement.R.anim.dd_menu_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ll_common_list.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ll_select.setAnimation(animation);
        v_shadow.setVisibility(View.GONE);
        v_shadow.setAnimation(AnimationUtils.loadAnimation(mContext, com.widget.smallelement.R.anim.dd_mask_out));
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel, this);
    }

    @Override
    protected void extraInit() {
        requestFirstPage();
    }

    private void requestFirstPage(){
        isRefresh = true;
        param.setStart(0);
        request();
    }
    private void requestNextPage(){
        isRefresh = false;
        param.setStart(param.getStart()+param.getLength());
        request();
    }
    private void request() {
        if(!isRequesting){
            mPresenter.getArtList(param);
            isRequesting = true;
        }
    }

    @Override
    protected boolean childInterceptException(String apiName, ApiException e) {
        if(apiName.equals(ArtListContact.TAG_ART_LIST)){
            isRequesting = false;
            if(isRefresh){
                ptrClassicFrameLayout.refreshComplete();
            }else{
                ptrClassicFrameLayout.setLoadMoreEnable(false);
                ptrClassicFrameLayout.loadMoreComplete(false);
            }
        }
        return super.childInterceptException(apiName, e);
    }

    @Override
    public void getArtListSuccess(ArtListResult result) {
        isRequesting = false;
        List<ArtItemBean> list = result.getPaintingList();
        if(isRefresh){
            ptrClassicFrameLayout.refreshComplete();
            if(!CollectionUtil.isEmpty(list)){
                adapter.replaceAllItem(list);
                if(list.size() >= param.getLength()){
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                }else{
                    ptrClassicFrameLayout.setLoadMoreEnable(false);
                }
            }else{
                ptrClassicFrameLayout.setLoadMoreEnable(false);
            }
        }else{
            if(CollectionUtil.isEmpty(list)){
                ptrClassicFrameLayout.loadMoreComplete(false);
            }else{
                adapter.addItems(list);
                if(list.size() >= param.getLength()){
                    ptrClassicFrameLayout.loadMoreComplete(true);
                }else{
                    ptrClassicFrameLayout.loadMoreComplete(false);
                }
            }
        }
    }

    @Override
    public void getAllClassifySuccess(AllClassifyResult result) {
        if(result != null && !CollectionUtil.isEmpty(result.getList())){
            filtrateDialog = new BasePowfullDialog.Builder(R.layout.dialog_filtrate, mContext, getSupportFragmentManager())
                    .setDialogAnim(R.style.FiltrateDialogAnim)
                    .setDialogWidthForScreen(1.0)
                    .setCanceledOnTouchOutside(true)
                    .isCanExistWidthSoft(false)
                    .setDialogOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return false;
                        }
                    })
                    .builder()
                    .setViewClickCancel(R.id.rl_translate);
            TextView tv_btn_reset = (TextView) filtrateDialog.getInsideView(R.id.tv_btn_reset);
            TextView tv_btn_ok = (TextView) filtrateDialog.getInsideView(R.id.tv_btn_ok);

            RecyclerView rvFiltrate = (RecyclerView) filtrateDialog.getInsideView(R.id.rv);
            VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
            filtrateDelegateAdapter = new DelegateAdapter(virtualLayoutManager);
            final FiltratePriceAdapter filtratePriceAdapter = new FiltratePriceAdapter(mContext, "价格区间");
            filtrateDelegateAdapter.addAdapter(filtratePriceAdapter);

            typeTagAdapters = new ArrayList<>();

            final List<Long> oneClassIds = new ArrayList<>();
            final List<Long> twoClassIds = new ArrayList<>();
            for (AllClassifyBean allClassifyBean : result.getList()) {
                final FiltrateTagAdapter typeTagAdapter = new FiltrateTagAdapter(mContext, allClassifyBean);
                typeTagAdapter.setOnSelectListener(new FiltrateTagAdapter.OnSelectListener() {

                    @Override
                    public void onParentHasSelected(AllClassifyBean oneClass, boolean has) {
                        if(has){
                            oneClassIds.add(oneClass.getId());
                        }else{
                            oneClassIds.remove(oneClass.getId());
                        }
                    }

                    @Override
                    public void onSelectedOne(ClassifyLevelBean twoClass) {
                        twoClassIds.add(twoClass.getId());
                    }

                    @Override
                    public void unSelectOne(ClassifyLevelBean twoClass) {
                        twoClassIds.remove(twoClass.getId());
                    }
                });
                typeTagAdapter.setDelegateAdapter(filtrateDelegateAdapter);
                typeTagAdapters.add(typeTagAdapter);
            }
            filtrateDelegateAdapter.addAdapters(typeTagAdapters);
            rvFiltrate.setLayoutManager(virtualLayoutManager);
            rvFiltrate.setAdapter(filtrateDelegateAdapter);

            RxViewUtil.addOnClick(mRxManager, tv_btn_reset, new Consumer() {
                @Override
                public void accept(Object o) throws Exception {
                    oneClassIds.clear();
                    twoClassIds.clear();
                    filtratePriceAdapter.reset();
                    for (DelegateAdapter.Adapter typeTagAdapter : typeTagAdapters) {
                        ((FiltrateTagAdapter)typeTagAdapter).reset();
                    }
                }
            });
            RxViewUtil.addOnClick(mRxManager, tv_btn_ok, new Consumer() {
                @Override
                public void accept(Object o) throws Exception {
                    filtrateDialog.miss();
                    param.setStartprice(filtratePriceAdapter.getPrice1());
                    param.setEndprice(filtratePriceAdapter.getPrice2());
                    param.getOneclass().clear();
                    param.getOneclass().addAll(oneClassIds);
                    param.getTowclass().clear();
                    param.getTowclass().addAll(twoClassIds);
                    requestFirstPage();
                }
            });
        }
    }
}
