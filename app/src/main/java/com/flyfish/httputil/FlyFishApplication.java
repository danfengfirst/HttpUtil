package com.flyfish.httputil;

import com.flyfish.flyfishcorelib.app.App;
import com.flyfish.flyfishcorelib.base.BaseApplication;
import com.flyfish.flyfishcorelib.net.interceptors.DebugInterceptor;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by Danfeng on 2017/12/28.
 */

public class FlyFishApplication extends BaseApplication {

    @Override
    public void setup() {
        App.init(mContext)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .initIcons()
                .widthInterceptor(new DebugInterceptor("test", R.raw.test))
                .config();
    }
}
