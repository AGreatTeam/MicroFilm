package com.zsc.game.mvp.presenter;

import android.util.Log;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.ShiPinModle;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.view.ShiPinView;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by 1 on 2017/12/17.
 */

public class ShiPinPresenter extends BasePresenter<ShiPinModle,ShiPinView> {


    @Inject
    public ShiPinPresenter() {
    }

    //请求加载数据
    public  void getLoadShipin(String id){
        Log.i("xxx","我的是肯定");
        Flowable<ShipinContentInfo> shiModle = model.getShiModle(id);
        shiModle.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSubscriber<ShipinContentInfo>() {
            @Override
            public void onNext(ShipinContentInfo shipinContentInfo) {
                ShipinContentInfo.RetBean ret = shipinContentInfo.getRet();
                getView().getShipin(ret);
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
