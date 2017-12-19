package com.zsc.game.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.base.BaseView;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.model.bean.VideoDetail;
import com.zsc.game.mvp.presenter.ShiPinPresenter;
import com.zsc.game.mvp.view.ShiPinView;

import butterknife.BindView;

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

    @Override
    protected int setLayout() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void processLogic() {

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
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void getShipin(ShipinContentInfo.RetBean retBean) {

    }
}
