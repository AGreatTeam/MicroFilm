package com.zsc.game.mvp.view;

import com.zsc.game.base.BaseView;

import java.util.List;

/**
 * Created by 苏照亮 2017/12/17.
 */

public interface WelcomeView extends BaseView {

    void showContent(List<String> list);

    void jumpToMain();
}
