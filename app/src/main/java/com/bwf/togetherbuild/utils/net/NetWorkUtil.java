package com.bwf.togetherbuild.utils.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.bwf.togetherbuild.utils.commom.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Fanhy on 2016/11/16.
 * 类名称 ：NetUtil 类描述 ：网络相关的工具类
 */
@SuppressLint("DefaultLocale")
public class NetWorkUtil {
    private static final String TAG = "MobileUtils";

    /*
     * 判断网络连接是否已开 2012-08-20true 已打开 false 未打开
     */
    public static boolean isNetDeviceAvailable(Context context) {
        boolean bisConnFlag = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

    public static enum ProviderName {
        chinaMobile("中国移动"), chinaUnicom("中国联通"), chinaTelecom("中国电信"), chinaNetcom("中国网通"), other("未知");
        private String text;

        private ProviderName(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }

    /**
     * 获取SIM卡的IMSI码 SIM卡唯一标识：IMSI 国际移动用户识别码 （IMSI：International Mobile
     * Subscriber Identification Number）是区别移动用户的标志， 储存在SIM卡中，可用于区别移动用户的有效信息。
     * IMSI由MCC、MNC、MSIN组成，其中MCC为移动国家号码，由3位数字组成，
     * 唯一地识别移动客户所属的国家，我国为460；MNC为网络id，由2位数字组成， 用于识别移动客户所归属的移动网络，中国移动为00，中国联通为01,
     * 中国电信为03；MSIN为移动客户识别码，采用等长11位数字构成。 唯一地识别国内GSM移动通信网中移动客户。
     * 所以要区分是移动还是联通，只需取得SIM卡中的MNC字段即可
     */
    public static ProviderName getProviderName(Context context) {
        String imsi = getIMSI(context);
        if (imsi != null) {
            // 因为移动网络编号46000下的IMSI已经用完,所以虚拟了一个46002编号，134/159号段使用了此编号
            LogUtil.i("imsi", imsi);
            if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
                return ProviderName.chinaMobile;
            } else if (imsi.startsWith("46001")) {
                return ProviderName.chinaUnicom;
            } else if (imsi.startsWith("46003")) {
                return ProviderName.chinaTelecom;
            } else {
                return ProviderName.other;
            }
        } else {
            return ProviderName.other;
        }
    }

    /**
     * IMEI 全称为 International Mobile Equipment Identity，中文翻译为国际移动装备辨识码，
     * 即通常所说的手机序列号，
     * 用于在手机网络中识别每一部独立的手机，是国际上公认的手机标志序号，相当于移动电话的身份证。序列号共有15位数字，前6位（TAC）是型号核准号码，
     * 代表手机类型。接着2位（FAC）是最后装配号，代表产地。后6位（SNR）是串号，代表生产顺序号。最后1位（SP）一般为0，是检验码，备用。
     * 国际移动装备辨识码一般贴于机身背面与外包装上，同时也存在于手机记忆体中，通过输入*#06#即可查询。
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getDeviceId();
    }

    /**
     * IMSI 全称为 International Mobile Subscriber
     * Identity，中文翻译为国际移动用户识别码。它是在公众陆地移动电话网
     * （PLMN）中用于唯一识别移动用户的一个号码。在GSM网络，这个号码通常被存放在SIM卡中
     *
     * @param context
     * @return
     */
    public static String getIMSI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getSubscriberId();
    }

    /**
     * 获取路由器Mac
     */
    public static String getRouterMac(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifi.getConnectionInfo().getBSSID();
    }
    /**
     * 判断网络是否可用
     *
     * @return true:有网络
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return boolean :true:空（null,"null","NULL",""） false:不为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.equals("") || str.equalsIgnoreCase("null")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        int res;
        if (dpValue < 0) {
            res = (int) (dpValue * scale - 0.5f);
            return res;
        }
        res = (int) (dpValue * scale + 0.5f);
        return res;
    }

    /**
     * 切换密码显示状态
     *
     * @param isChecked
     */
    public static void togglePwdShow(boolean isChecked, EditText isShow) {
        if (isChecked) {
            isShow.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            isShow.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        isShow.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = isShow.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }


    /**
     * 批量设置点击监听
     *
     * @param listener
     * @param views
     */
    public static void setListener(View.OnClickListener listener, View... views) {
        for (View v : views) {
            v.setOnClickListener(listener);
        }
    }

    public static void call(String tel, Context context) {
        if (!NetWorkUtil.isEmpty(tel)) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + tel));
            context.startActivity(intent);
        }
    }

    /**
     * 描述 将long时间装换为格式字符串
     * 参数
     * 返回值
     */
    public static String longToDateStr(Long longDate, String formmat) {
        if (longDate == null) {
            return "";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formmat);
        Date date = new Date(longDate);
        return formatter.format(date);
    }

    /**
     * 描述 将long时间装换为格式字符串
     * 参数
     * 返回值
     */
    public static String longToDateStr(long longDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(longDate);
        return formatter.format(date);
    }

    /**
     * 描述 判断字符串的数是否大于0
     * 参数
     * 返回值 true 大于0,false 小于等于0
     */
    public static boolean isMoreThanZore(String str) {
        if (isEmpty(str)) {
            return false;
        }
        double f = Double.parseDouble(str);
        return f > 0;
    }

    /**
     * 描述 是否是电话号
     * 参数
     * 返回值 true:是正确的电话格式 false:不是正确的电话格式
     */
    public static boolean isMobileNO(String mobiles) {
        String telphone = "^[0-9]{11}$";
        if (TextUtils.isEmpty(mobiles))
            return false;
        boolean isTel = mobiles.matches(telphone);
        return isTel;
    }

    /**
     * 描述 将String转为int
     * 返回值
     */
    public static double paserDouble(String str) {
        return paserDouble(str, 0);
    }

    /**
     * 描述 将String转为int
     * 返回值
     */
    public static double paserDouble(String str, int defaultNum) {
        if (!isEmpty(str)) {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException e) {
                return defaultNum;
            }
        }
        return defaultNum;
    }

    /**
     * 描述 将String转为int
     * 返回值
     */
    public static int paserInt(String str) {
        return paserInt(str, 0);
    }

    /**
     * 描述 将String转为int
     * 参数
     * 返回值
     */
    public static int paserInt(String str, int defaultNum) {
        if (!isEmpty(str)) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return defaultNum;
            }
        }
        return defaultNum;
    }

    /**
     * 描述 获取版本号
     * 参数
     * 返回值
     */
    public static String getVersion(Context mContext) {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(),
                    0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 描述 获取屏幕宽高
     * 参数 Context context
     * 返回值 int[] 0:宽
     */
    public static int[] getScreen(Context context) {
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        int width;
        int height;
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
            width = size.x;
            height = size.y;

        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        return new int[]{width, height};
    }

    /**
     * 描述 获取屏幕宽
     * 参数
     * 返回值 int
     */
    public static int getScreenWidth(Context context) {
        return getScreen(context)[0];
    }

    /**
     * 描述 获取屏幕高
     * 参数
     * 返回值 int
     */
    public static int getScreenHeight(Context context) {
        return getScreen(context)[1];
    }

    public static <T> T clearListWithoutPos(int pos, List<T> list) {
        if (list == null || pos < 0 || pos > list.size() - 1) {
            return null;
        }
        T obj = list.get(0);
        list.clear();
        list.add(obj);
        return obj;
    }


}
