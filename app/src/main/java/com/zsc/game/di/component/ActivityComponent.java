package com.zsc.game.di.component;

import com.zsc.game.ui.activity.ClassifyListActivity;
import com.zsc.game.ui.activity.DetailActivity;
import com.zsc.game.ui.activity.Main2Activity;
import com.zsc.game.di.module.MainModule;
import com.zsc.game.di.module.YourScope;
import com.zsc.game.ui.activity.Main3Activity;
import com.zsc.game.ui.activity.WelcomeActivity;
import com.zsc.game.ui.fragment.FragmentA;
import com.zsc.game.ui.fragment.FragmentB;
import com.zsc.game.ui.fragment.FragmentC;
import com.zsc.game.ui.fragment.FragmentD;


import dagger.Subcomponent;

/**
 * 类的用途：注入 桥梁
 * <p>
 * 此YourScope注解为自定义注解，作用与single一致
 * 但写single会报错 :原因 上层组件和下层组件作用域不能一致
 * <p>
 * mac周昇辰
 * 2017/12/6  19:47
 */


@YourScope
@Subcomponent(modules = MainModule.class)
public interface ActivityComponent {

    void Inject(Main2Activity activity);
    void Inject(FragmentA fragmentA);
    void Inject(FragmentB fragmentB);
    void Inject(FragmentC fragmentC);
    void Inject(FragmentD fragmentD);
    void Inject(WelcomeActivity welcomeActivity);
    void Inject(Main3Activity activity);
    void Inject(ClassifyListActivity activity);
    void Inject(DetailActivity activity);

}
