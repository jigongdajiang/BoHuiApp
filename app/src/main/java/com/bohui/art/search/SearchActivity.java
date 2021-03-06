package com.bohui.art.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.search.SearchTagResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.search.mvp.SearchContact;
import com.bohui.art.search.mvp.SearchModel;
import com.bohui.art.search.mvp.SearchPresenter;
import com.framework.core.util.StrOperationUtil;
import com.widget.smallelement.flowtag.FlowLayout;
import com.widget.smallelement.flowtag.TagAdapter;
import com.widget.smallelement.flowtag.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 * 搜索页
 * 搜索情况分为 搜索艺术品  搜索艺术家  搜索设计师
 * 对应改变et的hint
 * 不同的类型对应不同的热门标签列表
 */


public class SearchActivity extends AbsNetBaseActivity<SearchPresenter,SearchModel> implements SearchContact.ISearchView {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tag_search)
    TagFlowLayout tag_search;

    public static final String SEARCH_TYPE = "search_type";
    public static final String SEARCH_KEY = "search_key";
    private int mType;//0 艺术品  1 艺术家  2设计师，不同的类型返回的搜索标签列表内容不一样，但是格式一样

    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        mType = getIntent().getIntExtra(SEARCH_TYPE,0);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        RxViewUtil.addOnClick(mRxManager, iv_back, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                onBackPressed();
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_search, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                String content = et_search.getText().toString();
                if(StrOperationUtil.isEmpty(content)){
                    showMsgDialg("输入内容不能为空");
                    return;
                }
                goSearchResult(content);
            }
        });

    }

    private void goSearchResult(String content) {
        //进入搜索结果页
        Bundle bundle = new Bundle();
        bundle.putString(SEARCH_KEY,content);
        switch (mType){
            case 0:
                startAty(SearchResultArtActivity.class,bundle);
                break;
            case 1:
                startAty(SearchResultArtManActivity.class,bundle);
                break;
            case 2:
                startAty(SearchResultDesignerActivity.class,bundle);
                break;
        }
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    protected void extraInit() {
        mPresenter.getSearchTag();
    }

    @Override
    public void getSearchTagSuccess(final SearchTagResult result) {
        //更新Tag
        tag_search.setAdapter(new TagAdapter<String>(result.getTags())
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_search_tag,
                        tag_search, false);
                tv.setText(s);
                return tv;
            }
        });
        tag_search.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String tag = result.getTags().get(position);
                goSearchResult(tag);
                return false;
            }
        });
    }
}
