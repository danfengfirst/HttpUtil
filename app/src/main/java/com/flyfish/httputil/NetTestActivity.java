package com.flyfish.httputil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyfish.flyfishcorelib.base.BaseActivity;
import com.flyfish.flyfishcorelib.net.RestClient;
import com.flyfish.flyfishcorelib.net.callback.IError;
import com.flyfish.flyfishcorelib.net.callback.IFailure;
import com.flyfish.flyfishcorelib.net.callback.ISuccess;

import butterknife.BindView;

/**
 * Created by Danfeng on 2018/1/1.
 */

public class NetTestActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView mTv;
    @Override
    public void setup(Bundle savedInstanceState) {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    public void get(View view){
        netget();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_nettest;
    }
    public void netget(){
        RestClient.builder()
                .url("http://127.0.0.1/test")
//                .params("","")
                .loader(this)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mTv.setText(response);
                        Log.d("debug",response);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String response) {

                    }
                })
                .build()
                .get();
    }
}
