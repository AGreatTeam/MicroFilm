package com.zsc.game.mvp.presenter;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.WelcomeModel;
import com.zsc.game.mvp.view.WelcomeView;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 苏照亮 2017/12/17.
 */

public class WelcomePresenter extends BasePresenter<WelcomeModel, WelcomeView> {

    @Inject
    public WelcomePresenter() {
    }

    public void getWelcomeData(){
        getView().showContent(model.getImgData());
        Observable.timer(2200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        getView().jumpToMain();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
