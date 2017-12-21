package com.zsc.game.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.zsc.game.R;
import com.zsc.game.base.BaseApplication;
import com.zsc.game.greendao.LoadInfo;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.service.DownLoadService;
import com.zsc.game.ui.adapter.LoadAdapter;
import com.zsc.game.util.DaoUtils;
import com.zsc.game.util.DownLoadUtil;
import com.zsc.game.util.MData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/20  18:46
 */

public class LoadActivity extends AppCompatActivity {

    private ProgressBar pb;
    private RecyclerView recyclerView;
    private List<ShipinContentInfo.RetBean> list;
    private LoadAdapter loadAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_activity);
        EventBus.getDefault().register(this);

        recyclerView = findViewById(R.id.loadrecy);
        list = DaoUtils.selectAll();
        loadAdapter = new LoadAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(loadAdapter);

        loadAdapter.setItemClick(new LoadAdapter.MyOnItemClick() {
            @Override
            public void OnItemClick(int position) {



             String url="http://pic.ibaotu.com/00/40/40/88F888piC3mI.mp4_10s.mp4";
                DownLoadUtil.isPause=false;
                LoadInfo info=new LoadInfo((long)10,url,0,0,0);
                Intent intent=new Intent(LoadActivity.this, DownLoadService.class);
                intent.putExtra("info",info);
                startService(intent);
            }
        });



    }

    @Subscribe
    public void zz(MData data)
    {
        int mzz=Integer.parseInt(data.getFinished().toString());
        int   id=data.getId();
        Log.i("xxx","拿到进度"+mzz+"标识"+id);
        list.get(id).setProgress(mzz);
        loadAdapter.notifyItemChanged(id);
       //loadAdapter.notifyDataSetChanged();



    }
    public void but(View view)
    {

       // EventBus.getDefault().post(new MData(0, (long) 10));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       EventBus.getDefault().unregister(this);
    }
}
