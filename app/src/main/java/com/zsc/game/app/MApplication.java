package com.zsc.game.app;
import android.app.Application;
import com.zsc.game.base.BaseApplication;
import com.zsc.game.di.component.AppComponent;
import com.zsc.game.di.component.DaggerAppComponent;
import com.zsc.game.di.module.AppModule;
import com.zsc.game.di.module.GlobalConfigModule;
import com.zsc.game.di.module.MainModule;
import com.zsc.game.util.SystemUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 类的用途：Application  可以在此进行初始化操作
 * <p>
 * mac周昇辰
 * 2017/12/8  8:52
 */

public class MApplication extends BaseApplication {
    public final String URL="http://api.svipmovie.com/";
  //  public final String  PATH=  Environment.getExternalStorageDirectory().getAbsolutePath()+",MyCache";
    public static AppComponent appComponent;
    public static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
       mApplication=this;
       //缓存拦截器
       /* File cacheFile = new File(PATH);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);*/
        Interceptor interceptor=new Interceptor() {
           @Override
           public Response intercept(Chain chain) throws IOException {
               // 有网络时 设置缓存超时时间1个小时
               int maxAge = 60 * 60;
               // 无网络时，设置超时为1天
               int maxStale = 60 * 60 * 24;
               Request request = chain.request();
               if (SystemUtils.isNetworkConnected()) {
                   //有网络时只从网络获取
                   request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
               } else {
                   //无网络时只从缓存中读取
                   request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
               }
               Response response = chain.proceed(request);
               if (SystemUtils.isNetworkConnected()) {
                   response = response.newBuilder()
                           .removeHeader("Pragma")
                           .header("Cache-Control", "public, max-age=" + maxAge)
                           .build();
               } else {
                   response = response.newBuilder()
                           .removeHeader("Pragma")
                           .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                           .build();
               }
               return response;
           }
       };

        //
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this.getApplicationContext()))
                .globalConfigModule(new GlobalConfigModule.Builder()
                        .baseurl(URL)
                        .netWorkInterceptor(interceptor).builder())
                .build();

    }


}
