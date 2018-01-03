package com.flyfish.flyfishcorelib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.flyfish.flyfishcorelib.R;
import com.flyfish.flyfishcorelib.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Danfeng on 2017/12/28.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder=null;
    public Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout container=new FrameLayout(App.getApplicationContext());
        container.setId( R.id.container);
        View rootview=getLayoutInflater().inflate(getLayoutId(),container,false);
        if (rootview!=null){
            container.addView(rootview);
        }
        setContentView(container);
        mUnbinder= ButterKnife.bind(this);
        mContext=this;
        setup(savedInstanceState);
    }

    public abstract void setup(Bundle savedInstanceState) ;

    public abstract int getLayoutId() ;

    @Override
    protected void onDestroy() {
        if (mUnbinder!=null){
            mUnbinder.unbind();
        }
        System.runFinalization();
        System.gc();
        super.onDestroy();
    }
}
