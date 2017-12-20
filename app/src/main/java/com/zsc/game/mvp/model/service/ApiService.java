package com.zsc.game.mvp.model.service;

import com.zsc.game.mvp.model.bean.VideoCatagory;

import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.mvp.model.bean.VrVideoBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    //专题：http://api.svipmovie.com/front/columns/getVideoList.do?catalogId=1&pnum=10 请求方式：GET
    /**
     * 影片分类列表
     * @param catalogId
     * @param pnum
     * @return
     */
    @GET("front/columns/getVideoList.do")
    Flowable<VideoCatagory> getVideoList(@Query("catalogId") String catalogId, @Query("pnum") String pnum);
    @GET("front/videoDetailApi/videoDetail.do")
    Flowable<ShipinContentInfo> getLoadShiPin(@Query("mediaId") String id);
    //第二个页面fragmennt
    @GET("front/homePageApi/homePage.do")
    Flowable<VideoInfo> getData1();

    /**
     * VR视频数据
     * */
    @GET("api/video/query")
    Flowable<VrVideoBean> getVrVideo();

}
