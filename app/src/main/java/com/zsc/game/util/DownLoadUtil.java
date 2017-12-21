package com.zsc.game.util;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/20  18:58
 */

import android.os.Environment;
import android.util.Log;

import com.zsc.game.greendao.LoadInfo;
import com.zsc.game.greendao.LoadInfoDao;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/11/13  14:50
 */

public class DownLoadUtil {
    //标识状态
    public static Boolean isPause=false;
    //记录下载长度
    private Long finished=new Long(0);
    //dao
    private LoadInfoDao infoDao;
    //信息
    private LoadInfo fileInfo;
    private int threadCount;
    private Long lengthzz;
    private List<LoadInfo> lists;
    //初始化线程池
    private  ExecutorService service = Executors.newFixedThreadPool(6);
    //初始化线程集合
    private List<MyThread> threads;

    public DownLoadUtil( LoadInfoDao threadDAO, LoadInfo fileInfo,int threadCount) {
        this.lengthzz= Long.valueOf(fileInfo.getLength());
        this.infoDao = threadDAO;
        this.fileInfo = fileInfo;
        this.threadCount=threadCount;
    }

    //下载方法
    public  synchronized  void download(){

        lists = infoDao.queryBuilder().where(LoadInfoDao.Properties.Url.eq(fileInfo.getUrl())).build().list();

        if(lists.size() == 0){
            Log.i("zz","第一次下载");
            //第一次下载，创建子线程下载
            for(int i=0;i<threadCount;i++)
            {
                //任务总长度
                int length=fileInfo.getLength();
                //任务平均长度
                int  ren=length/threadCount;
                //单任务开始位置
                int start=i*ren;
                //单任务结束位置
                int now=(i+1)*ren-1;
                if(i==threadCount-1)
                {
                    now=length-1;
                }
                LoadInfo info1=new LoadInfo();
                info1.setLength(now);
                info1.setStart(start);
                info1.setUrl(fileInfo.getUrl());
                //添加到集合
                lists.add(info1);
                infoDao.insert(info1);
            }

        }
        threads = new ArrayList<>();
        for (LoadInfo in:lists)
        {
           /*MyThread myThread=new MyThread(in);
            Log.i("xxx",in.getNow()+"xxxxx");
            service.execute(myThread);
            threads.add(myThread);*/
          load(in);
        }
    }



    public void load(final LoadInfo info)
    {
        try {
        //设置下载位置
        final int start =info.getStart()+info.getNow();

        //算出长度
        int end=info.getUrl().length();
        //截取出后六位
        String name = info.getUrl().substring(end-6,end);
        //设置文件写入位置
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MLoad",name);

            final RandomAccessFile randomFile = new RandomAccessFile(file, "rwd");
            randomFile.seek(start);

        final OkHttpClient client=new OkHttpClient.Builder().build();

        final Request request = new Request.Builder()
                .url(info.getUrl())
                //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                .addHeader("RANGE", "bytes=" + start + "-" + info.getLength())
                .build();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.code()==206)
                {
                    //获得文件流
                 InputStream   inputStream = response.body().byteStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    long time = System.currentTimeMillis();

                    while ((len = inputStream.read(buffer))!= -1){
                        //写入文件
                        randomFile.write(buffer,0,len);
                        //把进度发送给Activity
                        finished += len;

                        info.setNow(info.getNow()+len);
                        Log.i("xxxz",info.getId()+"哈哈哈"+info.getNow());
                         Long  oo= finished*100/fileInfo.getLength();
                         Log.i("xxx",oo+"长度");
                        EventBus.getDefault().post(new MData(0,oo));
                        //判断是否是暂停状态
                        if(isPause){
                            Log.i("xxxx","修改了"+info.getNow());
                            info.setNow(info.getNow());
                            infoDao.update(info);
                            return; //结束循环
                        }
                    }
                    //删除线程信息
                    infoDao.delete(info);

                }
            }


        });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    class  MyThread extends Thread{
        private LoadInfo info = null;
        private String name;

        public MyThread(LoadInfo threadInfo) {
            this.info = threadInfo;
        }
        @Override
        public void run() {


            HttpURLConnection urlConnection = null;
            RandomAccessFile randomFile =null;
            InputStream inputStream = null;
            try {
                URL url = new URL(info.getUrl());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestMethod("GET");
                //设置下载位置
                int start =info.getStart()+info.getNow();
                Log.i("xxx","下载的开始位置"+start);
                urlConnection.setRequestProperty("Range","bytes=" + start + "-" + info.getLength());
                //算出长度
                int end=info.getUrl().length();
                //截取出后六位
                name = info.getUrl().substring(end-6,end);
                //设置文件写入位置
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MLoad",name);
                randomFile = new RandomAccessFile(file, "rwd");
                randomFile.seek(start);
                //向Activity发送
                Log.i("xxx","进度"+info.getNow());
                finished += info.getNow();
                Log.i("xxxx","数据库中的值"+finished);
                if (urlConnection.getResponseCode() == 206) {
                    //获得文件流
                    inputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer))!= -1){
                        //写入文件
                        randomFile.write(buffer,0,len);
                        //把进度发送给Activity
                        finished += len;

                        info.setNow(info.getNow()+len);
                        Log.i("xxxz",info.getId()+"哈哈哈"+info.getNow());
                        EventBus.getDefault().post(new MData(1,finished*100/fileInfo.getLength()));
                        //判断是否是暂停状态
                        if(isPause){
                            Log.i("xxxx","修改了"+info.getNow());
                            info.setNow(info.getNow());
                            infoDao.update(info);

                            return; //结束循环
                        }
                    }
                    //删除线程信息

                    infoDao.delete(info);


                    Log.i("xxxx","走了");

                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {//回收工作略
            }
        }
    }

}