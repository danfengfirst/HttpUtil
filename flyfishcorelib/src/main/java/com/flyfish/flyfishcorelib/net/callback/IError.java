package com.flyfish.flyfishcorelib.net.callback;

/**
 * Created by Danfeng on 2017/12/13.
 */

public interface IError {
    void onError(int code, String response);
}
