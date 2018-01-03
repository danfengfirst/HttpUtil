package com.flyfish.flyfishcorelib.net;


import android.content.Context;

import com.flyfish.flyfishcorelib.net.api.RestService;
import com.flyfish.flyfishcorelib.net.callback.IError;
import com.flyfish.flyfishcorelib.net.callback.IFailure;
import com.flyfish.flyfishcorelib.net.callback.IRequest;
import com.flyfish.flyfishcorelib.net.callback.ISuccess;
import com.flyfish.flyfishcorelib.net.callback.RequestCallBacks;
import com.flyfish.flyfishcorelib.net.download.DownloadHandler;
import com.flyfish.flyfishcorelib.net.enums.HttpMethod;
import com.flyfish.flyfishcorelib.ui.FlyfishLoader;
import com.flyfish.flyfishcorelib.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Danfeng on 2017/12/14.
 */
//一次构建完毕决不允许修改
public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody REQUESTBODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    public RestClient(String url,
                      WeakHashMap<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IError error,
                      IFailure failure,
                      RequestBody requestbody,
                      LoaderStyle loaderStyle,
                      Context context, File file, String download_dir, String extension, String name) {
        this.URL = url;
        this.CONTEXT = context;
        this.FILE = file;
        this.DOWNLOAD_DIR = download_dir;
        this.EXTENSION = extension;
        this.NAME = name;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.REQUESTBODY = requestbody;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }


    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (REQUESTBODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                //采用postraw（原始数据的请求方式，params必须为空）
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (REQUESTBODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }
    public final void upload(){
        request(HttpMethod.UPLOAD);
    }
    public final void download(){
        new DownloadHandler(URL,REQUEST,SUCCESS,ERROR,FAILURE,DOWNLOAD_DIR,EXTENSION,NAME).handlerDownload();
    }
    private final void request(HttpMethod method) {
        RestService restService = RestCreator.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if (LOADER_STYLE != null) {
            FlyfishLoader.showLoading(CONTEXT, LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = restService.get(URL, PARAMS);
                break;
            case POST:
                call = restService.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = restService.postRaw(URL, REQUESTBODY);
                break;
            case PUT:
                call = restService.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = restService.putRaw(URL, REQUESTBODY);
                break;
            case DELETE:
                call = restService.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = restService.upload(URL, body);
                break;
        }
        if (call != null) {
            call.enqueue(getCallBack());
        }
    }

    private Callback<String> getCallBack() {
        return new RequestCallBacks(REQUEST, SUCCESS, ERROR, FAILURE, LOADER_STYLE);
    }
}
