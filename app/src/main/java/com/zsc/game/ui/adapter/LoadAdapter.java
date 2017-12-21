package com.zsc.game.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zsc.game.R;
import com.zsc.game.mvp.model.bean.ShipinContentInfo;
import com.zsc.game.util.MData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * 类的用途：
 * <p>
 * mac周昇辰
 * 2017/12/21  8:38
 */

public class LoadAdapter extends RecyclerView.Adapter<LoadAdapter.MyViewHolder> {

    private int oo=0;
    private Context context;
    private List<ShipinContentInfo.RetBean> lists;
    public LoadAdapter(Context context, List<ShipinContentInfo.RetBean> lists) {
        this.context = context;
        this.lists = lists;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.load_item,parent,false);
        final MyViewHolder viewHolder=new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.OnItemClick(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Glide.with(context).load(lists.get(position).getPic()).into(holder.image);
        holder.title.setText(lists.get(position).getTitle());
        holder.pb.setProgress(lists.get(position).getProgress());
        Log.i("xxx","更新进度了");

    }




    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image;
        public TextView title;
        public ProgressBar pb;

        public MyViewHolder(View itemView) {
            super(itemView);
           image= itemView.findViewById(R.id.image);
           title= itemView.findViewById(R.id.title);
           pb= itemView.findViewById(R.id.pb);
        }
    }


    private MyOnItemClick onItemClick;
    public void setItemClick(MyOnItemClick onItemClick)
    {
        this.onItemClick=onItemClick;
    }
    public interface MyOnItemClick
    {
        void OnItemClick(int position);
    }
}
