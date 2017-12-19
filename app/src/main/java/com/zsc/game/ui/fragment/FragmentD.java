package com.zsc.game.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.mvp.presenter.FdPresenter;
import com.zsc.game.mvp.view.FdView;
import com.zsc.game.ui.adapter.LSAdapter;
import com.zsc.game.util.DaoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentD extends BaseFragment<FdPresenter> implements FdView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.set)
    ImageView set;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.rl_record)
    RelativeLayout rlRecord;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_down)
    TextView tvDown;
    @BindView(R.id.rl_down)
    RelativeLayout rlDown;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.tv_them)
    TextView tvThem;
    @BindView(R.id.rl_them)
    RelativeLayout rlThem;
    Unbinder unbinder;
    private LSAdapter lsAdapter;

    @Override
    protected int setLayout() {
        return R.layout.fragmentd;
    }

    @Override
    protected void processLogic() {
     rlRecord.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Toast.makeText(getContext(), "哈哈哈", Toast.LENGTH_SHORT).show();
         }
     });
     //loadData();

    }

   public void  loadData()
   {
       List<ShipinContentInfo.RetBean> objects = DaoUtils.selectAll();
       Log.i("xxxa","数据"+objects.size());
       if(objects.size()>0)
       {
           recyclerView.setVisibility(View.VISIBLE);
           recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

               lsAdapter = new LSAdapter(getContext(),objects);
               recyclerView.setAdapter(lsAdapter);
               lsAdapter.setMyItemClick(new LSAdapter.MyItemClick() {
                   @Override
                   public void OnItemClick(int position) {
                       Log.i("xxx","position"+position);
                   }
               });
           

       }

   }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {

        mainComponent.Inject(this);
    }

    @Override
    protected void addLayout() {

    }

    @Override
    public void showToast(String msg) {

    }


}
