package com.bohui.art.found.order;

import android.widget.EditText;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.bean.found.OrderBean;
import com.bohui.art.bean.found.OrderResult;
import com.bohui.art.common.activity.AbsNetBaseActivity;
import com.bohui.art.common.app.AppFuntion;
import com.bohui.art.common.util.CallUitl;
import com.bohui.art.common.util.RxViewUtil;
import com.bohui.art.common.widget.title.DefaultTitleBar;
import com.framework.core.util.StrOperationUtil;
import com.framework.core.util.StrVerifyUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author : gaojigong
 * @date : 2017/12/16
 * @description: 定制页
 */


public class OrderActivity extends AbsNetBaseActivity<OrderPresenter,OrderModel> implements OrderContact.View{
    @BindView(R.id.et_type)
    EditText et_type;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_price1)
    EditText et_price1;
    @BindView(R.id.et_price2)
    EditText et_price2;
    @BindView(R.id.et_specification)
    EditText et_specification;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_btn_order)
    TextView tv_btn_order;
    @BindView(R.id.tv_btn_call)
    TextView tv_btn_call;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        new DefaultTitleBar.DefaultBuilder(mContext)
                .setTitle("我要定制")
                .builder();
        RxViewUtil.addOnClick(mRxManager, tv_btn_order, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                if(controlEdit(et_type,"类型不能为空")){
                    return;
                }
                if(controlEdit(et_name,"名称不能为空")){
                    return;
                }
                if(controlEdit(et_price1,"价格下限不能为空")){
                    return;
                }
                if(controlEdit(et_price2,"价格上限不能为空")){
                    return;
                }
                if(controlEdit(et_specification,"规格不能为空")){
                    return;
                }
                if(controlEdit(et_number,"数量不能为空")){
                    return;
                }
                if(controlEdit(et_phone,"联系方式不能为空")){
                    return;
                }

                String type  = et_type.getText().toString().trim();
                String name  = et_name.getText().toString().trim();
                String price1  = et_price1.getText().toString().trim();
                String price2  = et_price2.getText().toString().trim();
                String specification  = et_specification.getText().toString().trim();
                String num  = et_number.getText().toString().trim();
                String phone  = et_phone.getText().toString().trim();
                if(!StrVerifyUtil.isMobileNO(phone)){
                    showMsgDialg("请输入正确的手机号");
                    return;
                }
                String remarks  = et_remark.getText().toString().trim();

                OrderBean param = new OrderBean();
                param.setType(type);
                param.setName(name);
                param.setPrice(price1+"-"+price2);
                param.setSize(specification);
                param.setNum(num);
                param.setMobile(phone);
                param.setRemarks(remarks);
                param.setUid(AppFuntion.getUid());
                mPresenter.order(param);
            }
        });
        RxViewUtil.addOnClick(mRxManager, tv_btn_call, new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                CallUitl.call(OrderActivity.this,"400-8000000");
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter.setMV(mModel,this);
    }

    @Override
    public void orderSuccess(OrderResult result) {
        showMsgDialg(result.getMsg());
    }

    @Override
    public void orderDetailSuccess(OrderBean result) {
        et_type.setText(result.getType());
        et_name.setText(result.getName());
        et_price1.setText(result.getPrice());
        et_price2.setText(result.getPrice());
        et_specification.setText(result.getSize());
        et_number.setText(result.getNum());
        et_phone.setText(result.getMobile());
        et_remark.setText(result.getRemarks());
    }

    private boolean controlEdit(EditText editText,String msg){
        String content  = editText.getText().toString().trim();
        if(StrOperationUtil.isEmpty(content)){
            showMsgDialg(msg);
            return true;
        }
        return false;
    }
}
