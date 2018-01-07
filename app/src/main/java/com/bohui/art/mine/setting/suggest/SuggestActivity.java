package com.bohui.art.mine.setting.suggest;

import android.view.View;

import com.bohui.art.R;
import com.bohui.art.bean.mine.SuggestSubmitResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.setting.suggest.mvp.SuggestContanct;
import com.bohui.art.mine.setting.suggest.mvp.SuggestModel;
import com.bohui.art.mine.setting.suggest.mvp.SuggestPresenter;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class SuggestActivity extends AbsNetBaseActivity<SuggestPresenter,SuggestModel> implements SuggestContanct.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_suggest;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("咨询建议")
                .setRightText("提交")
                .setRightTextClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.suggestSubmit();
                    }
                })
                .builder();
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void suggestSubmitSuccess(SuggestSubmitResult result) {

    }
}
