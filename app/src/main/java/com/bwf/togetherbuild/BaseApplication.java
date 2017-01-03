package com.bwf.togetherbuild;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fanhy on 2016/11/14.
 * description：应用Application超类
 */

public class BaseApplication extends Application{
    private Context mContext;
    private volatile static BaseApplication mBaseApp;

    public static BaseApplication newInstance(){
        if(mBaseApp == null){
            synchronized (BaseApplication.class){
                if(mBaseApp == null){
                    mBaseApp = new BaseApplication();
                }
            }
        }
        return mBaseApp;
    }

    public Context getAppContext(){
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApp = this;
        mContext = getApplicationContext();
    }
}
