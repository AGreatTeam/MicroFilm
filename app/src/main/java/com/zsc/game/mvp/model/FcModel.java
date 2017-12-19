package com.zsc.game.mvp.model;

import com.zsc.game.base.BaseModel;
import com.zsc.game.mvp.model.bean.VideoCatagory;
import com.zsc.game.di.module.Me;
import com.zsc.game.mvp.model.bean.VideoInfo;
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

    @Me
    @Inject
    Retrofit retrofit;
    @Inject
    public FcModel() {
    }

    public Flowable<VideoCatagory> loadData(String catalogId, String pnum)
    {
        return  retrofit.create(ApiService.class).getVideoList(catalogId, pnum);
    }

}
