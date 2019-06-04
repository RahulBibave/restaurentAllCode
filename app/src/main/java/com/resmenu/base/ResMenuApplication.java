package com.resmenu.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

public class ResMenuApplication extends MultiDexApplication {
    private static Context mContext;
    public static final String LOG_TAG = "AppAuthSample";

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public ResMenuApplication() {

    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
