package com.flyfish.flyfishcorelib.net.callback;

import android.os.Handler;

import com.flyfish.flyfishcorelib.ui.FlyfishLoader;
import com.flyfish.flyfishcorelib.ui.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Danfeng on 2017/12/15.
 */

public class RequestCallBacks implements Callback<String> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoaderStyle LOADER_STYLE;
    //handler 尽量声明成static类型，避免内存泄漏
    private static final Handler HANDLER=new Handler();
    public RequestCallBacks(IRequest request, ISuccess success, IError error, IFailure failure, LoaderStyle loader_style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        LOADER_STYLE = loader_style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if (ERROR!=null){
                ERROR.onError(response.code(),response.message());
            }
        }
        stoploading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
       stoploading();
    }
    private void stoploading(){
        if (LOADER_STYLE!=null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FlyfishLoader.stoploading();
                }
            },1000);
        }
    }
}
