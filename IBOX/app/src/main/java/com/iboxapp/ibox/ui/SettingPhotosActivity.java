package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.BaseRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.CollectRecyclerViewAdapter;

public class SettingPhotosActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BaseRecyclerViewAdapter mAdapter;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_photos);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.navigation_photos));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_photos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        //创建Adapter
        mAdapter = new BaseRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        /*mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SettingPhotosActivity.this, MyScrollingActivity.class);
                intent.putExtra("key", 3);
                startActivity(intent);
            }
        });*/
    }
}
