package com.zsc.game.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.mvp.presenter.FaPresenter;
import com.zsc.game.mvp.view.FaView;
import com.zsc.game.ui.activity.SousuoActivity;
import com.zsc.game.ui.adapter.HomeAdapter;
import com.zsc.game.util.SystemUtil;

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
    private int imageHeight = 50; //设置渐变高度，一般为导航图片高度，自己控制
    private XRecyclerView siftXr1;
    private SwipeRefreshLayout idSwipe;
    private HomeAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ImageView goback;
    private LinearLayout gobackLayout;
    private LinearLayout titleBarLayout;
    private TextView settv;
    private TextView titleBarName;
    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayout1;
    private TextView textView;
    private NetworkChangeReciver network;

    @Override
    protected int setLayout() {
        return R.layout.fragmenta;
    }

    @Override
    protected void processLogic() {
        mPresenter.loadata();
        /**
         * 注册广播，进行监听是否有网络
         * */
        IntentFilter intentfile=new IntentFilter();
        intentfile.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        network = new NetworkChangeReciver();
        getActivity().registerReceiver(network, intentfile);
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
    }

    @Override
    protected void addLayout() {
        relativeLayout1 = findViewById(R.id.relative1);
        relativeLayout = findViewById(R.id.relative);
        textView = findViewById(R.id.xiexie);

        siftXr1 = findViewById(R.id.sift_xr);
        titleBarName = findViewById(R.id.title_bar_name);
        settv = findViewById(R.id.settv);
        titleBarLayout = findViewById(R.id.title_bar_layout);
        gobackLayout = findViewById(R.id.gobackLayout);
        goback = findViewById(R.id.goback);
        idSwipe=findViewById(R.id.id_swipe);

        if(!SystemUtil.isNetworkConnected()){
            Log.i("www","宝宝");
                relativeLayout1.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"我点击了哦，宝贝",Toast.LENGTH_SHORT).show();
                        Log.i("www","宝宝111");
                        // 跳转到系统的网络设置界面
                        Intent intent = null;
                        // 先判断当前系统版本
                        if(android.os.Build.VERSION.SDK_INT > 10){  // 3.0以上
                            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        }else{
                            intent = new Intent();
                            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                        }
                        getActivity().startActivity(intent);
                    }
                });
        }
        View abnv = View.inflate(getActivity(), R.layout.layout_bann, null);
        init();
        goback.setVisibility(View.GONE);
        settv.setVisibility(View.GONE);
        mybanner = abnv.findViewById(R.id.mybanner);
        /**
         * 查找头部相对应的控件
         * */
        TextView tv = abnv.findViewById(R.id.edit_sou);
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
        /**
         * 点击跳转搜索框
         * */
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), SousuoActivity.class));
            }
        });
    }
    /**
     * recycleview数据展示
     * */
    private void getData(final List<VideoInfo.RetBean.ListBean.ChildListBean> jcList) {
        siftXr1.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new HomeAdapter(getActivity(),jcList);
        siftXr1.setAdapter(adapter);
    }
    /**
     * 上拉刷新下拉加载
     * */
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

    /**
     * 网络数据获取更新UI
     * */
    @Override
    public void showToast(VideoInfo msg) {
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

    /**
     * 广播内部类
     * */

    class NetworkChangeReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub'
            ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable()) {
                Toast.makeText(context, "当前网络正常！", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(context, "当前网络处于断网！", Toast.LENGTH_SHORT).show();
                Log.i("ttt","我真的很帅");

            }
        }

    }

    @Override
    public void onDestroy() {
        //注销广播
        getActivity().unregisterReceiver(network);
        super.onDestroy();
    }
}
