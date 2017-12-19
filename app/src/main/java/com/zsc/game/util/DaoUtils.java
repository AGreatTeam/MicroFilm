package com.zsc.game.util;

import android.util.Log;

import com.google.gson.Gson;
import com.zsc.game.base.BaseApplication;
import com.zsc.game.greendao.Data;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/19  11:18
 */

public class DaoUtils {

    /**播放历史
     * 添加到数据库
     * @param mClass
     * @param
     */
    public static  void insert(Object mClass)
    {
        Gson gson=new Gson();
        String json=gson.toJson(mClass);
        Boolean ishas=false;
        List<Data> list = BaseApplication.daoSession.getDataDao().queryBuilder().list();
        for (int i=0;i<list.size();i++)
        {
             if(json.equals(list.get(i).getJson()))
             {
                 ishas=true;
             }
        }
        if(!ishas)
        {
            Data date=new Data();
            date.setJson(json);
            BaseApplication.daoSession.getDataDao().insert(date);
        }


    }

    /**
     * 查询所有播放历史
     * @return
     */
    public static List<Object> selectAll()
    {
        List<Data> list = BaseApplication.daoSession.getDataDao().queryBuilder().list();

        List<Object> lists=new ArrayList<>();

        Gson gson=new Gson();

        for (int i=0;i<list.size();i++)
        {
           String json=list.get(i).getJson();
           lists.add(gson.fromJson(json, ShipinContentInfo.class));
        }
       return  lists;
    }

    /**
     * 删除所有历史
     */
    public static void delectAll()
    {
        BaseApplication.daoSession.getDataDao().deleteAll();
    }


}
