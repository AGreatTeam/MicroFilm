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
import com.zsc.game.mvp.model.bean.VideoInfo;
import com.zsc.game.ui.activity.Main3Activity;

import java.util.List;

/**
 * Created by 1 on 2017/12/16.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>  {


    Context mContext;
    private  List<VideoInfo.RetBean.ListBean.ChildListBean> jcList;

    public HomeAdapter(Context mContext, List<VideoInfo.RetBean.ListBean.ChildListBean> jcList) {
        this.mContext = mContext;
        this.jcList = jcList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_home, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv.setText(jcList.get(position).getTitle());
        Log.i("hhh","w我就在及");
        Glide.with(mContext).load(jcList.get(position).getPic()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("sss","我点击了1 ");
                String dataId = jcList.get(position).getDataId();
                //Intent intent = new Intent();
                Log.i("sss","我点击了 ");
                Intent intent = new Intent(mContext, Main3Activity.class);
                intent.putExtra("id",dataId);
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return jcList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.item_simp);
            tv = view.findViewById(R.id.item_text1);
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
