package com.zsc.game.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsc.game.app.MApplication;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.di.module.MainModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/11/29  10:48
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {


    @Inject
    protected P  mPresenter;
    private View view;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null)
        {
            view = inflater.inflate(setLayout(),container,false);

            unbinder = ButterKnife.bind(this, view);
          //  ButterKnife.bind(getActivity());
        }

        initInject(ininComponent());
        if(mPresenter!=null)
        {
            mPresenter.attachView( this);
        }
        addLayout();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected  abstract void addLayout();


    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewById(int id)
    {if (view == null) {return null;}
        return (T) view.findViewById(id);
    }
}
