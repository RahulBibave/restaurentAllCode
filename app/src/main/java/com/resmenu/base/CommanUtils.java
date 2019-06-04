package com.resmenu.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.resmenu.base.ResMenuApplication;

public class CommanUtils  {

    public static Context mContext;
    private AppCompatActivity mCurrentActivityWindow;


    public CommanUtils(AppCompatActivity currentActivityWindow) {
        this.mCurrentActivityWindow = currentActivityWindow;
    }
    public CommanUtils(Context context){
        this.mContext = context;
    }

    //Network Check
    public static boolean isNetworkAvailable() {
        Context mContext = ResMenuApplication.getContext();
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
