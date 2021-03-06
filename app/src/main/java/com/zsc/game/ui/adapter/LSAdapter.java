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

    private int zz=0;
    private int type_0;
    private int type_1;
    private Context context;
    private List<ShipinContentInfo.RetBean> list;

    public LSAdapter(Context context, List<ShipinContentInfo.RetBean> list,int zz) {
        this.context = context;
        this.zz=zz;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType==0)
        {
            View view=LayoutInflater.from(context).inflate(R.layout.ls_item,parent,false);

            final RecyclerView.ViewHolder viewHolder=new MyViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.OnItemClick(viewHolder.getAdapterPosition());
                }
            });
            return viewHolder;
        }

        return null;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           if(holder instanceof MyViewHolder)
           {
               if(zz!=0)
               {
                   ViewGroup.LayoutParams params=  ((MyViewHolder) holder).image.getLayoutParams();
                   params.width=330;
                   params.height=260;
                   ((MyViewHolder) holder).image.setLayoutParams(params);
               }

                  Glide.with(context).load(list.get(list.size()-(position+1)).getPic()).into(((MyViewHolder) holder).image);
                   ((MyViewHolder) holder).tv.setText(list.get(list.size()-(position+1)).getTitle());
           }
    }

    @Override
    public int getItemCount() {
           if(zz==0)
           {
               return (list.size()>=0&&list.size()<=3)?list.size():3;
           }else
           {
               return  list.size();
           }

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

    private MyItemClick click;
    public void setMyItemClick(MyItemClick click)
    {
        this.click=click;
    }
    public interface MyItemClick
    {
        void OnItemClick(int position);

    }
}
