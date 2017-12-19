package com.zsc.game.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.JavaBean;
import com.zsc.game.mvp.presenter.FbPresenter;
import com.zsc.game.mvp.view.FbView;
import com.zsc.game.ui.activity.ClassifyListActivity;
import com.zsc.game.ui.adapter.FragmentBAdapter;

import java.util.List;

import butterknife.Unbinder;

/**
 * 类的用途：专题
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentB extends BaseFragment<FbPresenter> implements FbView {

    private static String TAG = "FragmentB";


   // @BindView(R.id.ry_view)
    RecyclerView ryView;
  //  @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Unbinder unbinder;

    @Override
    protected int setLayout() {
        return R.layout.fragmentb;
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
            ryView = findViewById(R.id.ry_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    public void showToast(String msg) {
        Gson gson = new Gson();
        JavaBean bean = gson.fromJson(msg,JavaBean.class);
        final List<JavaBean.RetBean.ListBean> list = bean.getRet().getList();
        ryView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        FragmentBAdapter adapter = new FragmentBAdapter(getActivity(),list);
        ryView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter.setOnItemClickListener(new FragmentBAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int childAdapterPosition = ryView.getChildAdapterPosition(view);
                Toast.makeText(getActivity(), "item click index = "+childAdapterPosition, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ClassifyListActivity.class);
                intent.putExtra("index",childAdapterPosition);
                intent.putExtra("title",list.get(childAdapterPosition).getTitle());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        if(unbinder != null){
            unbinder.unbind();
        }
    }

}
