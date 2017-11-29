package com.jimeijf.core.toast;

import android.content.Context;

/**
 * @author : gongdaocai
 * @date : 2017/11/14
 * FileName:
 * @description:
 */


public class EToastStrategy implements IToastStrategy {
    private EToast toast;
    @Override
    public void show(Context context, String str, int duration) {
        if(toast == null){
            toast = EToast.makeText(context,str,duration);
        }else{
            toast.setText(str);
        }
        toast.show();
    }

    @Override
    public void cancel() {
        if(toast != null){
            toast.destory();
            toast = null;
        }
    }
}
