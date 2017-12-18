package com.zsc.game.mvp.presenter;

import android.util.Log;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.FaModel;
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.mvp.view.FaView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/6  19:37
 */

public class FaPresenter extends BasePresenter<FaModel,FaView> {


    @Inject
    public FaPresenter() {
    }

    public void loadata()
     {
         model.loadData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<VideoInfo>() {
                    @Override
                    public void onNext(VideoInfo responseBody) {
                        try {
                            //String json=responseBody.string();
                            Log.i("xxx","我fa的数据"+responseBody);
                            getView().showToast(responseBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                       // getView().showToast("fa成功");
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
