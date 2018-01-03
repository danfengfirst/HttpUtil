package com.flyfish.flyfishcorelib.net;

import android.content.Context;

import com.flyfish.flyfishcorelib.net.callback.IError;
import com.flyfish.flyfishcorelib.net.callback.IFailure;
import com.flyfish.flyfishcorelib.net.callback.IRequest;
import com.flyfish.flyfishcorelib.net.callback.ISuccess;
import com.flyfish.flyfishcorelib.ui.LoaderStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Danfeng on 2017/12/14.
 */

public class RestClientBuilder {
    private String mUrl;
    private  static final WeakHashMap<String,Object> mParams=RestCreator.getParams();
    private  IRequest mRequest;
    private  ISuccess mSuccess;
    private  IError mError;
    private  IFailure mFailure;
    private  RequestBody mRequestBody;
    private Context mContext;
    private LoaderStyle mLoaderStyle;
    private File mFile;
    private String mDownloadDir;
    private String mExtension;
    private String mName;


    public RestClientBuilder() {
    }
    public final RestClientBuilder url(String url){
       this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        this.mParams.putAll(params);
        return this;
    }
    public final RestClientBuilder params(String key, Object value){
        this.mParams.put(key,value);
        return this;
    }
    public final RestClientBuilder raw(String raw){
        this.mRequestBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }
    public final RestClientBuilder file(File file){
        mFile=file;
        return  this;
    }
    public final RestClientBuilder file(String path){
        mFile=new File(path);
     return this;
    }
    public final RestClientBuilder dir(String dir){
        this.mDownloadDir=dir;
        return this;
    }
    public final RestClientBuilder extension(String extension){
        this.mExtension=extension;
        return this;
    }
    public final RestClientBuilder name(String name){
        this.mName=name;
        return this;
    }
    public final RestClientBuilder request(IRequest iRequest){
        this.mRequest=iRequest;
        return this;
    }
    public final RestClientBuilder success(ISuccess iSuccess){
        this.mSuccess=iSuccess;
        return this;
    }
    public final RestClientBuilder failure(IFailure iFailure){
        this.mFailure=iFailure;
        return this;
    }
    public final RestClientBuilder error(IError iError){
        this.mError=iError;
        return this;
    }
    public final RestClientBuilder loader(Context context,LoaderStyle loaderStyle){
        this.mContext=context;
        this.mLoaderStyle=loaderStyle;
        return this;
    }
    public final RestClientBuilder loader(Context context){
        this.mContext=context;
        this.mLoaderStyle=LoaderStyle.BallPulseIndicator;
        return this;
    }
    public final RestClient build(){
        return new RestClient(mUrl,mParams,mRequest,mSuccess,mError,mFailure,mRequestBody, mLoaderStyle,mContext, mFile, mDownloadDir, mExtension, mName);
    }
}
