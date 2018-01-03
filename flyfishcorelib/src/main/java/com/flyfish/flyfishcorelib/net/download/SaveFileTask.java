package com.flyfish.flyfishcorelib.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.flyfish.flyfishcorelib.app.App;
import com.flyfish.flyfishcorelib.net.callback.IError;
import com.flyfish.flyfishcorelib.net.callback.IFailure;
import com.flyfish.flyfishcorelib.net.callback.IRequest;
import com.flyfish.flyfishcorelib.net.callback.ISuccess;
import com.flyfish.flyfishcorelib.uitls.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by Danfeng on 2018/1/1.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;

    public SaveFileTask(IRequest request, ISuccess success, IError error, IFailure failure) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
    }


    @Override
    protected File doInBackground(Object... objects) {
        String downloadir= (String) objects[0];
        String extension= (String) objects[1];
        final ResponseBody body= (ResponseBody) objects[2];
        final InputStream is=body.byteStream();
        final String name= (String) objects[3];
        if (downloadir==null||downloadir.equals("")){
            downloadir="flyfish_download";
        }
        if (extension==null|| extension.equals("")){
            extension="";//作为扩展，暂时没有相关操作不赋值
        }
        if (name==null){
            return FileUtil.writeToDisk(is,downloadir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS!=null){
            SUCCESS.onSuccess(file.getAbsolutePath());
        }
        //TODO 还需要处理Error、Failure操作
        if (REQUEST!=null){
            REQUEST.onRequestEnd();
        }
//        autoInstallApk(file);
    }
    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            //获取文件后缀名，判断是否为apk
            final Intent intent=new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            App.getApplicationContext().startActivity(intent);
        }

    }
}
