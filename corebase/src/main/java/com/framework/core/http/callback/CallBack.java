/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.framework.core.http.callback;


import com.framework.core.http.exception.ApiException;
import com.framework.core.util.TUtil;

import java.lang.reflect.Type;

/**
 * <p>描述：网络请求回调</p>
 * 能对泛型进行转化
 * 作者： zhouyou<br>
 * 日期： 2016/12/28 16:54<br>
 * 版本： v2.0<br>
 */
public abstract class CallBack<T> implements IType<T> {
    /**
     * 开始
     */
    public abstract void onStart();

    /**
     * 完成
     */
    public abstract void onCompleted();

    /**
     * 错误
     */
    public abstract void onError(ApiException e);

    /**
     * 成功
     * @param t
     */
    public abstract void onSuccess(T t);

    @Override
    public Type getType() {//获取需要解析的泛型T类型
        return TUtil.findNeedClass(getClass());
    }

    public Type getRawType() {//获取需要解析的泛型T raw类型
        return TUtil.findRawType(getClass());
    }
}
