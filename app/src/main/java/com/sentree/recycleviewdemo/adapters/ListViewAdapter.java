package com.sentree.recycleviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sentree.recycleviewdemo.R;
import com.sentree.recycleviewdemo.beans.Bean;

import java.util.List;

public class ListViewAdapter extends RecyclerViewBaseAdapter {

    //两种类型的view
    private static int TYPE_NORMAL = 0;   //普通控件标识
    private static int TYPE_LOAD = 1;     //上拉刷新用的控件标识
    private OnRefreshListener mRefreshListener;

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()-1){
            //如果是最后一个，填充加载用控件
            return TYPE_LOAD;
        }else {
            return TYPE_NORMAL;
        }
    }

    public ListViewAdapter(List<Bean> data) {
        super(data);
    }


    /**
     * 因为在父类RecyclerViewBaseAdapter中，onBindViewHolder强转了InnerViewHolder，而这里我们还有另一个ViewHolder
     * 所以复写onBindViewHolder
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==TYPE_NORMAL && holder instanceof InnerViewHolder){
            ((RecyclerViewBaseAdapter.InnerViewHolder)holder).setData(mData.get(position),position);
        }else if (getItemViewType(position)==TYPE_LOAD && holder instanceof LoadMoreViewHolder){
            ((LoadMoreViewHolder)holder).update(LoadMoreViewHolder.LOADER_STATE_LOADING);
        }
    }

    //复写
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent,viewType);
        if (viewType == TYPE_NORMAL){
            return new InnerViewHolder(view);
        }else{
            return new LoadMoreViewHolder(view);
        }
    }

    @Override
    public View getSubView(ViewGroup parent,int viewType) {
        //TODO 两种View
        View view;
        if (viewType == TYPE_NORMAL){
            view = View.inflate(parent.getContext(), R.layout.item_list_view,null);
        }else{
            view = View.inflate(parent.getContext(), R.layout.item_refresh_view,null);
        }
        return view;
    }


    public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout linearLayout;
        private TextView textView;

        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.loading);
            textView = itemView.findViewById(R.id.reload);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO "点击重试"的点击事件
                    update(LOADER_STATE_LOADING);
                }
            });
        }

        //在创建新布局的时候用
        public void update(int state){
            //先初始化
            linearLayout.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);

            switch (state){
                case LOADER_STATE_LOADING:
                    linearLayout.setVisibility(View.VISIBLE);
                    startOnLoadMore();
                    break;
                case LOADER_STATE_RELOAD:
                    textView.setVisibility(View.VISIBLE);
                    break;
                case LOADER_STATE_NORMAL:
                    textView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    break;
            }
        }

        private void startOnLoadMore() {
            if (mRefreshListener != null) {
                mRefreshListener.onUpPullRefresh(this);
            }
        }

    }

    /**
     * 设置刷新监听
     */
    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
    }

    public interface OnRefreshListener{
        void onUpPullRefresh(LoadMoreViewHolder loadMoreViewHolder);
    }

}
