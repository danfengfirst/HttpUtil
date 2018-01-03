package com.flyfish.flyfishcorelib.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by Danfeng on 2017/11/16.
 */

public class Configurator {
    private static final HashMap<String, Object> APP_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();

    private Configurator() {
        APP_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    //单例模式（静态内部类holder+getInstance方法）
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    public final void config() {
        initIcons();//配置完毕之前进行初始化
        APP_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    //字体初始化
    public final Configurator initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
        }
        for (int i = 1; i < ICONS.size(); i++) {
            Iconify.with(ICONS.get(i));
        }
        return this;
    }

    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        Iconify.with(descriptor);
        return this;
    }
    public final Configurator widthInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR.name(),INTERCEPTORS);
        return this;
    }

    public final Configurator widthInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR.name(),INTERCEPTORS);
        return this;
    }
    final HashMap<String, Object> getAppConfigs() {
        return APP_CONFIGS;
    }


    public final Configurator withApiHost(String url) {
        APP_CONFIGS.put(ConfigType.API_HOST.name(), url);
        return this;
    }


    private void checkConfiguration() {
        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready!");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfig(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) APP_CONFIGS.get(key);
    }
}
