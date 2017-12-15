package com.zsc.game.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 类的用途： 提供全局Context实例
 * <p>
 * mac周昇辰
 * 2017/12/7  9:32
 */

@Module
public class AppModule {

    private Context mApplicationContext;

    public AppModule(Context mApplicationContext) {
        this.mApplicationContext = mApplicationContext;
    }

    @Provides
    @Singleton
    public Context provideApplication()
    {
        return  mApplicationContext;
    }
}
