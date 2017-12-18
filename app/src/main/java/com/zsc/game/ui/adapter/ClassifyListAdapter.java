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
import com.zsc.game.mvp.model.bean.JavaBean;

import java.util.List;

/**
 * 类的作用：
 * lenovo 刘珂珂
 * 2017/12/18
 * 13:49
 */

public class ClassifyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<JavaBean.RetBean.ListBean.ChildListBean> list;
    private OnItemClickListener mOnItemClickListener;

    public ClassifyListAdapter(Context context, List<JavaBean.RetBean.ListBean.ChildListBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.classify_item, parent,false));

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view);
            }
        });
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        Glide.with(context).load(list.get(position).getPic()).into((myHolder.ivClassify));
        if (list.get(position).getTitle()!=null)
            myHolder.tvClassyfy.setText(list.get(position).getTitle());
        else
            myHolder.tvClassyfy.setText("暂无数据");
    }

    @Override
    public int getItemCount() {
        return list.size()>0?list.size():0;
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private ImageView ivClassify;
        private TextView tvClassyfy;

        public MyHolder(View itemView) {
            super(itemView);
            ivClassify = itemView.findViewById(R.id.iv_classify);
            tvClassyfy = itemView.findViewById(R.id.tv_classify);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }


}
