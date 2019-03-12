package com.by5388.photogallery.rx.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author admin  on 2019/3/12.
 */
public class NetTools {
    private Retrofit mRetrofit;
    public static final String BASE_URL = "https://api.flickr.com/";

    public NetTools() {
//        final OkHttpClient client = new OkHttpClient.Builder()
//                .callTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100, TimeUnit.SECONDS)
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .writeTimeout(100, TimeUnit.SECONDS)
//                .build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                //设置服务器地址
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

//        mRetrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
