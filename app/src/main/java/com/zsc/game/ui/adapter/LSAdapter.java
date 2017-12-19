package com.zsc.game.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.zsc.game.R;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;

import java.util.List;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/19  11:56
 */

public class LSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int type_0;
    private int type_1;
    private Context context;
    private List<ShipinContentInfo.RetBean> list;

    public LSAdapter(Context context, List<ShipinContentInfo.RetBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        if(viewType==0)
        {
            View view=LayoutInflater.from(context).inflate(R.layout.ls_item,parent,false);
            viewHolder=new MyViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(holder instanceof MyViewHolder)
           {

                  Glide.with(context).load(list.get(position).getPic()).into(((MyViewHolder) holder).image);
                   ((MyViewHolder) holder).tv.setText(list.get(position).getTitle());


           }
    }

    @Override
    public int getItemCount() {
        return (list.size()>=0&&list.size()<=3)?list.size():3;
    }

    @Override
    public int getItemViewType(int position) {
        return type_0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView image;
        public TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            tv=itemView.findViewById(R.id.tv);
        }
    }
}
