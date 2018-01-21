package com.bohui.art.search;

import android.content.DialogInterface;
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
import com.bohui.art.bean.common.ArtListResult;
import com.bohui.art.bean.home.ArtItemBean;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.mvp.ArtListContact;
import com.bohui.art.common.mvp.ArtListModel;
import com.bohui.art.common.mvp.ArtListPresenter;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.found.artman.ArtManListAdapter;
import com.bohui.art.bean.found.ArtManListResult;
import com.bohui.art.home.RecommendFragment;
import com.bohui.art.home.adapter.DesignerAdapter;
import com.bohui.art.home.art1.Art2Adapter;
import com.bohui.art.bean.home.ArtCoverItemBean;
import com.bohui.art.bean.home.RecommendDesignerBean;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.smallelement.dialog.BasePowfullDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 * 搜索结果页
 * 接受两个参数 关键字 和 类型
 * 艺术品 结果 有上面导航和刷选
 * 艺术家 和 设计师 直接就是一个列表
 */


public class SearchResultActivity extends AbsNetBaseActivity<ArtListPresenter,ArtListModel> implements ArtListContact.View {
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

    @BindView(R.id.rv)
    RecyclerView rv;

    public static final String SEARCH_KEY = "search_key";
    private String mSearchKey;
    public static final String SEARCH_TYPE = "search_type";
    private int mType;

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mSearchKey = getIntent().getStringExtra(SEARCH_KEY);
        mType = getIntent().getIntExtra(SEARCH_TYPE,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setLeftText(mSearchKey)
                .builder();
        if(mType == 0){
            ll_sequence.setVisibility(View.VISIBLE);
            initSequence();
        }else{
            ll_sequence.setVisibility(View.GONE);
        }
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        BaseAdapter contentAdapter = null;
        switch (mType){
            case 0:
                contentAdapter = new Art2Adapter(mContext);
                List<ArtCoverItemBean> artBeans = new ArrayList<>();
                for(int i=0;i<20;i++){
                    artBeans.add(new ArtCoverItemBean());
                }
                contentAdapter.setDatas(artBeans);
                break;
            case 1:
                contentAdapter = new ArtManListAdapter(mContext);
                List<ArtManListResult> artManListItemBeans = new ArrayList<>();
                for(int j=0;j<20;j++){
                    ArtManListResult artManListItemBean = new ArtManListResult();
                    artManListItemBean.setArtManAvr(RecommendFragment.imgs[j%RecommendFragment.imgs.length]);
                    List<ArtItemBean> artBeans2 = new ArrayList<>();
                    for(int m=0; m<10;m++ ){
                        ArtItemBean artBean = new ArtItemBean();
                        artBean.setCover(RecommendFragment.imgs[m%RecommendFragment.imgs.length]);
                        artBeans2.add(artBean);
                    }
                    artManListItemBean.setArtBeans(artBeans2);
                    artManListItemBeans.add(artManListItemBean);
                }
                contentAdapter.setDatas(artManListItemBeans);
                break;
            case 2:
                contentAdapter = new DesignerAdapter(mContext);
                List<RecommendDesignerBean> designerBeans = new ArrayList<>();
                for(int i=0;i<20;i++){
                    RecommendDesignerBean designerBean = new RecommendDesignerBean();
                    designerBeans.add(designerBean);
                }
                contentAdapter.setDatas(designerBeans);
                break;
        }

        delegateAdapter.addAdapter(contentAdapter);
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
    }

    /**
     * 初始化筛选对话框
     */
    private void initSequence() {
        final BasePowfullDialog basePowfullDialog = new BasePowfullDialog.Builder(R.layout.dialog_filtrate,mContext,getSupportFragmentManager())
                .setDialogAnim(R.style.FiltrateDialogAnim)
                .setDialogWidthForScreen(1.0)
                .setCanceledOnTouchOutside(true)
                .setDialogOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        return false;
                    }
                })
                .builder()
                .setViewClickCancel(R.id.rl_translate);
        RecyclerView rvFiltrate = (RecyclerView) basePowfullDialog.getInsideView(R.id.rv);
        initRv(rvFiltrate);
        RxViewUtil.addOnClick(mRxManager, rl_filtrate, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                //弹出筛选对话框
                basePowfullDialog.showDialog();
            }
        });
        RxViewUtil.addOnClick(mRxManager, rl_common, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                if(ll_common_list.getVisibility() == View.GONE){
                    //显示
                    ll_common_list.setVisibility(View.VISIBLE);
                    ll_select.setVisibility(View.VISIBLE);
                    ll_select.setAnimation(AnimationUtils.loadAnimation(mContext, com.widget.smallelement.R.anim.dd_menu_in));
                    v_shadow.setVisibility(View.VISIBLE);
                    v_shadow.setAnimation(AnimationUtils.loadAnimation(mContext, com.widget.smallelement.R.anim.dd_mask_in));
                }else{
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

    private void initRv(RecyclerView rv) {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager);

        FiltrateTagBean serviceTag = new FiltrateTagBean();
        serviceTag.setTabDes("博绘服务");
        List<String> serviceTags = new ArrayList<>();
        serviceTags.add("热销");
        serviceTags.add("精品");
        serviceTags.add("强推");
        serviceTags.add("高性价比");
        serviceTag.setTags(serviceTags);
        FiltrateTagAdapter serviceTagAdapter = new FiltrateTagAdapter(mContext,serviceTag);
        delegateAdapter.addAdapter(serviceTagAdapter);

        FiltratePriceAdapter filtratePriceAdapter = new FiltratePriceAdapter(mContext,"价格区间");
        delegateAdapter.addAdapter(filtratePriceAdapter);

        String[] level1 = new String[]{"国画","油画","版画","书法","壁画"};
        for (String level:level1){
            FiltrateTagBean typeTag = new FiltrateTagBean();
            typeTag.setTabDes(level+"分类");
            List<String> levelTags = new ArrayList<>();
            for (int i=0;i<6;i++){
                levelTags.add(level+i);
            }
            typeTag.setTags(levelTags);
            FiltrateTagAdapter typeTagAdapter = new FiltrateTagAdapter(mContext,typeTag);
            delegateAdapter.addAdapter(typeTagAdapter);
        }
        rv.setLayoutManager(virtualLayoutManager);
        rv.setAdapter(delegateAdapter);
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getArtList(null);
    }


    @Override
    public void getArtListSuccess(ArtListResult result) {

    }
}
