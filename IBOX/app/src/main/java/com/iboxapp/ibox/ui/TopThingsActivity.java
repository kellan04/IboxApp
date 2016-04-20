package com.iboxapp.ibox.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.BaseRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.TopThingsRecyclerViewAdapter;

public class TopThingsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private String[] title = {"top1","top2","top3","top4","top5","top6","top7","top8","top9","top10"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_things);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.show_selection));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=(RecyclerView)findViewById(R.id.recycler_view_top_things);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(new TopThingsRecyclerViewAdapter(this, title));
        mRecyclerView.setHasFixedSize(true);
        //添加头部
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.top_things_recyclerview_header);
        header.attachTo(mRecyclerView, true);
    }
}
