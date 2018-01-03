package com.flyfish.flyfishcorelib.net.download;

import android.os.AsyncTask;

import com.flyfish.flyfishcorelib.net.RestCreator;
import com.flyfish.flyfishcorelib.net.callback.IError;
import com.flyfish.flyfishcorelib.net.callback.IFailure;
import com.flyfish.flyfishcorelib.net.callback.IRequest;
import com.flyfish.flyfishcorelib.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Danfeng on 2018/1/1.
 */

public class DownloadHandler {
    private final String mUrl;
    private static final WeakHashMap<String, Object> mParam = RestCreator.getParams();
    private final IRequest mRequest;
    private final ISuccess mSuccess;
    private final IError mError;
    private final IFailure mFailure;
    private final String mDownloadDir;
    private final String mExtension;
    private final String mName;

    public DownloadHandler(String url, IRequest request, ISuccess success, IError error, IFailure failure, String download_dir, String extension, String name) {
        mUrl = url;
        mRequest = request;
        mSuccess = success;
        mError = error;
        mFailure = failure;
        mDownloadDir = download_dir;
        mExtension = extension;
        mName = name;
    }

    public final void handlerDownload() {
        if (mRequest != null) {
            mRequest.onRequestStart();
        }
        RestCreator.getRestService().download(mUrl, mParam)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        final ResponseBody responseBody = response.body();
                        if (response.isSuccessful()) {
                            SaveFileTask saveFileTask = new SaveFileTask(mRequest, mSuccess, mError, mFailure);
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mDownloadDir, mExtension, responseBody, mName);
                            //这里需要判断asynctask是否结束，如果没有结束，文件只下载了一半
                            if (saveFileTask.isCancelled()) {
                                if (mRequest != null) {
                                    mRequest.onRequestEnd();
                                }
                            }
                        } else {
                            if (mError != null) {
                                mError.onError(response.code(), response.message());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (mFailure != null) {
                            mFailure.onFailure();
                        }
                    }
                });
    }
}
