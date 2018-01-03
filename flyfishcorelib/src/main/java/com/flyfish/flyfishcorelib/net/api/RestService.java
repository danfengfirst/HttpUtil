package com.flyfish.flyfishcorelib.net.api;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Danfeng on 2017/12/13.
 */

public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody requestBody);
    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url,@FieldMap Map<String,Object> params);

    @PUT
    Call<String> putRaw(@Url String url,@Body RequestBody requestBody);
    @FormUrlEncoded

    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    @Streaming //用于下载大文件，通过ResponseBody进行输出
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);
    @POST
    Call<String> uploadPhoto(@Url String url, @Part MultipartBody.Part file, @PartMap Map<String,RequestBody> maps);
}
