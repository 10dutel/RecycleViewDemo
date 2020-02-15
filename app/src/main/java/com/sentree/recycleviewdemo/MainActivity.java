package com.sentree.recycleviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.sentree.recycleviewdemo.adapters.GridViewAdapter;
import com.sentree.recycleviewdemo.adapters.ListViewAdapter;
import com.sentree.recycleviewdemo.adapters.RecyclerViewBaseAdapter;
import com.sentree.recycleviewdemo.adapters.StaggerViewAdapter;
import com.sentree.recycleviewdemo.beans.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Bean> beanList;
    private RecyclerViewBaseAdapter viewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mSwipeRefreshLayout = findViewById(R.id.refreshlayout);
        beanList = new ArrayList<>();
        initDatas();
        //默认显示listview状态
        showList(true,false);

        //刷新
        handleDownPullUpdate();
    }

    private void handleDownPullUpdate() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setColorSchemeColors(Color.YELLOW,Color.CYAN,Color.BLUE,Color.GREEN,Color.RED,Color.MAGENTA);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //1.创建新数据
                Bean bean = new Bean();
                bean.setTitle("这是下拉刷新出的数据");
                bean.setIcon(R.drawable.ic_launcher_background);
                beanList.add(0,bean);
                //2.更新UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //重置数据
                        viewAdapter.notifyDataSetChanged();
                        //停止更新状态
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
    }


    /**
     * 创建模拟数据
     */
    private void initDatas() {
        for (int i=1;i<=Datas.iconId.length;i++){
            Bean bean = new Bean();
            bean.setIcon(Datas.iconId[i-1]);
            bean.setTitle("这是第"+i+"条目");
            beanList.add(bean);
        }
    }

    /**
     * 设置右上角的菜单
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.lvs:
                Log.d("test",id+"");
                showList(true,false);
                break;
            case R.id.lvr:
                Log.d("test",id+"");
                showList(true,true);
                break;
            case R.id.lhs:
                Log.d("test",id+"");
                showList(false,false);
                break;
            case R.id.lhr:
                Log.d("test",id+"");
                showList(false,true);
                break;

            case R.id.gvs:
                Log.d("test",id+"");
                showGrid(GridLayoutManager.VERTICAL,false);
                break;
            case R.id.gvr:
                Log.d("test",id+"");
                showGrid(GridLayoutManager.VERTICAL,true);
                break;
            case R.id.ghs:
                Log.d("test",id+"");
                showGrid(GridLayoutManager.HORIZONTAL,false);
                break;
            case R.id.ghr:
                Log.d("test",id+"");
                showGrid(GridLayoutManager.HORIZONTAL,true);
                break;

            case R.id.svs:
                Log.d("test",id+"");
                showStagger(true,false);
                break;
            case R.id.svr:
                Log.d("test",id+"");
                showStagger(true,true);
                break;
            case R.id.shs:
                Log.d("test",id+"");
                showStagger(false,false);
                break;
            case R.id.shr:
                Log.d("test",id+"");
                showStagger(false,true);
                break;
            case R.id.moretype:
                startActivity(new Intent(MainActivity.this,MoreTypeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showStagger(boolean isVertical, boolean isReverse) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                isVertical?StaggeredGridLayoutManager.VERTICAL:StaggeredGridLayoutManager.HORIZONTAL);
        staggeredGridLayoutManager.setReverseLayout(isReverse);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        viewAdapter = new StaggerViewAdapter(beanList);
        initListener(viewAdapter);
        mRecyclerView.setAdapter(viewAdapter);
    }

    /**
     * 展示listview样式
     */
    private void showList(boolean isVertical,boolean isReverse) {
        //布局管理器控制RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(isVertical? LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setReverseLayout(isReverse);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        viewAdapter = new ListViewAdapter(beanList);
        initListener(viewAdapter);
        mRecyclerView.setAdapter(viewAdapter);
    }

    private void showGrid(int oritention,boolean isReverse) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,oritention,isReverse);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        viewAdapter = new GridViewAdapter(beanList);
        initListener(viewAdapter);
        mRecyclerView.setAdapter(viewAdapter);
    }

    private void initListener(final RecyclerViewBaseAdapter viewAdapter) {
        viewAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });

        if (viewAdapter instanceof ListViewAdapter){
            ((ListViewAdapter) viewAdapter).setOnRefreshListener(new ListViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewAdapter.LoadMoreViewHolder loadMoreViewHolder) {
                    //这里面去加载更多的数据,同样,需要在子线程中完成,这里仅作演示

                    //更新UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Random random = new Random();

                            if (random.nextInt() % 2 == 0) {
                                Bean data = new Bean();
                                data.setTitle("我是新添加的数据...");
                                data.setIcon(R.mipmap.pic_09);
                                beanList.add(data);

                                //这里要做两件事,一件是让刷新停止,另外一件则是要更新列表
                                viewAdapter.notifyDataSetChanged();
                                loadMoreViewHolder.update(ListViewAdapter.LoadMoreViewHolder.LOADER_STATE_NORMAL);
                            } else {
                                loadMoreViewHolder.update(ListViewAdapter.LoadMoreViewHolder.LOADER_STATE_RELOAD);
                            }

                        }
                    }, 3000);
                }
            });
        }
    }
}
