package com.zsc.game.mvp.presenter;

import android.util.Log;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.VrVideoModel;
import com.zsc.game.mvp.model.bean.VrVideoBean;
import com.zsc.game.mvp.view.VrVideoView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by 1 on 2017/12/19.
 */

public class VrVideoPresenter extends BasePresenter<VrVideoModel,VrVideoView> {

    @Inject
    VrVideoModel vrVideoModel;

    @Inject
    public VrVideoPresenter() {

    }
    public void getVideoPer(){
        vrVideoModel.getVrVideo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<VrVideoBean>() {
                    @Override
                    public void onNext(VrVideoBean vrVideoBean) {
                        Log.i("ffff","我总算找到了");
                        Log.i("ffff",vrVideoBean.getMessage());

                        getView().Show(vrVideoBean);
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
