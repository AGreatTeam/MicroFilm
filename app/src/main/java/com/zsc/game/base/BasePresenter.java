package com.zsc.game.base;

import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 类的用途：抽取presenter
 * 避免重复操作
 * 复用代码
 * <p>
 * mac周昇辰
 * 2017/12/5  10:24
 */

public class BasePresenter<M,V> {

    @Inject
    public M model;
    /**
     * 弱引用管理v  防止oom
     */
    private Reference<V> mReference;

    /**
     * 订阅管理者   防止 oom
     */

    private CompositeDisposable mCompositeDisposable;

    /**
     * 添加订阅
     */

    public void addDisposable(Disposable disposable)
    {
        if(mCompositeDisposable==null)
        {
            mCompositeDisposable=new CompositeDisposable();
        }

        mCompositeDisposable.add(disposable);

    }
    /**
     * 取消订阅
     */

    public void removeDisposable()
    {
        if(mCompositeDisposable!=null)
        {
            mCompositeDisposable.dispose();
        }
    }
    /**
     * 绑定的方法
     * @param view
     */
    public void attachView(V view)
    {
        mReference=new WeakReference<V>(view);

        Log.i("xxx", ""+(mReference.get() != null)+" "+(model!=null));
    }
    /**
     * 判断是否关联
     * @param
     */
    public boolean isAttachView()
    {
        if(mReference!=null&&mReference.get()!=null)
        {
            return true;
        }
        return false;
    }
    /**
     * 获取view
     */
    public V getView()
    {
        if(isAttachView())
        {
            return  mReference.get();
        }
        return  null;
    }

    /**
     * 解绑
     * @param
     */
    public void detachView()
    {
          if (isAttachView())
          {
              mReference.clear();
              mReference=null;
          }
    }
}
