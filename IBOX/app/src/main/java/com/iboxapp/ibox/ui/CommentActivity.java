package com.iboxapp.ibox.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.adapter.CommentRecyclerViewAdapter;
import com.iboxapp.ibox.tool.Bean;
import com.iboxapp.ibox.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.github.codefalling.recyclerviewswipedismiss.SwipeDismissRecyclerViewTouchListener;

public class CommentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        // initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        toolbar = (Toolbar) findViewById(R.id.back_toolbar);
        toolbar.setTitle("评论");
        setSupportActionBar(toolbar);

        //初始化数据
        List<Bean> myDataset = new ArrayList<Bean>();
        myDataset.add(new Bean(Bean.X_TYPE, "评论1"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论2"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论3"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论4"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论5"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论6"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论7"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论8"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论9"));
        myDataset.add(new Bean(Bean.X_TYPE, "评论10"));

        //创建Adapter
        CommentRecyclerViewAdapter mAdapter = new CommentRecyclerViewAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        makeDismiss(recyclerView, mAdapter);
    }

    private void makeDismiss(final RecyclerView mRecyclerView, final CommentRecyclerViewAdapter mAdapter) {
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
