package com.zsc.game.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zsc.game.R;
import com.zsc.game.base.BaseFragment;
import com.zsc.game.di.component.ActivityComponent;
import com.zsc.game.mvp.model.bean.VideoCatagory;
import com.zsc.game.mvp.model.bean.VideoDetail;
import com.zsc.game.mvp.presenter.FcPresenter;
import com.zsc.game.mvp.view.FcView;
import com.zsc.game.ui.activity.VideoDetailActivity;
import com.zsc.game.widget.SwipeCardView;
import com.zsc.game.ui.adapter.SwipeDeckAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/15  18:33
 */

public class FragmentC extends BaseFragment<FcPresenter> implements FcView {


    @BindView(R.id.goback)
    ImageView goback;
    @BindView(R.id.gobackLayout)
    LinearLayout gobackLayout;
    @BindView(R.id.title_bar_name)
    TextView titleBarName;
    @BindView(R.id.settv)
    TextView settv;
    @BindView(R.id.title_bar_layout)
    LinearLayout titleBarLayout;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;
    @BindView(R.id.scw)
    SwipeCardView swipeDeck;
    @BindView(R.id.btn_next)
    Button btnNext;

    private SwipeDeckAdapter adapter;
    private List<VideoCatagory.RetBean.ListBean> list;

    @Override
    protected int setLayout() {
        return R.layout.fragmentc;
    }

    @Override
    protected void processLogic() {
        mPresenter.loadata();
    }

    @Override
    protected void initInject(ActivityComponent mainComponent) {
        mainComponent.Inject(this);
        titleBarLayout.setBackgroundColor(Color.RED);
        goback.setVisibility(View.GONE);
        settv.setVisibility(View.GONE);
        titleBarName.setText("发现");
    }

    @Override
    protected void addLayout() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onSuccess(final VideoCatagory catagory) {
        if (catagory != null) {
            list = catagory.ret.list;
            swipeDeck.setVisibility(View.VISIBLE);
            if (adapter == null) {
                adapter = new SwipeDeckAdapter(list, getContext());
                swipeDeck.setAdapter(adapter);
                swipeDeck.setCardsSlideListener(new SwipeCardView.CardsSlideListener() {
                    @Override
                    public void onShow(int index) {

                    }

                    @Override
                    public void onCardVanish(int index, SwipeCardView.SlideType type) {
                    }

                    @Override
                    public void onItemClick(View cardImageView, int index) {
                        Intent intent = new Intent(getContext(), VideoDetailActivity.class);
                        VideoCatagory.RetBean.ListBean listBean = list.get(index);
                        VideoDetail videoDetail = new VideoDetail(listBean.title,listBean.pic,listBean.dataId,listBean.score,listBean.airTime,listBean.loadURL,listBean.loadtype);
                        intent.putExtra("videoInfo", videoDetail);
                        startActivity(intent);
                        getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                });
            } else {
                adapter.notifyDataChange(list);
                swipeDeck.notifyDatasetChanged(adapter);
            }
        }
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        mPresenter.loadata();
    }
}
