package com.zsc.game.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.zsc.game.R;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.ui.adapter.LSAdapter;
import com.zsc.game.util.DaoUtils;

import java.util.List;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/19  20:44
 */

public class LSActivity  extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LSAdapter lsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              setContentView(R.layout.ls_activity);
             recyclerView = findViewById(R.id.mrecycleview);
            List<ShipinContentInfo.RetBean> objects = DaoUtils.selectAll();
             Log.i("xxxa","数据"+objects.size());
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));

            lsAdapter = new LSAdapter(this,objects,1);
            recyclerView.setAdapter(lsAdapter);
            lsAdapter.setMyItemClick(new LSAdapter.MyItemClick() {
                @Override
                public void OnItemClick(int position) {
                    Log.i("xxx","position"+position);
                }
            });


    }
}
