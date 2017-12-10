package com.bohui.art.home;

import android.os.Bundle;
import android.widget.TextView;

import com.bohui.art.R;
import com.bohui.art.common.fragment.AbsNetBaseFragment;
import com.bohui.art.home.bean.TypeBean;

import butterknife.BindView;

/**
 * @author : gaojigong
 * @date : 2017/12/10
 * @description:
 */


public class TypeFragment extends AbsNetBaseFragment {
    @BindView(R.id.tv_type)
    TextView tv_type;
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
        tv_type.setText(mType.getType());
    }
}
