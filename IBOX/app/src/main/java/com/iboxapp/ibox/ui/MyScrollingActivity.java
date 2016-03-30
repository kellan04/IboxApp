package com.iboxapp.ibox.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iboxapp.ibox.R;

public class MyScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int key = 0;
        //接收时
        if (getIntent() != null) {
            key = getIntent().getIntExtra("key", 0);
        }
        if (key == 1) {
            initBox();
        } else if (key == 3) {
            initBuy();
        }


    }

    private void initBox() {
        setContentView(R.layout.activity_box_scrolling);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.box_scrolling_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.box_toolbar_layout);
        toolBarLayout.setTitle(getResources().getString(R.string.goods_title));
    }

    private void initBuy() {
        setContentView(R.layout.activity_buy_scrolling);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.buy_scrolling_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.buy_toolbar_layout);
        toolBarLayout.setTitle(getResources().getString(R.string.goods_title));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_buy);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
