package com.bwf.togetherbuild.ShareUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fanhy on 2016/11/13.
 * Description: SharedPrefrence工具类
 */
public class PreferenceWrapper {

    private SharedPreferences sharedPreferences;

    private static final String SP_NAME = "share_";

    protected PreferenceWrapper(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);//Context.MODE_MULTI_PROCESS支持跨进程访问
    }

    protected void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    protected String getString(String key, String defaultVaule) {
        return sharedPreferences.getString(key, defaultVaule);
    }

    protected String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    protected void setBoolean(String key, boolean vaule){
        sharedPreferences.edit().putBoolean(key,vaule).commit();
    }

    protected boolean getBoolean(String key, boolean defaultVaule){
        return sharedPreferences.getBoolean(key,defaultVaule);
    }

    /**
     * 清空sharePrefrence文件
     */
    public void clear() {
        sharedPreferences.edit().clear();
    }


}
