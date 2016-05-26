package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.MultiCategoryMoreRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.MultiCategoryRecyclerViewAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MultiCategoryMoreActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private MultiCategoryMoreRecyclerViewAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler handler = new Handler();

    private int value = R.string.app_name;
    private ArrayList<String> mDatas = new ArrayList<>();
    private ArrayList<Integer> mDatasImg = new ArrayList<Integer>();
    private boolean isLoading;
    private final String TAG = "MultiCategory";
    private int num = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_category_more);

        //接收时
        if (getIntent() != null) {
            value = getIntent().getIntExtra("mButton", 0);
        }

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(value));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_multi_category_more);
        // 网格布局管理器
        mGridLayoutManager = new GridLayoutManager(this, 2);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new MultiCategoryMoreRecyclerViewAdapter(this, mDatas, mDatasImg);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.multi_category_more_swipeRefreshLayout);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d(TAG, "post()");
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        mDatasImg.clear();
                        getData();
                        initDatasImg();
                    }
                }, 2000);
                Log.d(TAG, "onRefresh()");
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            /**
             * 当RecyclerView的滑动状态改变时触发
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);
            }

            /**
             * 当RecyclerView滑动时触发
             * 类似点击事件的MotionEvent.ACTION_MOVE
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                initDatasImg();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
        mAdapter.setOnItemClickListener(new MultiCategoryMoreRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MultiCategoryMoreActivity.this, MyScrollingActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }

        });
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initData();
    }

    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                initDatasImg();
            }
        }, 1500);

    }

    /**
     * 获取测试数据
     */
    private void getData() {
        mDatas.add("蓝牙耳机");
        mDatas.add("iphone");
        mDatas.add("外设音响");
        mDatas.add("耳机");
        /*for (int i = 0; i < this.num; i++) {;
            mDatas.add("user");
        }*/
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    public void initDatasImg() {
        int orders = 9;
        for (int position = 1; position <= this.num; position++)
            mDatasImg.add(getResId("ic_test_things_" + orders + "_" + position, R.drawable.class));
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
