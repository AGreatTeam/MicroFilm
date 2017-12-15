package com.zsc.game.ui.fragment;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.FaPresenter;
import com.zsc.game.mvp.presenter.FcPresenter;
import com.zsc.game.mvp.view.FcView;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentC extends BaseFragment<FcPresenter> implements FcView{


    @Override
    protected int setLayout() {
        return R.layout.fragmentc;
    }

    @Override
    protected void processLogic() {
    mPresenter.loadata();

    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {

        mainComponent.Inject(this);
    }

    @Override
    public void showToast(String msg) {

    }
}
