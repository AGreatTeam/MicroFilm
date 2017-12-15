package com.zsc.game.di.component;

import android.app.Application;

import com.zsc.game.di.module.AppModule;
import com.zsc.game.di.module.ClientModule;
import com.zsc.game.di.module.GlobalConfigModule;
import com.zsc.game.di.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 *    1.
 *    {当子Conponent是依赖一个组件 时
 *        向下层传递 全局对象
 *     }
 *    2.
 *     {当上级Component提供包含方法是
 *       相当于动态的添加了同级的Module
 *
 *     }
 *
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/7  16:10
 */


@Singleton
@Component(modules = {AppModule.class,ClientModule.class, GlobalConfigModule.class})
public interface AppComponent {

 /*  Application proApplication();

   Retrofit    proRetrofit();
*/
   ActivityComponent plus(MainModule mainModule);
}
