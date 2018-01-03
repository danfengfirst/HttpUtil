package com.flyfish.flyfishcorelib.net;


import com.flyfish.flyfishcorelib.app.App;
import com.flyfish.flyfishcorelib.app.ConfigType;
import com.flyfish.flyfishcorelib.net.api.RestService;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Danfeng on 2017/12/13.
 */

public class RestCreator {
    private static final  class ParamsHolder{
     static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    }
    public static final WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }
    public static final RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }
    public static final class RetrofitHolder {
        private static final String BASE_URL = (String) App.getAppConfigs().get(ConfigType.API_HOST.name());
        private static final Retrofit REROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKhttpHolder.OKHTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

    }

    public static final class OKhttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUIDLER=new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS= (ArrayList<Interceptor>) App.getAppConfigs().get(ConfigType.INTERCEPTOR.name());
        private static OkHttpClient.Builder addInterceptors(){
            if (INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor:INTERCEPTORS){
                    BUIDLER.addInterceptor(interceptor);
                }
            }
            return BUIDLER;
        }

        private static final OkHttpClient OKHTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    public static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.REROFIT_CLIENT.create(RestService.class);
    }
}
