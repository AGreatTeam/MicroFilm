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
import com.zsc.game.VR.ImageItem;
import com.zsc.game.ui.activity.VRpicMusicActivity;

import java.util.List;

/**
 * Created by 1 on 2017/12/16.
 */

public class VrPicAdapter extends RecyclerView.Adapter<VrPicAdapter.MyViewHolder>  {


    private  Context mContext;
    private List<ImageItem> list;

    public VrPicAdapter(Context mContext, List<ImageItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.vr_pic_music, parent,
                false));
        return holder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv.setText(list.get(position).getName());
        Log.i("hhh","w我就在及");
        Glide.with(mContext).load(list.get(position).getPicAddress()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageItem imageItem = list.get(position);
                Intent intent = new Intent(mContext, VRpicMusicActivity.class);
                intent.putExtra("pic",imageItem);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.vr_image_music);
            tv = view.findViewById(R.id.vr_item_music);
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
