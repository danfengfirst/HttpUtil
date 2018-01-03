package com.flyfish.flyfishcorelib.uitls;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.flyfish.flyfishcorelib.app.App;

/**
 * Created by Danfeng on 2018/1/1.
 */

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources= App.getApplicationContext().getResources();
        final DisplayMetrics dm=resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight(){
        final Resources resources=App.getApplicationContext().getResources();
        final DisplayMetrics dm=resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
