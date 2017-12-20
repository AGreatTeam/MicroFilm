package com.zsc.game.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dou361.ijkplayer.bean.VideoijkBean;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.IjkVideoView;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.model.bean.VideoDetail;
import com.zsc.game.mvp.presenter.ShiPinPresenter;
import com.zsc.game.mvp.view.ShiPinView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 苏照亮 2017/12/19.
 */

public class VideoDetailActivity extends BaseActivity<ShiPinPresenter> implements ShiPinView {

    @BindView(R.id.goback)
    ImageView goback;
    @BindView(R.id.gobackLayout)
    LinearLayout gobackLayout;
    @BindView(R.id.title_bar_name)
    TextView titleBarName;
    @BindView(R.id.settv)
    TextView settv;
    @BindView(R.id.title_bar_layout)
    LinearLayout titleBarLayout;
    @BindView(R.id.video_view)
    IjkVideoView videoView;
    @BindView(R.id.iv_trumb)
    ImageView ivTrumb;
    @BindView(R.id.ll_bg)
    LinearLayout llBg;
    @BindView(R.id.app_video_status_text)
    TextView appVideoStatusText;
    @BindView(R.id.app_video_replay_icon)
    ImageView appVideoReplayIcon;
    @BindView(R.id.app_video_replay)
    LinearLayout appVideoReplay;
    @BindView(R.id.app_video_netTie_icon)
    TextView appVideoNetTieIcon;
    @BindView(R.id.app_video_netTie)
    LinearLayout appVideoNetTie;
    @BindView(R.id.app_video_speed)
    TextView appVideoSpeed;
    @BindView(R.id.app_video_loading)
    LinearLayout appVideoLoading;
    @BindView(R.id.app_video_volume_icon)
    ImageView appVideoVolumeIcon;
    @BindView(R.id.app_video_volume)
    TextView appVideoVolume;
    @BindView(R.id.app_video_volume_box)
    LinearLayout appVideoVolumeBox;
    @BindView(R.id.app_video_brightness_icon)
    ImageView appVideoBrightnessIcon;
    @BindView(R.id.app_video_brightness)
    TextView appVideoBrightness;
    @BindView(R.id.app_video_brightness_box)
    LinearLayout appVideoBrightnessBox;
    @BindView(R.id.app_video_fastForward)
    TextView appVideoFastForward;
    @BindView(R.id.app_video_fastForward_target)
    TextView appVideoFastForwardTarget;
    @BindView(R.id.app_video_fastForward_all)
    TextView appVideoFastForwardAll;
    @BindView(R.id.app_video_fastForward_box)
    LinearLayout appVideoFastForwardBox;
    @BindView(R.id.app_video_center_box)
    FrameLayout appVideoCenterBox;
    @BindView(R.id.app_video_finish)
    ImageView appVideoFinish;
    @BindView(R.id.app_video_title)
    TextView appVideoTitle;
    @BindView(R.id.app_video_menu)
    ImageView appVideoMenu;
    @BindView(R.id.app_video_top_box)
    LinearLayout appVideoTopBox;
    @BindView(R.id.app_video_play)
    ImageView appVideoPlay;
    @BindView(R.id.app_video_currentTime_full)
    TextView appVideoCurrentTimeFull;
    @BindView(R.id.app_video_currentTime_left)
    TextView appVideoCurrentTimeLeft;
    @BindView(R.id.app_video_endTime_left)
    TextView appVideoEndTimeLeft;
    @BindView(R.id.app_video_lift)
    LinearLayout appVideoLift;
    @BindView(R.id.app_video_seekBar)
    SeekBar appVideoSeekBar;
    @BindView(R.id.app_video_currentTime)
    TextView appVideoCurrentTime;
    @BindView(R.id.app_video_endTime)
    TextView appVideoEndTime;
    @BindView(R.id.app_video_center)
    LinearLayout appVideoCenter;
    @BindView(R.id.app_video_endTime_full)
    TextView appVideoEndTimeFull;
    @BindView(R.id.app_video_process_panl)
    LinearLayout appVideoProcessPanl;
    @BindView(R.id.app_video_stream)
    TextView appVideoStream;
    @BindView(R.id.ijk_iv_rotation)
    ImageView ijkIvRotation;
    @BindView(R.id.app_video_fullscreen)
    ImageView appVideoFullscreen;
    @BindView(R.id.ll_bottom_bar)
    LinearLayout llBottomBar;
    @BindView(R.id.simple_player_volume_controller)
    SeekBar simplePlayerVolumeController;
    @BindView(R.id.simple_player_volume_controller_container)
    LinearLayout simplePlayerVolumeControllerContainer;
    @BindView(R.id.simple_player_brightness_controller)
    SeekBar simplePlayerBrightnessController;
    @BindView(R.id.simple_player_brightness_controller_container)
    LinearLayout simplePlayerBrightnessControllerContainer;
    @BindView(R.id.simple_player_settings_container)
    LinearLayout simplePlayerSettingsContainer;
    @BindView(R.id.simple_player_select_streams_list)
    ListView simplePlayerSelectStreamsList;
    @BindView(R.id.simple_player_select_stream_container)
    LinearLayout simplePlayerSelectStreamContainer;
    @BindView(R.id.play_icon)
    ImageView playIcon;
    @BindView(R.id.app_video_box)
    RelativeLayout appVideoBox;
    private PlayerView player;

    List<VideoijkBean> list = new ArrayList<VideoijkBean>();
    private RequestManager glide;
    private String hdurl;

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
            mPresenter.getLoadShipin(videoInfo.dataId);
            titleBarName.setText(videoInfo.title);
        }
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
