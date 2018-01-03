package com.flyfish.flyfishcorelib.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Danfeng on 2017/12/28.
 */

public abstract  class BaseApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext==null){
            mContext=this.getApplicationContext();
        }
        setup();
    }

    public abstract void setup() ;

    public static Context getAppContext(){
        return mContext;
    }
}
