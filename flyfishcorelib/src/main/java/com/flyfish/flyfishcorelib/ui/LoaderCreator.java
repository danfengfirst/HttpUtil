package com.flyfish.flyfishcorelib.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by Danfeng on 2018/1/1.
 */

public final class LoaderCreator {
    public static final WeakHashMap<String,Indicator> LOADING_MAP=new WeakHashMap<>();
    static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView=new AVLoadingIndicatorView(context);
        if (LOADING_MAP.get(type)==null){
            //没有缓存时创建Indicator
            final Indicator indicator=getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }
        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }
    //通过反射获取Indicator
    private static Indicator getIndicator(String name){
        if (name==null||name.isEmpty())return null;
        final StringBuilder drawableClassName=new StringBuilder();
        if (!name.contains(".")){
            //如果不包含"."证明传入的是类名
            final String defaultPackageName=AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");

        }
        drawableClassName.append(name);
        try {
            final Class<?> drawableClass=Class.forName(drawableClassName.toString());
            return (Indicator) drawableClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
