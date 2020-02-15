package com.sentree.recycleviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sentree.recycleviewdemo.R;
import com.sentree.recycleviewdemo.beans.Bean;

import java.util.List;

/**
 * 配适器里面的逻辑：
 * 1.先通过构造方法创建adapter对象
 * 2.因为配适器要匹配数据与视图（view），所以传入data
 * 3.adapter里有三个方法：
 *      （1）onCreateViewHolder，在这里获得单个条目的view，同时创建viewholder
 *      （2）onBindViewHolder，通过viewholder来进行位置、数据和视图的匹配
 *      （3）getItemCount，条目个数
 * 4.创建viewholder类执行匹配
 * 5.位置对应的数据和视图最后都汇总到了viewholder
 */

/**
 * recyclerview本身没有点击事件，而单个View有
 *重点：OnItemClickListener这个接口的对象是否被创建直接影响了单个View的OnClick方法能否执行
 * 因为onclick会调用这个新建接口对象的方法（而这个方法的具体实现是由baseviewadapter来做的）
 */


public abstract class RecyclerViewBaseAdapter extends RecyclerView.Adapter {
    
    public List<Bean> mData;
    private OnItemClickListener mListener;

    public RecyclerViewBaseAdapter(List<Bean> data){
        this.mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent,viewType);
        return new InnerViewHolder(view);
    }

    public abstract View getSubView(ViewGroup parent,int viewType);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecyclerViewBaseAdapter.InnerViewHolder)holder).setData(mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;
        private int mPosition;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_icon);
            tv = itemView.findViewById(R.id.tv_title);
            //为每一个item设置监听器
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(mPosition);
                    }
                }
            });
        }

        public void setData(Bean bean,int position) {
            iv.setImageResource(bean.getIcon());
            tv.setText(bean.getTitle());
            this.mPosition = position;
        }
    }

    //设置监听器
    public void setOnItemClickListener(OnItemClickListener listener){
        if (listener != null) {
            this.mListener = listener;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
