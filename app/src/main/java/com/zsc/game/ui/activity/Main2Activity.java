package com.zsc.game.ui.activity;

import android.support.v4.app.Fragment;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.MainPresenter;
import com.zsc.game.mvp.view.MainView;
import com.zsc.game.ui.adapter.ContentPagerAdapter;
import com.zsc.game.ui.fragment.FragmentA;
import com.zsc.game.ui.fragment.FragmentB;
import com.zsc.game.ui.fragment.FragmentC;
import com.zsc.game.ui.fragment.FragmentD;
import com.zsc.game.widget.ResideLayout;
import com.zsc.game.widget.UnScrollViewPager;
import com.zsc.game.widget.theme.ColorRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class Main2Activity extends BaseActivity<MainPresenter> implements MainView {


    @BindView(R.id.crl_cehua)
    ColorRelativeLayout crlCehua;
    @BindView(R.id.unVP)
    UnScrollViewPager vpContent;
    @BindView(R.id.tab_rb_1)
    RadioButton tabRb1;
    @BindView(R.id.tab_rb_2)
    RadioButton tabRb2;
    @BindView(R.id.tab_rb_3)
    RadioButton tabRb3;
    @BindView(R.id.tab_rb_4)
    RadioButton tabRb4;
    @BindView(R.id.tab_rg_menu)
    RadioGroup tabRgMenu;
    @BindView(R.id.crl_zhu)
    ColorRelativeLayout crlZhu;
    @BindView(R.id.cehua)
    ResideLayout cehua;
    private ContentPagerAdapter mPagerAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_main2;
    }

    @Override
    protected void processLogic() {

        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(false);
        mPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager(), fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
        mPresenter.loadata();

        tabRgMenu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_rb_1:
                        vpContent.setCurrentItem(0, false);
                        break;
                    case R.id.tab_rb_2:
                        vpContent.setCurrentItem(1, false);
                        break;
                    case R.id.tab_rb_3:
                        vpContent.setCurrentItem(2, false);
                        break;
                    case R.id.tab_rb_4:
                        vpContent.setCurrentItem(3, false);
                        break;
                }
            }
        });

    }
    private List<Fragment> initFragments()
    {
        List<Fragment> list=new ArrayList<>();
        list.add(new FragmentA());
        list.add(new FragmentB());
        list.add(new FragmentC());
        list.add(new FragmentD());
        return  list;
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
    }


    @Override
    public void showToast(String msg) {
        Toast.makeText(this, "哈哈哈", Toast.LENGTH_SHORT).show();
    }



}
