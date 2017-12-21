package com.zsc.game.mvp.model;

import com.zsc.game.di.module.My;
import com.zsc.game.mvp.model.bean.VrVideoBean;
import com.zsc.game.di.module.My;
import com.zsc.game.mvp.model.service.ApiService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Retrofit;

/**
 * Created by 1 on 2017/12/19.
 */

public class VrVideoModel {

    @Inject
    public VrVideoModel() {
    }
    @My
    @Inject
    Retrofit retrofit;

    public Flowable<VrVideoBean> getVrVideo(){
        Flowable<VrVideoBean> vrVideo = retrofit.create(ApiService.class).getVrVideo();
        return  vrVideo;
    }
}
