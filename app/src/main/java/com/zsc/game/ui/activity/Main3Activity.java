package com.zsc.game.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.presenter.ShiPinPresenter;
import com.zsc.game.mvp.view.ShiPinView;
import com.zsc.game.util.DaoUtils;
import com.zsc.game.util.NetUtils;

import butterknife.BindView;

public class Main3Activity extends BaseActivity<ShiPinPresenter> implements ShiPinView {


    private String id;
    @BindView(R.id.app_video_box)
    RelativeLayout videoBox;

    @Override
    protected int setLayout() {
        return R.layout.activity_main3;
    }

    @Override
    protected void processLogic() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.i("rrr", id);
        mPresenter.getLoadShipin(id);
        int netWorkType = NetUtils.getNetWorkType(Main3Activity.this);
        Log.i("rrr",netWorkType+"");
//        if (netWorkType>1){
//            AlertDialog.Builder dialog=new AlertDialog.Builder(Main3Activity.this);
//            dialog.setTitle("网络提醒");
//            dialog.setMessage("您正在使用数据流量是否继续？");
//            dialog.setCancelable(false);
//            dialog.setPositiveButton("土豪继续",new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(Main3Activity.this, "正在使用数据流量播放哦~", Toast.LENGTH_SHORT).show();
//
//
//                }
//            });
//            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    finish();
//                }
//            });
//            dialog.show();
//        }else if (netWorkType==-1){
//            Toast.makeText(Main3Activity.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
//        }
    }

    //获取桥梁的对象
    @Override
    protected void initInject(ActivityComponent mainComponent) {
           mainComponent.Inject(this);
           isScreenChange();
    }

    public boolean isScreenChange() {

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation ; //获取屏幕方向

        if(ori == mConfiguration.ORIENTATION_LANDSCAPE){

//横屏
            ViewGroup.LayoutParams layoutParams = videoBox.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            videoBox.setLayoutParams(layoutParams);
            return true;
        }else if(ori == mConfiguration.ORIENTATION_PORTRAIT){

//竖屏
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            int height = defaultDisplay.getHeight();
            System.out.println("高度："+height);
            ViewGroup.LayoutParams layoutParams = videoBox.getLayoutParams();
            layoutParams.height = height/5*2;
            videoBox.setLayoutParams(layoutParams);
            return false;
        }
        return false;
    }
    //播放视频
    @Override
    public void getShipin(ShipinContentInfo.RetBean retBean) {
            Log.i("qqq",retBean.getHtmlURL());

        // 注释部分如果你要全屏播放就打开要不是的话就注释掉，我播放的是本地视频，这个也可以播放网络视频，只要把url改好把权限加上（网络，读写，网络状态）就行；
              new PlayerView(this)
                .setTitle(retBean.getTitle())
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .forbidTouch(false)
                .setPlaySource(retBean.getHDURL())
                .startPlay();

              //播放视频 记录历史 添加数据库
              DaoUtils.insert(retBean,id);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.base_slide_right_out_f,R.anim.base_slide_right_in_f);
    }
}
