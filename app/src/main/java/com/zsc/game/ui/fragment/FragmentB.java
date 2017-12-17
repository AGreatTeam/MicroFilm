package com.zsc.game.ui.fragment;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.FaPresenter;
import com.zsc.game.mvp.presenter.FbPresenter;
import com.zsc.game.mvp.view.FbView;

/**
 * 类的用途：专题
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentB extends BaseFragment<FbPresenter> implements FbView{


    @Override
    protected int setLayout() {
        return R.layout.fragmentb;
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
