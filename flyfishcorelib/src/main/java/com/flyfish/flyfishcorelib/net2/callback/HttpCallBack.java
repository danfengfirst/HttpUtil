package com.flyfish.flyfishcorelib.net2.callback;

import com.flyfish.flyfishcorelib.net2.model.BaseCallModel;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Danfeng on 2018/1/3.
 */

public abstract class HttpCallBack<T extends BaseCallModel> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code()==200){
                if (response.body().code==0){

                }
        }else {

        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if(t instanceof SocketTimeoutException){
            //TODO
        }else if(t instanceof ConnectException){
            //TODO
        }else if(t instanceof RuntimeException){
            //TODO

        }
        onFailure(t.getMessage());
    }
    public abstract void onSuccess( Response<T> response);
    public abstract void onFailure(String message);
    public abstract void onError(int code);
}
