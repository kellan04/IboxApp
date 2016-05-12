package com.iboxapp.ibox.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iboxapp.ibox.IshowFragment;
import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.FindRecyclerViewAdapter;
import com.iboxapp.ibox.widget.GridSpacingItemDecoration;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FindActivity extends AppCompatActivity {

    private Button mButton;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private FindRecyclerViewAdapter mAdapter;

    private static final String ACTIVITY_TAG="LogDemo";
    private String[] mDatasText={"女装","女鞋","美妆","男装","男鞋","箱包","饰品","户外/运动","数码","美食","日常百货","家具装饰"};
    private ArrayList<Integer> mDatasImg = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.show_find));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_find_recycler_view);
        // 网格布局管理器
        mGridLayoutManager = new GridLayoutManager(this, 3);
        mGridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new FindRecyclerViewAdapter(this,mDatasText,mDatasImg);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FindRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(FindActivity.this, MultiCategoryActivity.class);
                intent.putExtra("mButton", position);
                startActivity(intent);
            }
        });
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int spanCount = 3;
        int spacing = 30;
        boolean includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        initDatasImg();
        /*initButton();


        Log.i(FindActivity.ACTIVITY_TAG, "This is Information2");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FindActivity.ACTIVITY_TAG, "This is Information3");
                Intent intent = new Intent(FindActivity.this, MultiCategoryActivity.class);
                startActivity(intent);
            }
        });*/
    }

    public void initDatasImg() {
        for (int position = 1; position <= 12; position++)
            mDatasImg.add(getResId("ic_button_" + position, R.drawable.class));
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

    /*public void initButton() {
        mButton = (Button) findViewById(R.id.Button9);
    }*/


}
