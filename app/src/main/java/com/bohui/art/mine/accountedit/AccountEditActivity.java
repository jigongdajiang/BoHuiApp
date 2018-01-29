package com.bohui.art.mine.accountedit;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bohui.art.R;
import com.bohui.art.bean.mine.AccountEditResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.accountedit.mvp.AccountEditContact;
import com.bohui.art.mine.accountedit.mvp.AccountEditModel;
import com.bohui.art.mine.accountedit.mvp.AccountEditPresenter;
import com.bohui.art.mine.accountedit.mvp.UserInfoEditParam;
import com.framework.core.util.StrOperationUtil;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class AccountEditActivity extends AbsNetBaseActivity<AccountEditPresenter,AccountEditModel> implements AccountEditContact.View {
    @BindView(R.id.iv_avr)
    ImageView iv_avr;
    @BindView(R.id.et_nick)
    EditText et_nick;
    @BindView(R.id.rg_sex)
    RadioGroup rg_sex;
    @BindView(R.id.et_intro)
    EditText et_intro;

    private int sex = 1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_edit;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("个人资料")
                .setRightText("保存")
                .setRightTextClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = et_nick.getText().toString();
                        if(StrOperationUtil.isEmpty(name)){
                            showMsgDialg("昵称不能为空");
                            return;
                        }
                        String intro = et_intro.getText().toString();
                        UserInfoEditParam param = new UserInfoEditParam();
                        param.setUid(1);
//                        param.setUid(AppFuntion.getUid());
                        param.setSex(sex);
                        param.setName(name);
                        mPresenter.accountEdit(param);
                    }
                })
                .builder();
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_nan){
                    sex = 1;
                }else{
                    sex = 0;
                }
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void accountEditSuccess(AccountEditResult result) {
        showMsgDialg(result.getMsg());
    }
}
