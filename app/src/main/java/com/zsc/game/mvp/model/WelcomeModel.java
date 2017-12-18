package com.zsc.game.mvp.model;

import com.zsc.game.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 苏照亮 2017/12/17.
 */

public class WelcomeModel implements BaseModel {

    @Inject
    public WelcomeModel() {
    }

    public List<String> getImgData() {
        List<String> imgs = new ArrayList<>();
        imgs.add("file:///android_asset/a.jpg");
        imgs.add("file:///android_asset/b.jpg");
        imgs.add("file:///android_asset/c.jpg");
        imgs.add("file:///android_asset/d.jpg");
        imgs.add("file:///android_asset/e.jpg");
        imgs.add("file:///android_asset/f.jpg");
        imgs.add("file:///android_asset/g.jpg");
        return imgs;
    }
}
