package com.bohui.art.search;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.bohui.art.R;
import com.bohui.art.common.widget.rv.ItemType;
import com.framework.core.util.KeyBordUtil;
import com.framework.core.util.StrOperationUtil;
import com.widget.grecycleview.adapter.base.BaseAdapter;
import com.widget.grecycleview.viewholder.BaseViewHolder;

/**
 * @author : gaojigong
 * @date : 2017/12/17
 * @description:
 */


public class FiltratePriceAdapter extends BaseAdapter<String> {
    private String des;

    public FiltratePriceAdapter(Context context, String des) {
        super(context);
        addItem(des);
    }

    @Override
    public int geViewType(int position) {
        return ItemType.SEARCH_FILTRATE_PRICE;
    }

    @Override
    public int geLayoutId(int position) {
        return R.layout.item_filtrate_price;
    }

    private EditText et_price1,et_price2;
    @Override
    public void bindViewHolder(BaseViewHolder holder, String itemData, int position) {
        holder.setText(R.id.tv_des,itemData);
        et_price1 = holder.getView(R.id.et_price1);
        et_price2 = holder.getView(R.id.et_price2);
//        et_price2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    KeyBordUtil.showSoftKeyboard(v);
//                }else{
//                    KeyBordUtil.hideSoftKeyboard(v);
//                }
//            }
//        });
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return singleLayoutHelper;
    }
    public  double getPrice1(){
        String p = et_price1.getText().toString();
        if(StrOperationUtil.isEmpty(p)){
            return 0;
        }
        try {
            return Double.parseDouble(p);
        }catch (Exception e){
            return 0;
        }
    }
    public  double getPrice2(){
        String p = et_price2.getText().toString();
        if(StrOperationUtil.isEmpty(p)){
            return 0;
        }
        try {
            return Double.parseDouble(p);
        }catch (Exception e){
            return 0;
        }
    }

    public void reset(){
        et_price1.setText("");
        et_price2.setText("");
    }
}
