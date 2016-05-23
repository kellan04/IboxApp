package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.iboxapp.ibox.MainActivity;
import com.iboxapp.ibox.R;

public class OrderInfoUnActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButtonAppraise;
    private Button mButtonLogisticlnfo;
    private Button mButtonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_un);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.order_info));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonAppraise = (Button) findViewById(R.id.activity_order_info_un_appraise);
        mButtonAppraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInfoUnActivity.this, GoodsAppraiseActivity.class);
                startActivity(intent);
            }
        });


        mButtonLogisticlnfo = (Button) findViewById(R.id.activity_order_info_un_logisticlnfo);
        mButtonLogisticlnfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInfoUnActivity.this, LogisticInfoActivity.class);
                startActivity(intent);
            }
        });

        mButtonReturn = (Button) findViewById(R.id.activity_order_info_un_return_goods);
        mButtonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInfoUnActivity.this, GoodsReturnActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 监听Back键按下事件,方法1:
     * 注意:
     * super.onBackPressed()会自动调用finish()方法,关闭
     * 当前Activity.
     * 若要屏蔽Back键盘,注释该行代码即可
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OrderInfoUnActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
