package com.zsc.game.mvp.model;

import com.zsc.game.di.module.Me;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.model.service.ApiService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Retrofit;

/**
 * Created by 1 on 2017/12/17.
 */

public class ShiPinModle  {

    @Inject
    public ShiPinModle() {
    }
    @Me
    @Inject
    Retrofit retrofit;

    public Flowable<ShipinContentInfo> getShiModle(String id){
        Flowable<ShipinContentInfo> loadShiPin = retrofit.create(ApiService.class).getLoadShiPin(id);

        return  loadShiPin;
    }
}
