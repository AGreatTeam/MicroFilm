package com.zsc.game.mvp.presenter;

import android.util.Log;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.CModel;
import com.zsc.game.mvp.view.MainView;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/6  19:37
 */

public class MainPresenter extends BasePresenter<CModel,MainView> {


    @Inject
    public MainPresenter() {
    }

    public void loadata()
     {
         model.loadData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<String>() {
                    @Override
                    public void onNext(String responseBody) {
                        try {
                            //String json=responseBody.string();
                            Log.i("xxx","我的数据"+responseBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        getView().showToast("成功");
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

     }



}
