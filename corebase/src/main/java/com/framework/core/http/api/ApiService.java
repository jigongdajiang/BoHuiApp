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

package com.framework.core.http.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * <p>描述：通用的的api接口</p>
 * <p>
 * 1.加入基础API，减少Api冗余<br>
 * 2.支持多种方式访问网络（get,put,post,delete），包含了常用的情况<br>
 * 3.传统的Retrofit用法，服务器每增加一个接口，就要对应一个api，非常繁琐<br>
 * 4.如果返回ResponseBody在返回的结果中去获取T,又会报错，这是因为在运行过程中,通过泛型传入的类型T丢失了,所以无法转换,这叫做泛型擦除。
 * 《泛型擦除》不知道的童鞋自己百度哦！！<br>
 * </p>
 * <p>
 * 注意事项：<br>
 * 1.使用@url,而不是@Path注解,后者放到方法体上,会强制先urlencode,然后与baseurl拼接,请求无法成功<br>
 * 2.注意事项： map不能为null,否则该请求不会执行,但可以size为空.<br>
 * </p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/19 16:35<br>
 * 版本： v2.0<br>
 */
public interface ApiService {
    /**
     * post 多参数表单请求
     * @param url 请求地址
     *          1.使用@url,而不是@Path注解,因为后者放到方法体上,会强制先urlencode,然后与baseurl拼接,不灵活
     *          2.使用@url后将不会主动拼接baseUrl，这就可以将url的拼接规范独立出来自己实现
     * @param maps 参数 不能为null，但是可以size()=0
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> maps);

    /**
     * post 对象请求
     * @param url
     * @param object
     *          将对象作为参数进行请求
     *          * 1. @Body标签不能同时和@FormUrlEncoded、@Multipart标签同时使用。
     *          * 2. @Body最终会依赖指定的转换器进行转义，如果指定的Convert是GsonConvert则会转为json传给服务器
     *          * 3. 如果自定义了转换器，这里就要注意处理，防止Unable to create @Body converter for %s错误
     * @return
     */
    @POST
    Observable<ResponseBody> postBody(@Url String url, @Body Object object);

    /**
     * post RequestBody请求
     * @param url
     * @param requestBody
     *          这种方式可以以自定义RequestBody作为请求参数
     *          例如:RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postInfoStr);
     * @return
     */
    @POST
    Observable<ResponseBody> postBody(@Url String url, @Body RequestBody requestBody);

    /**
     * 以Json作为参数和响应的请求 自动添加了请求中的header  表示请求的参数为json串  接受json串返回值
     * @param url
     * @param requestBody
     * @return
     */
    @POST
    @Headers({"Content-Type: application/json","Accept: application/json"})
    Observable<ResponseBody> postJson(@Url String url, @Body RequestBody requestBody);

    /**
     * 通用get请求
     * @param url
     * @param maps
     *  请求参数
     * @return
     */
    @GET
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * 单文件上传
     * @param fileUrl
     * @param description 文件描述
     *  例如RequestBody description =
     *                    RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);
     * @param file 上传的文件部分
     *  例如：
     *      //创建上传的文件
     *      RequestBody requestFile =
     *             RequestBody.create(MediaType.parse("multipart/form-data"), file);
     *      //设置和后端约定好的key部分，就是制定后端在收到时的存储文件的key-value值
     *      MultipartBody.Part body =
     *             MultipartBody.Part.createFormData("image", file.getName(), requestFile);
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFlie(@Url String fileUrl,
                                        @Part("description") RequestBody description,
                                        @Part("files") MultipartBody.Part file);

    /**
     * 多文件上传
     * @param url
     * @param maps
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @PartMap() Map<String, RequestBody> maps);

    /**
     * 多文件上传
     * @param url
     * @param parts
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url String url, @Part() List<MultipartBody.Part> parts);

    /**
     * 文件加载
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    /**
     * put通用多参数请求
     * @param url
     * @param maps
     * @return
     */
    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> maps);

    /**
     * put Body类型参数请求
     * @param url
     * @param object
     * @return
     */
    @PUT
    Observable<ResponseBody> putBody(@Url String url, @Body Object object);

    /**
     * delete类型请求
     * @param url
     * @param maps
     * @return
     */
    @DELETE()
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> maps);
}
