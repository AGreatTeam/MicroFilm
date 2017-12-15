package com.zsc.game.mvp.model;

import com.zsc.game.base.BaseModel;
import com.zsc.game.mvp.model.service.ApiService;

import javax.inject.Inject;

import io.reactivex.Flowable;
import retrofit2.Retrofit;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/6  19:39
 */

public class FcModel implements BaseModel {

    @Inject
    Retrofit retrofit;
    @Inject
    public FcModel() {
    }

    public Flowable<String> loadData()
    {
        return  retrofit.create(ApiService.class).getData();
    }
}
