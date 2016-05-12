package com.iboxapp.ibox.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.BaseRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.IshowRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.MultiCategoryRecyclerViewAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MultiCategoryActivity extends AppCompatActivity {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private View mView;
    private MultiCategoryRecyclerViewAdapter mAdapter;
    private LinearLayout mLinearLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private com.iboxapp.ibox.widget.ImageTextButton mButton1,mButton2,mButton3,mButton4,mButton5,mButton6,mButton7,mButton8;
    private Handler handler = new Handler();

    private ArrayList<String> mDatas = new ArrayList<>();
    private ArrayList<Integer> mDatasImg = new ArrayList<Integer>();
    private boolean isLoading;
    private final String TAG = "MultiCategory";
    private int value = R.string.app_name;
    private int key = 8;
    private int num = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_category);

        //接收时
        if (getIntent() != null) {
            key = getIntent().getIntExtra("mButton", 0);
        }
        if (key == 0) {
            value = R.string.show_find_woman_wear;
            num = 2;
        } else if (key == 1) {
            value = R.string.show_find_woman_shoes;
            num = 2;
        } else if (key == 2) {
            value = R.string.show_find_makeup;
            num = 4;
        }else if (key == 3) {
            value = R.string.show_find_man_wear;
            num = 2;
        } else if (key == 4) {
            value = R.string.show_find_man_shoes;
            num = 2;
        } else if (key == 5) {
            value = R.string.show_find_bag;
            num = 3;
        } else if (key == 6) {
            value = R.string.show_find_jewelry;
            num = 4;
        } else if (key == 7) {
            value = R.string.show_find_sport;
            num = 3;
        } else if (key == 8) {
            value = R.string.show_find_digital;
            num = 4;
        } else if (key == 9) {
            value = R.string.show_find_food;
            num = 3;
        } else if (key == 10) {
            value = R.string.show_find_life;
            num = 3;
        } else if (key == 11) {
            value = R.string.show_find_furniture;
            num = 3;
        }

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(value));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_multi_category);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MultiCategoryRecyclerViewAdapter(this, mDatas, mDatasImg);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        //添加头部
        /*RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.category_recyclerview_header);
        header.attachTo(mRecyclerView, true);*/
        View mView = LayoutInflater.from(this).inflate(R.layout.multi_category_header, mRecyclerView, false);
        mAdapter.addHeader(mView);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.multi_category_swipeRefreshLayout);

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

                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
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
        mAdapter.setOnItemClickListener(new MultiCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MultiCategoryActivity.this, MyScrollingActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }

        });
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initData();
        initButton(mView);
    }

    public void initButton(View mLinearLayoutButton) {
        mButton1 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button);
        mButton2 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button2);
        mButton3 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button3);
        mButton4 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button4);
        mButton5 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button5);
        mButton6 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button6);
        mButton7 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button7);
        mButton8 = (com.iboxapp.ibox.widget.ImageTextButton) mLinearLayoutButton.findViewById(R.id.button8);

        mView = getWindow().getDecorView();
        mView.post(new Runnable() {

            @Override
            public void run() {
                // TODO 自动生成的方法存根
                mButton1.setImgResource(R.drawable.ic_find_digital_1);
                mButton1.setText("手机壳");
                mButton2.setImgResource(R.drawable.ic_find_digital_2);
                mButton2.setText("耳机");
                mButton3.setImgResource(R.drawable.ic_find_digital_3);
                mButton3.setText("手机");
                mButton4.setImgResource(R.drawable.ic_find_digital_4);
                mButton4.setText("电脑");
                mButton5.setImgResource(R.drawable.ic_find_digital_5);
                mButton5.setText("移动电源");
                mButton6.setImgResource(R.drawable.ic_find_digital_6);
                mButton6.setText("相机");
                mButton7.setImgResource(R.drawable.ic_find_digital_7);
                mButton7.setText("键盘");
                mButton8.setImgResource(R.drawable.ic_find_digital_8);
                mButton8.setText("更多分类");
            }
        });
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
        for (int i = 0; i < this.num; i++) {;
            mDatas.add("user");
            Log.d(TAG, "getData()");
        }
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
    }

    public void initDatasImg() {
        int orders = key + 1;
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
}
