package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.iboxapp.ibox.MainActivity;
import com.iboxapp.ibox.R;
import com.iboxapp.ibox.widget.LocalImageHolderView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MyScrollingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout toolBarLayout;
    private Button mMoreCommentButton;
    private Button mButtonModify;
    private Button mButtonCollect;
    private Button mButtonBuy;
    private Button mAddCommentButton;
    private ConvenientBanner mConvenientBanner;//顶部广告栏控件
    private Button mButtonFollow;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();

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

        mConvenientBanner = (ConvenientBanner) findViewById(R.id.content_box_convenientBanner);
        initBanner();
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
                Snackbar.make(view, "已喜欢，该物品热度增加", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mButtonCollect = (Button) findViewById(R.id.collect_goods_button);
        mButtonCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                String str = mButtonCollect.getText().toString();
                if(str.equals("收藏")){
                    mButtonCollect.setText("已收藏");
                    mButtonCollect.setBackgroundColor(getResources().getColor(R.color.colorButton_unsell));
                    Snackbar.make(v, "已收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    /*Toast toast = Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/
                }else{
                    mButtonCollect.setText("收藏");
                    mButtonCollect.setBackgroundColor(getResources().getColor(R.color.colorTextView_unsell));
                    Snackbar.make(v, "取消收藏", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    /*Toast toast = Toast.makeText(getApplicationContext(), "取消收藏", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/
                }

            }
        });

        mButtonBuy = (Button) findViewById(R.id.add_into_buy__button);
        mButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "buy", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                startActivity(new Intent(MyScrollingActivity.this, ConfirmOrderActivity.class));

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

        mAddCommentButton = (Button) findViewById(R.id.add_comment_button);
        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "add comments", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                startActivity(new Intent(MyScrollingActivity.this, AddCommentActivity.class));

            }
        });

        mButtonFollow = (Button) findViewById(R.id.buy_followed);
        mButtonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mButtonFollow.getText().toString();
                if(str.equals("关注")){
                    mButtonFollow.setText("已关注");
                    mButtonFollow.setBackgroundColor(getResources().getColor(R.color.colorButton_unsell));
                    Snackbar.make(v, "已关注", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    mButtonFollow.setText("关注");
                    mButtonFollow.setBackgroundColor(getResources().getColor(R.color.colorButton_modify_goods));
                    Snackbar.make(v, "取消关注", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        mConvenientBanner = (ConvenientBanner) findViewById(R.id.content_boy_convenientBanner);
        initBanner();
    }

    private void initBanner(){
        initLocalImageLoader();
        //本地图片例子
        mConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
//                .setOnItemClickListener(getActivity());
    }


    private void initLocalImageLoader(){
        //本地图片集合
        for (int position = 1; position < 4; position++)
            localImages.add(getResId("p" + position, R.drawable.class));
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

    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        mConvenientBanner.stopTurning();
    }

    @Override
    public void onResume() {
        super. onResume();
        //开始自动翻页
        mConvenientBanner.startTurning(5000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
