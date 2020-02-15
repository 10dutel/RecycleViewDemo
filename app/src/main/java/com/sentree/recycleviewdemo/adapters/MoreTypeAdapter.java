package com.sentree.recycleviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sentree.recycleviewdemo.R;
import com.sentree.recycleviewdemo.beans.TypeBean;

import java.util.List;

public class MoreTypeAdapter extends RecyclerView.Adapter{

    private List<TypeBean> mData;

    private static final int FULL_IMG_TYPE = 0;
    private static final int RIGHT_IMG_TYPE = 1;
    private static final int TRI_IMG_TYPE = 2;

    public MoreTypeAdapter(List<TypeBean> data){
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        //对应三种type
        if (viewType == FULL_IMG_TYPE){
            view = View.inflate(parent.getContext(), R.layout.item_fullimg_view,null);
            return new FullImgViewHolder(view);
        }else if (viewType == RIGHT_IMG_TYPE){
            view = View.inflate(parent.getContext(), R.layout.item_rightimg_view,null);
            return new RightImgViewHolder(view);
        }else {
            view = View.inflate(parent.getContext(), R.layout.item_triimg_view,null);
            return new TriImgViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((InnerViewHolder)holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    //注意，这里是复写的方法
    @Override
    public int getItemViewType(int position) {
        TypeBean typeBean = mData.get(position);
        if (typeBean.getType()==FULL_IMG_TYPE){
            return FULL_IMG_TYPE;
        }else if (typeBean.getType()==RIGHT_IMG_TYPE){
            return RIGHT_IMG_TYPE;
        }else{
            return TRI_IMG_TYPE;
        }
    }

    //===========================
    //TODO 各自对应填充子VIew的方法
    public class FullImgViewHolder extends InnerViewHolder{
        private ImageView iv;
        private TextView tv;
        public FullImgViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_icon);
            tv = itemView.findViewById(R.id.tv_title);
        }

        public void setData(int position){
            iv.setImageResource(mData.get(position).getIcon());
            tv.setText("第"+position+"个标题");
        }
    }

    public class RightImgViewHolder extends InnerViewHolder{
        private ImageView iv;
        private TextView tv;
        public RightImgViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_icon);
            tv = itemView.findViewById(R.id.tv_title);
        }

        public void setData(int position){
            iv.setImageResource(mData.get(position).getIcon());
            tv.setText("第"+position+"个标题");
        }
    }

    public class TriImgViewHolder extends InnerViewHolder{
        private ImageView iv1;
        private ImageView iv2;
        private ImageView iv3;
        private TextView tv;
        public TriImgViewHolder(@NonNull View itemView) {
            super(itemView);
            iv1 = itemView.findViewById(R.id.iv_icon1);
            iv2 = itemView.findViewById(R.id.iv_icon2);
            iv3 = itemView.findViewById(R.id.iv_icon3);
            tv = itemView.findViewById(R.id.tv_title);
        }

        @Override
        public void setData(int position) {
            iv2.setImageResource(mData.get(position).getIcon());

            if (position == 0){
                iv1.setImageResource(mData.get(position+1).getIcon());
                iv3.setImageResource(mData.get(position+2).getIcon());
            }else if (position == mData.size()-1){
                iv1.setImageResource(mData.get(position-1).getIcon());
                iv3.setImageResource(mData.get(position-2).getIcon());
            }else {
                iv1.setImageResource(mData.get(position-1).getIcon());
                iv3.setImageResource(mData.get(position+1).getIcon());
            }
            tv.setText("第"+position+"个标题");
        }
    }

    public abstract class InnerViewHolder extends RecyclerView.ViewHolder{

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void setData(int position);

    }

}
