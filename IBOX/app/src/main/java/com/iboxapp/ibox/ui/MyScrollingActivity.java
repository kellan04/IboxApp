package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iboxapp.ibox.MainActivity;
import com.iboxapp.ibox.R;

public class MyScrollingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolBarLayout;
    private Button mMoreCommentButton;
    private Button mButtonModify;

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
        mToolbar = (Toolbar) findViewById(R.id.box_scrolling_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.box_toolbar_layout);
        toolBarLayout.setTitle(getResources().getString(R.string.goods_title));
        mMoreCommentButton = (Button) findViewById(R.id.more_comment_button);
        mMoreCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "more comments", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                startActivity(new Intent(MyScrollingActivity.this, CommentActivity.class));

            }
        });

        mButtonModify = (Button) findViewById(R.id.modify_goods_button);
        mButtonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MyScrollingActivity.this, EditThingsActivity.class);
                intent.putExtra("key", 1);
                startActivity(intent);
            }
        });
    }

    private void initBuy() {
        setContentView(R.layout.activity_buy_scrolling);
        mToolbar = (Toolbar) findViewById(R.id.buy_scrolling_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.buy_toolbar_layout);
        toolBarLayout.setTitle(getResources().getString(R.string.goods_title));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_buy);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mMoreCommentButton = (Button) findViewById(R.id.more_comment_button);
        mMoreCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "more comments", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                startActivity(new Intent(MyScrollingActivity.this, CommentActivity.class));

            }
        });
    }
}
