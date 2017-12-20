package com.zsc.game.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.VrVideoBean;
import com.zsc.game.mvp.presenter.VrVideoPresenter;
import com.zsc.game.mvp.view.VrVideoView;
import com.zsc.game.ui.adapter.VrVideoAdapter;

import java.util.List;

/**
 * Created by 1 on 2017/12/18.
 */

public class VRvideoFragment extends BaseFragment<VrVideoPresenter> implements VrVideoView{


    private XRecyclerView vr_recycle;

    @Override
    protected int setLayout() {
        return R.layout.vrvideofragment;
    }

    /***
     * 请求VR视频数据
     * */
    @Override
    protected void processLogic() {
            mPresenter.getVideoPer();
    }
    @Override
    protected void initInject(ActivityComponent mainComponent) {
            mainComponent.Injext(this);
    }
    @Override
    protected void addLayout() {
        vr_recycle = findViewById(R.id.vr_recycle);
    }

    /**
     * 展示VR视频数据
     * */
    @Override
    public void Show(VrVideoBean vrVideoBean) {

        List<VrVideoBean.ContentBean> content = vrVideoBean.getContent();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        vr_recycle.setLayoutManager(linearLayoutManager);
        vr_recycle.setAdapter(new VrVideoAdapter(getActivity(),content));
        Log.i("jjj","人炒房");
    }
}
