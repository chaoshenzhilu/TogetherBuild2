package com.bwf.togetherbuild.utils.display;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;


/**
 * Created by Fanhy on 2016/10/29.
 * Description  为TextView设置上下左右的图片
 */
public class DrawableUtil {

    /**
     * 设置drawableLeft图片
     */
    public static <T extends TextView> void drawableLeft(Context context, T myTextview, int res) {
        if (context == null || myTextview == null)
            return;
        Drawable drawable = ContextCompat.getDrawable(context, res);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myTextview.setCompoundDrawables(drawable, null, null, null);

    }

    /**
     * 设置drawableRight图片
     */
    public static <T extends TextView> void drawableRight(Context context, T myTextview, int res) {
        if (context == null || myTextview == null)
            return;
        Drawable drawable = ContextCompat.getDrawable(context, res);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myTextview.setCompoundDrawables(null, null, drawable, null);

    }

    /**
     * 设置drawableTop图片
     */
    public static <T extends TextView> void drawableTop(Context context, T myTextview, int res) {
        if (context == null || myTextview == null)
            return;
        Drawable drawable = ContextCompat.getDrawable(context, res);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myTextview.setCompoundDrawables(null, drawable, null, null);

    }

    /**
     * 设置drawableBottom图片
     */
    public static <T extends TextView> void drawableBottom(Context context, T myTextview, int res) {
        if (context == null || myTextview == null)
            return;
        Drawable drawable = ContextCompat.getDrawable(context, res);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        myTextview.setCompoundDrawables(null, null, null, drawable);

    }

    public static <T extends TextView> void drawableNone(Context context, T myTextview) {
        if (context == null || myTextview == null)
            return;
        myTextview.setCompoundDrawables(null, null, null, null);
    }

}
