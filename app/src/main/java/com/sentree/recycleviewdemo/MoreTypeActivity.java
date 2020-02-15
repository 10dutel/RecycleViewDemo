package com.sentree.recycleviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.sentree.recycleviewdemo.adapters.MoreTypeAdapter;
import com.sentree.recycleviewdemo.beans.TypeBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<TypeBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_type);
        recyclerView = findViewById(R.id.recyclerview2);

        initData();

        //创建adapter
        MoreTypeAdapter moreTypeAdapter = new MoreTypeAdapter(mList);
        //创建LayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false);
        //配置
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(moreTypeAdapter);
    }

    private void initData() {
        mList = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<Datas.iconId.length;i++){
            TypeBean typeBean = new TypeBean();
            typeBean.setIcon(Datas.iconId[i]);
            int k = random.nextInt(3);
            typeBean.setType(k);
            Log.e("*-*-*-*-",k+"");
            mList.add(typeBean);
        }
    }
}
