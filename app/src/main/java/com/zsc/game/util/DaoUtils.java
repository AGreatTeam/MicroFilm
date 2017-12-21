package com.zsc.game.util;

import android.util.Log;

import com.google.gson.Gson;
import com.zsc.game.base.BaseApplication;

import com.zsc.game.greendao.Data;
import com.zsc.game.greendao.Mls;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;

import java.util.ArrayList;
import java.util.List;

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
    public static  void insert(ShipinContentInfo.RetBean mClass,String id)
    {
        Gson gson=new Gson();
        String json=gson.toJson(mClass);

        Boolean ishas=false;
        List<Mls> list = BaseApplication.daoSession.getMlsDao().queryBuilder().list();

        Log.i("xxx","查询的数据"+list.size());
        for (int i=0;i<list.size();i++)
        {
             if(list.get(i).getTitile().equals(mClass.getTitle()))
             {
                 ishas=true;
                 Log.i("xxxe","有相同的");
             }

        }

        if(!ishas)
        {
            Log.i("xxxe","添加了");
            Mls date=new Mls();
            date.setJson(json);
            date.setVid(id);
            date.setTitile(mClass.getTitle());
            BaseApplication.daoSession.getMlsDao().insert(date);
        }


    }

    /**
     * 查询所有播放历史
     * @return
     */
    public static List<ShipinContentInfo.RetBean> selectAll()
    {
        List<Mls> list = BaseApplication
                .daoSession.getMlsDao().queryBuilder().list();

        List<ShipinContentInfo.RetBean> lists=new ArrayList<>();

        Gson gson=new Gson();

      for (int i=0;i<list.size();i++)
        {
           String json=list.get(i).getJson();
            ShipinContentInfo.RetBean  zz= gson.fromJson(json, ShipinContentInfo.RetBean.class);
            zz.setVid(list.get(i).getVid());
            lists.add(zz);

        }
       return  lists;
    }


    /**
     * 删除所有历史
     */
    public static void delectAll()
    {
        BaseApplication.daoSession.getMlsDao().deleteAll();
    }


}
