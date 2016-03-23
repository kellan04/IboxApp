package com.iboxapp.ibox.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.MessageRecyclerViewAdapter;
import com.iboxapp.ibox.tool.Bean;

import java.util.ArrayList;
import java.util.List;
import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messgae);

        // initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);



        //初始化数据
        List<Bean> myDataset = new ArrayList<Bean>();

        myDataset.add(new Bean(Bean.X_TYPE, "user1"));
        myDataset.add(new Bean(Bean.X_TYPE, "user2"));
        myDataset.add(new Bean(Bean.X_TYPE, "user3"));
        myDataset.add(new Bean(Bean.X_TYPE, "user4"));
        myDataset.add(new Bean(Bean.X_TYPE, "user5"));
        myDataset.add(new Bean(Bean.X_TYPE, "user6"));
        myDataset.add(new Bean(Bean.X_TYPE, "user7"));
        myDataset.add(new Bean(Bean.X_TYPE, "user8"));
        myDataset.add(new Bean(Bean.X_TYPE, "user9"));
        myDataset.add(new Bean(Bean.X_TYPE, "user10"));
        //创建Adapter
        MessageRecyclerViewAdapter mAdapter = new MessageRecyclerViewAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        makeDismiss(mRecyclerView, mAdapter);
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
