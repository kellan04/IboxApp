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
import com.iboxapp.ibox.adapter.IbuyRecyclerViewAdapter;
import com.iboxapp.ibox.adapter.MessageRecyclerViewAdapter;
import com.iboxapp.ibox.module.Bean;
import com.iboxapp.ibox.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messgae);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.message_title));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.messgae_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        //初始化数据
        final List<Bean> myDataset = new ArrayList<Bean>();
        myDataset.add(new Bean(Bean.Y_TYPE, "user1"));
        myDataset.add(new Bean(Bean.Y_TYPE, "user2"));
        myDataset.add(new Bean(Bean.X_TYPE, "user3"));
        myDataset.add(new Bean(Bean.X_TYPE, "user4"));

        //创建Adapter
        MessageRecyclerViewAdapter mAdapter = new MessageRecyclerViewAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        makeDismiss(mRecyclerView, mAdapter);

        mAdapter.setOnItemClickListener(new MessageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                if (myDataset.get(position).getType() == Bean.X_TYPE) {
                    Intent intent = new Intent(MessageActivity.this, ChatSysActivity.class);
                    intent.putExtra("key", 3);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MessageActivity.this, ChatActivity.class);
                    intent.putExtra("key", 3);
                    startActivity(intent);
                }
            }
        });


    }

    private void makeDismiss(final RecyclerView mRecyclerView, final MessageRecyclerViewAdapter mAdapter) {
        SwipeDismissRecyclerViewTouchListener listener = new SwipeDismissRecyclerViewTouchListener.Builder(
                mRecyclerView,
                new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(View view) {
                        // Do what you want when dismiss

                        int id = mRecyclerView.getChildPosition(view);
                        mAdapter.beans.remove(id);
                        mAdapter.notifyDataSetChanged();

                        Toast.makeText(getBaseContext(), String.format("Delete item %d", id), Toast.LENGTH_LONG).show();

                    }
                })
                .setIsVertical(false)
                .setItemTouchCallback(
                        new SwipeDismissRecyclerViewTouchListener.OnItemTouchCallBack() {
                            @Override
                            public void onTouch(int index) {
                                // Do what you want when item be touched
                                Toast.makeText(getBaseContext(), String.format("touch item %d", index), Toast.LENGTH_LONG).show();
                            }
                        })
                .create();
        mRecyclerView.setOnTouchListener(listener);
    }
}
