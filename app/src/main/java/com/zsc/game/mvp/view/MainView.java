package com.zsc.game.mvp.view;

import com.zsc.game.base.BaseView;
import com.zsc.game.mvp.model.bean.VideoInfo;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/6  19:38
 */

public interface MainView extends BaseView {

    void showToast(VideoInfo msg);

}
