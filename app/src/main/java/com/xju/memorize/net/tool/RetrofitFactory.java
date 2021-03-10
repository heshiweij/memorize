package com.xju.memorize.net.tool;


import android.util.Log;

import com.xju.memorize.base.BaseApplication;
import com.xju.memorize.constant.Constant;
import com.xju.memorize.net.api.HttpApi;
import com.xju.memorize.net.api.URL;
import com.xju.memorize.util.NetUtil;
import com.xju.memorize.util.SharedPreferencesUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//封装Retrofit配置

public class RetrofitFactory {
    public String TAG = RetrofitFactory.class.toString();

    public static String BASE_URL = URL.getUrl();
    private static final int DEFAULT_CONNECT_TIMEOUT = 30;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;
    private static final int DEFAULT_READ_TIMEOUT = 30;
    private Retrofit retrofit;
    private HttpApi httpApi;
    //请求失败重连次数
    private int RETRY_COUNT = 0;
    private OkHttpClient.Builder okHttpBuilder;

    //构造方法私有
    private RetrofitFactory() {
        //手动创建一个OkHttpClient并设置超时时间
        okHttpBuilder = new OkHttpClient.Builder();
        //设置缓存
        File cacheFile = new File(BaseApplication.getApp().getExternalCacheDir(), BaseApplication.getApp().getPackageName());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (!NetUtil.isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader(BaseApplication.getApp().getPackageName())// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为1天
                    int maxStale = 60 * 60 * 24;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader(BaseApplication.getApp().getPackageName())
                            .build();
                }
                return response;
            }
        };
        okHttpBuilder.cache(cache).addInterceptor(cacheInterceptor);


        //设置头信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
//                HttpUrl url=originalRequest.url().newBuilder()
//                        .addQueryParameter("appVersion", "1")
//                        .addQueryParameter("osv", "13.2")
//                        .addQueryParameter("os", "andorid")
//                        .addQueryParameter("appId", "zhencheng")
//                        .addQueryParameter("ip", "192.168.8.15")
//                        .addQueryParameter("channel", "1")
//                        .addQueryParameter("agentNet", "0")
//                        .addQueryParameter("netStatus", "WIFI")
//                        .addQueryParameter("deviceId", "1234568523546")
//                        .build();
                Request.Builder requestBuilder = originalRequest.newBuilder()
//                        .addHeader("Accept-Encoding", "gzip")
//                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .method(originalRequest.method(), originalRequest.body());
                String token = SharedPreferencesUtil.getSharedPreferencesData(BaseApplication.getApp(), Constant.DEVICE_TOKEN_KEY);
                Log.d(TAG, "token: " + token + " url:" + originalRequest.url());
                requestBuilder.addHeader("Authorization", String.format("Bearer %s", token));
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        okHttpBuilder.addInterceptor(headerInterceptor);


//        if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("memorize:", message);
            }


        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 Debug Log 模式
        okHttpBuilder.addInterceptor(loggingInterceptor);
//        }

        //设置超时和重新连接
        okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        //错误重连
        okHttpBuilder.retryOnConnectionFailure(true);


        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())//json转换成JavaBean
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();

    }

    //获取单例
    public static RetrofitFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //获取retrofit
    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void changeBaseUrl(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }

    //获取httpService
    public HttpApi getHttpApi() {
        return httpApi;
    }

    //设置订阅 和 所在的线程环境
    public <T> void toSubscribe(Observable<T> o, DisposableObserver<T> s) {

        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(RETRY_COUNT)//请求失败重连次数
                .subscribe(s);

    }
}