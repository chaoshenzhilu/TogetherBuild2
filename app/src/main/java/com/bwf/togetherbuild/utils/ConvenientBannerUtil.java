package com.bwf.togetherbuild.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bwf.togetherbuild.ImageUtils.ImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;



public class ConvenientBannerUtil {
    private Context context;
    private List<String> imgPath;
    private int[] indicators;
    private ConvenientBanner banner;

    public ConvenientBannerUtil(Context context, ConvenientBanner banner, List<String> imgPath, int[] indicators) {
        this.context = context;
        this.banner = banner;
        this.imgPath = imgPath;
        this.indicators = indicators;

        startTurning(2000);
        init();
    }

    public void init(){
        banner.setPageTransformer(new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

            }
        });
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, imgPath) .setPageIndicator(indicators)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    public void startTurning(int duration){
        if(banner.isTurning()){
            stopTurning();
        }
        banner.startTurning(duration);
    }

    public void stopTurning(){
        banner.stopTurning();
    }

    public class NetworkImageHolderView implements Holder<String> {
        private SimpleDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new SimpleDraweeView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageLoader.getInstance().disPlayImage(imageView, data);
        }
    }
}
