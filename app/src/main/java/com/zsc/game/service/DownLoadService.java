package com.zsc.game.service;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/20  18:55
 */

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zsc.game.base.BaseApplication;
import com.zsc.game.greendao.LoadInfo;
import com.zsc.game.greendao.LoadInfoDao;
import com.zsc.game.util.DownLoadUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/11/13  20:30
 */

public class DownLoadService extends Service {

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i("xxxx","哈哈哈1");
            LoadInfo info= (LoadInfo) msg.obj;
            LoadInfoDao infoDao = BaseApplication.daoSession.getLoadInfoDao();

            DownLoadUtil util=new DownLoadUtil(infoDao,info,2);
            util.download();
        }
    };
    private LoadInfo info;
    private String name;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        info =  intent.getParcelableExtra("info");
        //算出长度
        int end= info.getUrl().length();
        //截取出后六位
        name = info.getUrl().substring(end-6,end);
        // split = name.split(".");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url=new URL(info.getUrl());
                    HttpURLConnection con= (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(5000);
                    int lenth=-1;
                    if(con.getResponseCode()==200)
                    {
                        lenth = con.getContentLength();
                    }
                    if(lenth<=0)
                    {
                        return;
                    }
                    //本地下载的文件夹
                    File dir=new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"/MLoad");
                    //不存在就创建
                    if(!dir.exists())
                    {
                        dir.mkdir();
                    }
                    //创建文件
                    File file=new File(dir,name);
                    RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rwd");
                    randomAccessFile.setLength(lenth);
                    info.setLength(lenth);
                    Message message=new Message();
                    message.obj= info;
                    mHandler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}
