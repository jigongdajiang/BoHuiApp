package com.bohui.art.mine.setting.suggest;

import android.view.View;
import android.widget.EditText;

import com.bohui.art.R;
import com.bohui.art.bean.mine.SuggestSubmitResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.setting.suggest.mvp.SuggestContanct;
import com.bohui.art.mine.setting.suggest.mvp.SuggestModel;
import com.bohui.art.mine.setting.suggest.mvp.SuggestPresenter;
import com.framework.core.util.StrOperationUtil;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class SuggestActivity extends AbsNetBaseActivity<SuggestPresenter,SuggestModel> implements SuggestContanct.View {
    @BindView(R.id.et_suggest)
    EditText et_suggest;
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
                        String content = et_suggest.getText().toString();
                        if(StrOperationUtil.isEmpty(content)){
                            showMsgDialg("内容不能为空");
                            return;
                        }
                        mPresenter.suggestSubmit(AppFuntion.getUid(),content);
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
