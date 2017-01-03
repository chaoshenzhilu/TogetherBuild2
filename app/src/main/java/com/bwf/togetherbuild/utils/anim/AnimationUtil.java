package com.bwf.togetherbuild.utils.anim;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by Fanhy on 2016/11/16.
 * Description  转场动画工具类
 */
public class AnimationUtil {

    //动画持续时间
    public final static int ANIMATION_IN_TIME = 800;
    public final static int ANIMATION_OUT_TIME = 800;

    public static Animation createInAnimation(Context context, int fromXDelta) {
        AnimationSet set = new AnimationSet(context, null);
        set.setFillAfter(true);

        TranslateAnimation animation = new TranslateAnimation(fromXDelta, 0, 0, 0);
        animation.setDuration(ANIMATION_IN_TIME);
        set.addAnimation(animation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(ANIMATION_IN_TIME);
        set.addAnimation(alphaAnimation);
        return set;
    }

    public static Animation createOutAnimation(Context context, int toXDelta) {
        AnimationSet set = new AnimationSet(context, null);
        set.setFillAfter(true);

        TranslateAnimation animation = new TranslateAnimation(0, toXDelta, 0, 0);
        animation.setDuration(ANIMATION_OUT_TIME);
        set.addAnimation(animation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(ANIMATION_OUT_TIME);
        set.addAnimation(alphaAnimation);
        return set;
    }
}
