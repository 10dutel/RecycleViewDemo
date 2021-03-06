package com.sentree.recycleviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;


import com.sentree.recycleviewdemo.R;
import com.sentree.recycleviewdemo.beans.Bean;

import java.util.List;

public class GridViewAdapter extends RecyclerViewBaseAdapter {

    public GridViewAdapter(List<Bean> data) {
        super(data);
    }

    @Override
    public View getSubView(ViewGroup parent,int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_grid_view,null);
        return view;
    }
}
