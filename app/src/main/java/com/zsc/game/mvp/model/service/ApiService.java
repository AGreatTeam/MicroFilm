package com.zsc.game.mvp.model.service;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * 类的用途：retrofit动态代理必须提供的接口类
 * <p>
 * mac周昇辰
 * 2017/12/13  8:21
 */

public interface ApiService {

    //首页：http://api.svipmovie.com/front/homePageApi/homePage.do 请求方式：GET
    @GET("front/homePageApi/homePage.do")
    Flowable<String> getData();

}
