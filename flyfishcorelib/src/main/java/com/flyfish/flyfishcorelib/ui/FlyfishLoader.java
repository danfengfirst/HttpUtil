package com.flyfish.flyfishcorelib.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.flyfish.flyfishcorelib.R;
import com.flyfish.flyfishcorelib.uitls.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Danfeng on 2018/1/1.
 */

public class FlyfishLoader {
    private static final  int LOADER_SIZE_SCALE=8;
    private static final  int LOADER_OFFSET_SCALE=10;
    private static final ArrayList<AppCompatDialog> LOADERS=new ArrayList<>();
    private static final String DEFAULTLOADER=LoaderStyle.BallPulseIndicator.name();

    public  static void showLoading(Context context,Enum<LoaderStyle> styleEnum){
        showLoading(context,styleEnum.name());
    }

    public static void showLoading(Context context,String type){
        final AppCompatDialog dialog=new AppCompatDialog(context,R.style.dialog);
        final AVLoadingIndicatorView avLoadingIndicatorView=LoaderCreator.create(type,context);
       dialog.setContentView(avLoadingIndicatorView);
        int devicewidth= DimenUtil.getScreenWidth();
        int deviceheight= DimenUtil.getScreenHeight();
        final Window dialogwindow=dialog.getWindow();
        if (dialogwindow!=null){
            WindowManager.LayoutParams lp=dialogwindow.getAttributes();
            lp.width=devicewidth/LOADER_SIZE_SCALE;
            lp.height=deviceheight/LOADER_SIZE_SCALE;
            lp.height=lp.height+deviceheight/LOADER_OFFSET_SCALE;
            lp.gravity= Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }
    public  static void showloading(Context context){
        showLoading(context,DEFAULTLOADER);
    }
    public static void stoploading(){
        for (AppCompatDialog dialog:LOADERS){
            if (dialog!=null){
                if (dialog.isShowing()){
                    dialog.cancel();
                }
            }
        }
    }
}
