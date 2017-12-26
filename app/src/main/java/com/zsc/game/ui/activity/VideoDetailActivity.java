package com.zsc.game.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.model.bean.VideoDetail;
import com.zsc.game.mvp.presenter.ShiPinPresenter;
import com.zsc.game.mvp.view.ShiPinView;
import com.zsc.game.util.DaoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 苏照亮 2017/12/19.
 */

public class VideoDetailActivity extends BaseActivity<ShiPinPresenter> implements ShiPinView {

    @BindView(R.id.goback)
    ImageView goback;
    @BindView(R.id.title_bar_name)
    TextView titleBarName;

    @BindView(R.id.settv)
    TextView settv;

    @BindView(R.id.title_bar_layout)
    LinearLayout titleBarLayout;

    private PlayerView player;

    List<VideoijkBean> list = new ArrayList<>();
    private RequestManager glide;
    private String hdurl;
    private String dataId;

    @Override
    protected int setLayout() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void processLogic() {
        glide = Glide.with(this);
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
        Intent intent = getIntent();
        VideoDetail videoInfo = intent.getParcelableExtra("videoInfo");
        titleBarLayout.setBackgroundColor(Color.RED);
        goback.setVisibility(View.GONE);
        settv.setVisibility(View.GONE);
        if (videoInfo != null) {
            dataId = videoInfo.dataId;
            mPresenter.getLoadShipin(dataId);
            titleBarName.setText(videoInfo.title);
        }
        isScreenChange();
    }
    public boolean isScreenChange() {

        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation ; //获取屏幕方向

        if(ori == mConfiguration.ORIENTATION_LANDSCAPE){

//横屏
            titleBarLayout.setVisibility(View.GONE);
            return true;
        }else if(ori == mConfiguration.ORIENTATION_PORTRAIT){

//竖屏
            return false;
        }
        return false;
    }
    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void getShipin(ShipinContentInfo.RetBean retBean) {
        if (retBean != null && retBean.getHDURL() != null) {
            hdurl = retBean.getHDURL();
            DaoUtils.insert(retBean,dataId);
        } else {
            hdurl = "http://movie.vods1.cnlive.com/3/vod/2017/0613/3_8cd070627bb54939bf8763e40cad3fbe/ff8080815bf6b453015ca02f88462114_1500.m3u8";
        }
        VideoijkBean m1 = new VideoijkBean();
        m1.setStream("标清");
        m1.setUrl(hdurl);
        VideoijkBean m2 = new VideoijkBean();
        m2.setStream("高清");
        m2.setUrl(hdurl);
        list.add(m1);
        list.add(m2);
        /**播放器*/
        //View rootView = getLayoutInflater().from(this).inflate( , null);
        //setContentView(rootView);
        /**加载前显示的缩略图*/
        player = new PlayerView(this)
                .setTitle(retBean.getTitle())
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .setForbidDoulbeUp(false)
                .forbidTouch(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        /**加载前显示的缩略图*/
                        glide.load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                .placeholder(R.color.colorPurplePrimaryDark)
                                .error(R.color.colorLimePrimaryCenter)
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(list)
                .startPlay();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo 的内容，恢复系统其它媒体的状态*/
        //MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo 的内容，暂停系统其它媒体的状态*/
        //MediaUtils.muteAudioFocus(mContext, false);
        /**demo 的内容，激活设备常亮状态*/
        //if (wakeLock != null) {
        //    wakeLock.acquire();
        //}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

}
