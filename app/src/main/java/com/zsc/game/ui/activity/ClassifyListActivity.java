package com.zsc.game.ui.activity;

import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;

import com.zsc.game.mvp.model.bean.VideoInfo;

import com.zsc.game.mvp.presenter.MainPresenter;
import com.zsc.game.mvp.view.MainView;

public class ClassifyListActivity extends BaseActivity<MainPresenter> implements MainView {





    @Override
    protected int setLayout() {
        return R.layout.activity_classify_list;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {

    }

    @Override
    public void showToast(VideoInfo msg) {

    }
}
