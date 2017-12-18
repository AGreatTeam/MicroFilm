package com.zsc.game.mvp.presenter;

import android.util.Log;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.FbModel;
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.mvp.view.FbView;

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

public class FbPresenter extends BasePresenter<FbModel,FbView> {


    @Inject
    public FbPresenter() {
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
                            Log.i("xxx","我的数据"+responseBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                         getView().showToast(responseBody);
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
