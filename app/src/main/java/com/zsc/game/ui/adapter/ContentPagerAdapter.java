package com.zsc.game.ui.adapter;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  14:07
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 *
 */
public class ContentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public ContentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

}
