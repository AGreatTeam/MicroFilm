package com.zsc.game.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.dou361.ijkplayer.bean.VideoijkBean;
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
import cn.jzvd.JZVideoPlayerStandard;

public class JZVideoActivity extends BaseActivity<ShiPinPresenter> implements ShiPinView {

    List<VideoijkBean> list = new ArrayList<>();
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
    @BindView(R.id.jz_video)
    JZVideoPlayerStandard jzVideo;
    private RequestManager glide;
    private String hdurl;
    private String dataId;
    private String title;

    @Override
    protected int setLayout() {
        return R.layout.activity_jzvideo;
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
            title = videoInfo.title;
            titleBarName.setText(title);
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
            titleBarLayout.setVisibility(View.VISIBLE);
            return false;
        }
        return false;
    }
    @Override
    public void getShipin(ShipinContentInfo.RetBean retBean) {
        if (retBean != null && retBean.getHDURL() != null) {
            hdurl = retBean.getHDURL();
            //播放视频 记录历史 添加数据库
            DaoUtils.insert(retBean,dataId);
        } else {
            hdurl = "http://movie.vods1.cnlive.com/3/vod/2017/0613/3_8cd070627bb54939bf8763e40cad3fbe/ff8080815bf6b453015ca02f88462114_1500.m3u8";
        }
        jzVideo.setUp(hdurl,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, title);
        glide.load(retBean.getPic()).into(jzVideo.thumbImageView);
    }

}
