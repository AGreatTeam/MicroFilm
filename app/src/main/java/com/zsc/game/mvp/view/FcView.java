package com.zsc.game.mvp.view;

import com.zsc.game.base.BaseView;
import com.zsc.game.mvp.model.bean.VideoCatagory;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/6  19:38
 */

public interface FcView extends BaseView {

    void showToast(String msg);
    void onSuccess(VideoCatagory catagory);
    void onError(String msg);

}
