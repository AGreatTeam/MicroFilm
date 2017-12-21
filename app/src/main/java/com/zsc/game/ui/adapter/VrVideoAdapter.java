package com.zsc.game.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zsc.game.R;
import com.zsc.game.mvp.model.bean.VrVideoBean;
import com.zsc.game.ui.activity.VrVideoDetailActivity;

import java.util.List;

/**
 * Created by 1 on 2017/12/16.
 */

public class VrVideoAdapter extends RecyclerView.Adapter<VrVideoAdapter.MyViewHolder>  {


        private  Context mContext;
        private    List<VrVideoBean.ContentBean> content;

    public VrVideoAdapter(Context mContext, List<VrVideoBean.ContentBean> content) {
            this.mContext = mContext;
            this.content = content;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    mContext).inflate(R.layout.vritem, parent,
                    false));
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv.setText(content.get(position).getTitle());
        Log.i("hhh","w我就在及");
        Glide.with(mContext).load(content.get(position).getImg()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sss","我点击了1 ");
                String dataId = content.get(position).getPlay();
                //Intent intent = new Intent();
                Log.i("sss","我点击了 ");
               Intent intent = new Intent(mContext, VrVideoDetailActivity.class);
                intent.putExtra("idg",dataId);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return content.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.vr_image);
            tv = view.findViewById(R.id.vr_item);
        }
    }

    /**
     * 接口回调   点击事件
     * */
    private OnitemOnclick onitemOnclick;

    public interface OnitemOnclick{
        void getItem(int position);
    }

    public void setOnitemOnclick(OnitemOnclick onitemOnclick) {
        this.onitemOnclick = onitemOnclick;
    }
}
