package com.bwf.togetherbuild.utils.commom;

import android.widget.Toast;

import com.finesdk.BaseApplication;

/**
 * Created by Fanhy on 2016/11/16.
 * description:Toast工具类
 */
public class ToastUtil {

    /**
     * 短时间显示Toast
     *
     * @param info 显示的内容
     */
    public static void showToast(String info) {
        Toast.makeText(BaseApplication.newInstance().getAppContext(), info, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param info 显示的内容
     */
    public static void showToastLong(String info) {
        Toast.makeText(BaseApplication.newInstance().getAppContext(), info, Toast.LENGTH_LONG).show();
    }

    /**
     * 短时间显示Toast
     */
    public static void showToast(int resId) {
        Toast.makeText(BaseApplication.newInstance().getAppContext(), BaseApplication.newInstance().getAppContext().getString(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     */
    public static void showToastLong(int resId) {
        Toast.makeText(BaseApplication.newInstance().getAppContext(), resId, Toast.LENGTH_LONG).show();
    }

}
