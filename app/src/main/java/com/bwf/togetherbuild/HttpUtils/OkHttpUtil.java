package com.bwf.togetherbuild.HttpUtils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Fanhy on 2016/10/10.
 * 基于OkHttp2.0 网络框架二次封装
 *
 * update by Fanhy on 2016/11/21.
 * 基于OkHttp3.4.2 重构网络框架，添加多图片上传
 */
public class OkHttpUtil {
    private static final String TAG = "OkHttpUtil";
    private static OkHttpUtil mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
    }
    private synchronized static OkHttpUtil getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtil();
        }
        return mInstance;
    }
    private void getRequest(String url, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }
    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }
    private void deliveryResult(final ResultCallback callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String str = response.body().string();
                    sendSuccessCallBack(callback, str);
                } catch (final Exception e) {
                    Log.e(TAG, e.toString());
                    sendFailCallback(callback, e);
                }
            }
        });
    }
    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFailure(e);
                }
            }
        });
    }
    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(obj);
                }
            }
        });
    }
    private Request buildPostRequest(String url, List<Param> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();		
    }
	 private void postImageRequest(String url, ResultCallback callback, List<Param> params) {
        Request request = buildPostImageRequest(url, params);
        deliveryResult(callback, request);
    }
    private Request buildPostImageRequest(String url, List<Param> params) {
        if(params == null || params.size()==0){
            throw new RuntimeException("上传图片路径为null");
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Param param : params) {
            File file = new File(param.value);
            if(file.exists()){
                builder.addFormDataPart(param.key, file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }
	
    /**********************对外接口************************/
    /**
     * get请求
     * @param url  请求url
     * @param callback  请求回调
     */
    public static void get(String url, ResultCallback callback) {
        getmInstance().getRequest(url, callback);
    }
    /**
     * post请求
     * @param url       请求url
     * @param callback  请求回调
     * @param params    请求参数
     */
    public static void post(String url, final ResultCallback callback, List<Param> params) {
        getmInstance().postRequest(url, callback, params);
    }
    /**
     * post请求, 图片上传
     * @param url       请求url
     * @param callback  请求回调
     * @param params    请求参数
     */
    public static void uploadImage(String url, final ResultCallback callback, List<Param> params) {
        getmInstance().postImageRequest(url, callback, params);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        /**
         * 请求成功回调
         * @param response
         */
        public abstract void onSuccess(T response);
        /**
         * 请求失败回调
         * @param e
         */
        public abstract void onFailure(Exception e);
    }
    /**
     * post请求参数类
     */
    public static class Param {
        String key;
        String value;
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
