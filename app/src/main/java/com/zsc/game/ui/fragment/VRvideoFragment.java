package com.zsc.game.ui.fragment;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.VrVideoPresenter;

/**
 * Created by 1 on 2017/12/18.
 */

public class VRvideoFragment extends BaseFragment<VrVideoPresenter> {

    @Override
    protected int setLayout() {
        return R.layout.vrvideofragment;
    }

    /***
     * 请求VR视频数据
     * */
    @Override
    protected void processLogic() {
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Injext(this);
    }


    @Override
    protected void addLayout() {
    }
}
