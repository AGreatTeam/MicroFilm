package com.zsc.game.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.presenter.FaPresenter;
import com.zsc.game.mvp.view.FaView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentA extends BaseFragment<FaPresenter> implements FaView {


    @BindView(R.id.image)
    ImageView image;
    Unbinder unbinder;

    @Override
    protected int setLayout() {
        return R.layout.fragmenta;
    }

    @Override
    protected void processLogic() {
        mPresenter.loadata();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xxx","123");
            }
        });

    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);

    }

    @Override
    public void showToast(String msg) {

    }



}
