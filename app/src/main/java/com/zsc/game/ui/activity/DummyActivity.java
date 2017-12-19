package com.zsc.game.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zsc.game.R;
import com.zsc.game.ui.fragment.VRpickFragment;
import com.zsc.game.ui.fragment.VRvideoFragment;

import java.util.ArrayList;
import java.util.List;

public class DummyActivity extends AppCompatActivity {


    private View viewpager;
    private ViewPager viewpager1;
    private List<Fragment>  list;
    private List<String> stringList;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
       // registerReceiver();
        stringList = new ArrayList<>();
        stringList.add("全景图");
        stringList.add("全景视频");
        tabLayout = findViewById(R.id.rcf_tablayout);
        viewpager1 = findViewById(R.id.rcf_viewpager);
        addYeKa();
    }
    /**
     *  加载Tablayout实现页卡效果
     **/
    public void addYeKa(){
        VRpickFragment vRpickFragment = new VRpickFragment();
        VRvideoFragment vRvideoFragment = new VRvideoFragment();
        list = new ArrayList<>();
        list.add(vRpickFragment);
        list.add(vRvideoFragment);
        //进行设置默认的模式, MODE_SCROLLABLE这里是进行滑动的
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(stringList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(stringList.get(1)));

        //获得fragment的管理者
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //进行viewpager的数据适配器
        viewpager1.setAdapter(new getTablayAdapter(supportFragmentManager));
        //进行关联
        tabLayout.setupWithViewPager(viewpager1);
        tabLayout.setTabsFromPagerAdapter(new getTablayAdapter(supportFragmentManager));
    }

    public class getTablayAdapter extends FragmentPagerAdapter{


        public getTablayAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
