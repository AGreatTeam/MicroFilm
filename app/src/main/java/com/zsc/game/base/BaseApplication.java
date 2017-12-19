package com.zsc.game.base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zsc.game.greendao.DaoMaster;
import com.zsc.game.greendao.DaoSession;

/**
 * 类的用途：可以再此配置
 * 比如  下面的例子 ：计算应用隐居后台时间
 * <p>
 * mac周昇辰
 * 2017/12/8  8:46
 */

public class BaseApplication extends Application {

    public static DaoSession daoSession;
    private long time = -2;
    public int count = 0;


    @Override
    public void onCreate() {
        super.onCreate();

       /* registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            private Date date;
            private long returnTime;

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

                if (count == 0) {
                    //Log.v("tag", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                    // 首先计算时间 网络请求判断是否进入手势密码验证界面
                    Log.v("zsc", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                    if (-2 == time) {
                        //判断进入解锁手势密码
                    } else {
                        Date date2 = new Date();
                        returnTime = date2.getTime();
                        double sub =returnTime-time;
                        if (sub >= 3000d) {
                            //判断进入解锁手势密码
                            Log.v("zsc", ">>>>>>>>>>>>>>>>>>>密码  lifecycle");
                        } else {
                            time = -1;
                        }
                    }
                } else {
                    if (-2 == time) {
                        //判断进入解锁手势密码
                    }
                    time = -1;
                }
                count++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

                count--;
                if (count == 0) {

                    Log.v("zsc", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
                    date = new Date();
                    time = date.getTime();
                } else {
                    time = -1;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });*/

        //配置数据库
        setupDatabase();
    }


    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }



}
