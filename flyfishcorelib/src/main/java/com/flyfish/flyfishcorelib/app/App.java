package com.flyfish.flyfishcorelib.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Danfeng on 2017/11/16.
 */
//final不需要继承类，用于app的相关初始化操作，AppConfig主要是对外的工具类，因此都是静态方法
public final class App {

    //配置ApplicationContext
    public static Configurator init(Context context){
        getAppConfigs().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
        return Configurator.getInstance();
    }
    public static HashMap<String,Object> getAppConfigs(){
       return Configurator.getInstance().getAppConfigs();
    }
    public static Context getApplicationContext(){
      return (Context) getAppConfigs().get(ConfigType.APPLICATION_CONTEXT.name());
    }

}
