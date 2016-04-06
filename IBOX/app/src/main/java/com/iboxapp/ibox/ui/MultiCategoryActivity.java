package com.iboxapp.ibox.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.BaseRecyclerViewAdapter;

public class MultiCategoryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private String[] title = {"数码","数码","数码","数码","数码","数码","数码","数码","数码","数码","数码"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_category);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_shuma);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new BaseRecyclerViewAdapter(this, title));
        mRecyclerView.setHasFixedSize(true);
    }
}
