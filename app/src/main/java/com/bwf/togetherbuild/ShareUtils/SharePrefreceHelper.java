package com.bwf.togetherbuild.ShareUtils;

import android.content.Context;

/**
 * Created by Fanhy on 2016/11/13.
 * Description: SharedPrefrence工具类
 */
public class SharePrefreceHelper extends PrefrenceWrapper {

    private static SharePrefreceHelper sharePrefreceHelper;

    private SharePrefreceHelper(Context context) {
        super(context);
    }

    public static SharePrefreceHelper getInstence(Context context) {
        if (sharePrefreceHelper == null)
            sharePrefreceHelper = new SharePrefreceHelper(context);
        return sharePrefreceHelper;
    }

    public void setIsFirst(boolean isFirst){
        setBoolean("isFirst",isFirst);
    }
    public boolean isFirst(){
        return getBoolean("isFirst",true);
    }
}
