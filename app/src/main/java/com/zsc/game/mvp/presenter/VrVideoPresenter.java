package com.zsc.game.mvp.presenter;

import com.zsc.game.base.BasePresenter;
import com.zsc.game.mvp.model.VrVideoModel;
import com.zsc.game.mvp.view.VrVideoView;

import javax.inject.Inject;

/**
 * Created by 1 on 2017/12/19.
 */

public class VrVideoPresenter extends BasePresenter<VrVideoModel,VrVideoView> {

    @Inject
    VrVideoModel vrVideoModel;

    @Inject
    public VrVideoPresenter() {
    }

    public void getVideoPer(){
        vrVideoModel.getVrVideo();
    }
}
