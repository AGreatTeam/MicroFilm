package com.zsc.game.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.WelcomePresenter;
import com.zsc.game.mvp.view.WelcomeView;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeView {

    @BindView(R.id.iv_welcome)
    ImageView ivWelcome;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void processLogic() {
        mPresenter.getWelcomeData();
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
    }

    @Override
    public void showContent(List<String> list) {
        if (list != null) {
            int page = new Random().nextInt(list.size() - 1) % (list.size());
            Glide.with(this).load(list.get(page)).into(ivWelcome);
            ivWelcome.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
        }
    }

    @Override
    public void jumpToMain() {
        startActivity(new Intent(this, Main2Activity.class));
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
