package com.bohui.art.common.mvp;

import com.bohui.art.bean.common.ArtListParam;
import com.bohui.art.bean.common.ArtListResult;
import com.framework.core.http.EasyHttp;
import com.framework.core.util.CollectionUtil;
import com.framework.core.util.StrOperationUtil;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author : gaojigong
 * @date : 2018/1/21
 * @description:
 */


public class ArtListModel implements ArtListContact.Model {
    @Override
    public Observable<ArtListResult> getArtList(ArtListParam param) {
        String onclass = "";
        List<Long> oneClasses = param.getOneclass();
        if(!CollectionUtil.isEmpty(oneClasses)){
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<oneClasses.size();i++){
                if(i == oneClasses.size()-1){
                    stringBuffer.append(oneClasses.get(i)+"");
                }else {
                    stringBuffer.append(oneClasses.get(i)+",");
                }
            }
            onclass = stringBuffer.toString();
        }
        String twoClass = "";
        List<Long> towClasses = param.getTowclass();
        if(!CollectionUtil.isEmpty(towClasses)){
            StringBuffer stringBuffer = new StringBuffer();
            for(int i=0;i<towClasses.size();i++){
                if(i == towClasses.size()-1){
                    stringBuffer.append(towClasses.get(i)+"");
                }else {
                    stringBuffer.append(towClasses.get(i)+",");
                }
            }
            twoClass = stringBuffer.toString();
        }
        String name = param.getName();
        if(StrOperationUtil.isEmpty(name)){
            name = "";
        }
        return EasyHttp.post(ArtListContact.URL_ART_LIST)
                .params("start",String.valueOf(param.getStart()))
                .params("length",String.valueOf(param.getLength()))
                .params("name",name)
                .params("oneclass",onclass)
                .params("towclass",twoClass)
                .params("startprice",String.valueOf(param.getStartprice()))
                .params("endprice",String.valueOf(param.getEndprice()))
                .params("level",String.valueOf(param.getLevel()))
                .params("pricesort",String.valueOf(param.getPricesort()))
                .params("looksort",String.valueOf(param.getLooksort()))
                .execute(ArtListResult.class);
    }
}
