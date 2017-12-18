package com.zsc.game.mvp.presenter;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.CModel;
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.mvp.view.MainView;

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

public class MainPresenter extends BasePresenter<CModel,MainView> {


    @Inject
    public MainPresenter() {
    }

    public void loadata()
     {
         model.loadData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<VideoInfo>() {

                    @Override
                    public void onNext(VideoInfo videoInfo) {
                        getView().showToast(videoInfo);
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
