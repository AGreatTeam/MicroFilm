package com.zsc.game.ui.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zsc.game.R;
import com.zsc.game.base.BaseActivity;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.JavaBean;
import com.zsc.game.mvp.presenter.MainPresenter;
import com.zsc.game.mvp.view.MainView;
import com.zsc.game.ui.adapter.ClassifyListAdapter;
import com.zsc.game.ui.adapter.FragmentBAdapter;

import java.util.List;

import butterknife.BindView;

public class ClassifyListActivity extends BaseActivity<MainPresenter> implements MainView {

    @BindView(R.id.ry_view)
    RecyclerView ryView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private int index;

    @Override
    public void showToast(String msg) {
        Gson gson = new Gson();
        JavaBean bean = gson.fromJson(msg,JavaBean.class);
        List<JavaBean.RetBean.ListBean.ChildListBean> list = bean.getRet().getList().get(index).getChildList();
        ClassifyListAdapter adapter = new ClassifyListAdapter(this,list);
        ryView.setLayoutManager(new GridLayoutManager(this,3));
        ryView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter.setOnItemClickListener(new ClassifyListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int childAdapterPosition = ryView.getChildAdapterPosition(view);
                Toast.makeText(ClassifyListActivity.this, "item click index = "+childAdapterPosition, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setLayout() {
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        Toast.makeText(this, "index:" + index, Toast.LENGTH_SHORT).show();
        return R.layout.activity_classify_list;
    }

    @Override
    protected void processLogic() {
        mPresenter.loadata();
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
    }
}
