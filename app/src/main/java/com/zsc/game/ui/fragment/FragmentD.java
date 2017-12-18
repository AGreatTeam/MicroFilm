package com.zsc.game.ui.fragment;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.FdPresenter;
import com.zsc.game.mvp.view.FdView;

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
    protected void addLayout() {

    }

    @Override
    public void showToast(String msg) {

    }
}
