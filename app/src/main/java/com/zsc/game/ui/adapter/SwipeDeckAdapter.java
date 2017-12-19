package com.zsc.game.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zsc.game.R;
import com.zsc.game.mvp.model.bean.VideoCatagory;
import com.zsc.game.widget.BaseCardAdapter;

import java.util.List;

/**
 * Created by 苏照亮 2017/12/18.
 */

public class SwipeDeckAdapter extends BaseCardAdapter {

    private List<VideoCatagory.RetBean.ListBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SwipeDeckAdapter(List<VideoCatagory.RetBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyDataChange(List<VideoCatagory.RetBean.ListBean> data){
        this.list.clear();
        this.list = data;
    }

    @Override
    public List getData() {
        return list;
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.card_item;
    }
    public void setOnClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public void onBindData(final int position, View cardview, Object data) {
        TextView tv_title = (TextView) cardview.findViewById(R.id.tv_title);
        TextView offer_image = (TextView) cardview.findViewById(R.id.tv_introduction);
        ImageView img = (ImageView) cardview.findViewById(R.id.offer_image);
        Glide.with(context).load(list.get(position).pic).into(img);
        String intro = "\t\t\t" + list.get(position).description;
        offer_image.setText(intro);
        tv_title.setText(list.get(position).title);

        if (onItemClickListener != null) {
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickListener(v, position);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClickListener(View view, int position);
    }

}
