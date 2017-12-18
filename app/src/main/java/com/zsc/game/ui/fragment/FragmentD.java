package com.zsc.game.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.FdPresenter;
import com.zsc.game.mvp.view.FdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentD extends BaseFragment<FdPresenter> implements FdView {




    @Override
    protected int setLayout() {
        return R.layout.fragmentd;
    }

    @Override
    protected void processLogic() {

    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {

        mainComponent.Inject(this);
    }

    @Override
    public void showToast(String msg) {

    }



}
