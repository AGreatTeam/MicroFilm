package com.zsc.game.di.module;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 类的用途：提供一些第三方实例
 * <p>
 * mac周昇辰
 * 2017/12/6  20:06
 */

@Module
public class ClientModule {

    public static final int TIMEOUT=10;

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder()
    {
        return  new Retrofit.Builder();
    }


    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpBuild()
    {
        return  new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Retrofit.Builder retrofitBuilder, OkHttpClient okHttpClient, String url)
    {
        retrofitBuilder
                .baseUrl(url)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        return  retrofitBuilder.build();
    }


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder okHttpBuilder, HttpLoggingInterceptor httpLoggingInterceptor, Interceptor netWorkInterceptor)
    {
        okHttpBuilder.
                 connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT,TimeUnit.SECONDS);
        if(httpLoggingInterceptor!=null)
        {
            okHttpBuilder.addInterceptor(httpLoggingInterceptor);
        }
        if(netWorkInterceptor!=null)
        {
            okHttpBuilder.addNetworkInterceptor(netWorkInterceptor);
        }

        return  okHttpBuilder.build();

    }


    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLogg()
    {
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("xxx",message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       return  httpLoggingInterceptor;
    }




}
