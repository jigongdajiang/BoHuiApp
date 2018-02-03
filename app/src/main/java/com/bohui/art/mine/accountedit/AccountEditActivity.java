package com.bohui.art.mine.accountedit;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bohui.art.R;
import com.bohui.art.bean.mine.AccountEditResult;
import com.bohui.art.bean.mine.MineInfoResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.bohui.art.mine.MineFragment;
import com.bohui.art.mine.accountedit.mvp.AccountEditContact;
import com.bohui.art.mine.accountedit.mvp.AccountEditModel;
import com.bohui.art.mine.accountedit.mvp.AccountEditPresenter;
import com.bohui.art.mine.accountedit.mvp.UserInfoEditParam;
import com.bohui.art.mine.upload.UpLoadUtil;
import com.framework.core.glideext.GlideUtil;
import com.framework.core.util.StrOperationUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description:
 */


public class AccountEditActivity extends AbsNetBaseActivity<AccountEditPresenter,AccountEditModel> implements AccountEditContact.View {
    @BindView(R.id.iv_avr)
    ImageView iv_avr;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_nick)
    EditText et_nick;
    @BindView(R.id.rg_sex)
    RadioGroup rg_sex;
    @BindView(R.id.et_intro)
    EditText et_intro;

    public static final String ACCOUNT_INFO = "account_info";
    private int sex = 1;
    private UpLoadUtil upLoadUtil;//头像上传工具类

    private MineInfoResult info;
    @Override
    protected void doBeforeSetContentView() {
        super.doBeforeSetContentView();
        info = (MineInfoResult) getIntent().getSerializableExtra(ACCOUNT_INFO);
    }

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
                        param.setUid(AppFuntion.getUid());
                        param.setSex(sex);
                        param.setName(name);
                        param.setIndustry(intro);
                        mPresenter.accountEdit(param);
                    }
                })
                .builder();
        if(info != null){
            if(!StrOperationUtil.isEmpty(info.getPhoto())){
                GlideUtil.displayCircle(this,iv_avr,info.getPhoto());
            }
            et_account.setText(info.getMobile());
            et_nick.setText(info.getName());
            if(info.getSex() == 1){
                rg_sex.check(R.id.rb_nan);
            }else{
                rg_sex.check(R.id.rb_nv);
            }
            et_intro.setText(info.getIndustry());
        }
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

        upLoadUtil = new UpLoadUtil(AccountEditActivity.this, iv_avr);
        RxViewUtil.addOnClick(mRxManager, iv_avr, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                //上传头像
                upLoadUtil.changeAvr();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        upLoadUtil.onActivityResult(requestCode,resultCode,data);
    }
}
