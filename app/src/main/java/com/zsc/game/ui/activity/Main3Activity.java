package com.zsc.game.ui.activity;

import android.content.Intent;
import android.util.Log;

import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.presenter.ShiPinPresenter;
import com.zsc.game.mvp.view.ShiPinView;
import com.zsc.game.util.NetUtils;

public class Main3Activity extends BaseActivity<ShiPinPresenter> implements ShiPinView {



    @Override
    protected int setLayout() {
        return R.layout.activity_main3;
    }

    @Override
    protected void processLogic() {
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        Log.i("rrr",id);
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
            mainComponent.Injext(this);
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

    }
}
