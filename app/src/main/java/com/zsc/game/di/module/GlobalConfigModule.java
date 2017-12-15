package com.zsc.game.di.module;

import android.text.TextUtils;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

/**
 * 类的用途：全局配置 可进行初始化参数
 * <p>
 * mac周昇辰
 * 2017/12/10  19:52
 */
@Module
public class GlobalConfigModule {

      private String baseurl;
      private Interceptor netWorkInterceptor;

      private List<Interceptor> mInterceptors;

      public GlobalConfigModule(Builder builder) {
        this.baseurl=builder.baseurl;
        this.mInterceptors=builder.mInterceptors;
        this.netWorkInterceptor=builder.netWorkInterceptor;
      }

      @Provides
      @Singleton
      public String  proBaseurl()
      {
          return  baseurl;
      }

      @Provides
      @Singleton
      public Interceptor proNetwork()
      {
          return  netWorkInterceptor;
      }

      public static class Builder
      {

          private String baseurl;
          private Interceptor netWorkInterceptor;
          private List<Interceptor> mInterceptors;

          public Builder baseurl(String url)
          {
              if(TextUtils.isEmpty(url))
              {
                  throw new NullPointerException("你像个傻蛋一样传了一个空 url mdzz");
              }
              this.baseurl=url;
              return  this;
          }

          public Builder netWorkInterceptor(Interceptor interceptor)
          {
                this.netWorkInterceptor=interceptor;
                return this;
          }

          public  GlobalConfigModule builder()
          {
              return  new GlobalConfigModule(this);
          }

      }

}
