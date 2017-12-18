package com.zsc.game.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.zsc.game.app.MApplication;
import com.zsc.game.di.component.ActivityComponent;

import com.zsc.game.di.module.MainModule;


import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * 类的用途：baseactivity 无需多说
 * <p>
 * mac周昇辰
 * 2017/11/29  10:33
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {



    @Inject
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        cjs(true);
        ButterKnife.bind(this);
        initInject(ininComponent());
        if(mPresenter!=null)
        {
            mPresenter.attachView(this);
        }
        processLogic();

    }

    /**
     * 设置布局
     * @return
     */
    protected abstract int setLayout();
    /**
     * 处理逻辑
     * @return
     */
    protected abstract void processLogic();

    /**
     * 沉浸式
     * @return
     */
    protected  void cjs(Boolean on)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * 拿到component  可以注入
     * @return
     */
    protected  abstract void initInject(ActivityComponent mainComponent);
    /**
     * 拿到桥梁对象
     * @return
     */
    protected ActivityComponent ininComponent()
    {
       return  MApplication.appComponent.plus(new MainModule());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }





}
