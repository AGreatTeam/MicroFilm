package com.zsc.game.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.mvp.presenter.FaPresenter;
import com.zsc.game.mvp.view.FaView;
import com.zsc.game.ui.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentA extends BaseFragment<FaPresenter> implements FaView, SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    private XBanner mybanner;
    private int imageHeight = 100; //设置渐变高度，一般为导航图片高度，自己控制
    private XRecyclerView siftXr1;
    private SwipeRefreshLayout idSwipe;
    private HomeAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ImageView goback;
    private LinearLayout gobackLayout;
    private LinearLayout titleBarLayout;
    private TextView settv;
    private TextView titleBarName;

    @Override
    protected int setLayout() {
        return R.layout.fragmenta;
    }

    @Override
    protected void processLogic() {
        mPresenter.loadata();
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
    }

    @Override
    protected void addLayout() {
        siftXr1 = (XRecyclerView)findViewById(R.id.sift_xr);
        titleBarName = (TextView)findViewById(R.id.title_bar_name);
        settv = (TextView)findViewById(R.id.settv);
        titleBarLayout = (LinearLayout)findViewById(R.id.title_bar_layout);
        gobackLayout = (LinearLayout)findViewById(R.id.gobackLayout);
        goback = (ImageView)findViewById(R.id.goback);
        idSwipe=(SwipeRefreshLayout)findViewById(R.id.id_swipe);
        View abnv = View.inflate(getActivity(), R.layout.layout_bann, null);
        init();
        goback.setVisibility(View.GONE);
        settv.setVisibility(View.GONE);
        mybanner = (XBanner) abnv.findViewById(R.id.mybanner);
        titleBarLayout.bringToFront();
        siftXr1.addHeaderView(abnv);
        siftXr1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int y = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                y+=dy;
                if (y <= 0) {
                    titleBarName.setText("精选");
                    titleBarLayout.setBackgroundColor(Color.argb((int) 0,227,29,26));
                    titleBarName.setTextColor(Color.argb(0, 255, 255, 255));
                } else if (y > 0 && y <= imageHeight) {
                    float scale = (float) y / imageHeight;
                    float alpha = (255 * scale);
                    // 只是layout背景透明
                    titleBarName.setText("精选");
                    titleBarLayout.setBackgroundColor(Color.argb((int) alpha,227,29,26));
                    titleBarName.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                } else {
                    titleBarName.setText("精选");
                    titleBarLayout.setBackgroundColor(Color.argb((int) 255,227,29,26));
                    titleBarName.setTextColor(Color.argb((int) 225, 255, 255, 255));
                }

            }
        });
        idSwipe.setOnRefreshListener(this);
    }
    private void getData(final List<VideoInfo.RetBean.ListBean.ChildListBean> jcList) {
        for (int i = 0; i < 50; i++) {
            list.add(i + "");
        }
        siftXr1.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new HomeAdapter(getActivity(),jcList);
        siftXr1.setAdapter(adapter);
    }
    private void init( ) {
        siftXr1.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                siftXr1.refreshComplete();
            }
            @Override
            public void onLoadMore() {
                siftXr1.loadMoreComplete();
            }
        });
    }
    //重写方法进行展示网络数据
    @Override
    public void showToast(VideoInfo msg) {
//        List<VideoInfo.RetBean.ListBean> list = msg.getRet().getList();
        List<VideoInfo.RetBean.ListBean.ChildListBean> childList = msg.getRet().getList().get(0).getChildList();
        final List<String> xbanimg = new ArrayList<>();
        for (int i = 0; i < childList.size(); i++) {
            if(i!=3){
                xbanimg.add(childList.get(i).getPic());
            }
        }
        mybanner.setData(xbanimg, null);
        mybanner.setmAutoPalyTime(2500);
        mybanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getActivity()).load(xbanimg.get(position)).into((ImageView) view);
            }
        });

        //获取精彩数据
        VideoInfo.RetBean.ListBean listBean = msg.getRet().getList().get(4);
        List<VideoInfo.RetBean.ListBean.ChildListBean> jcList = listBean.getChildList();

        getData(jcList);
        Log.i("ggg",listBean.getTitle());
    }
    @Override
    public void onRefresh() {
        idSwipe.setRefreshing(false);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(unbinder != null){
            unbinder.unbind();
        }
    }
}
