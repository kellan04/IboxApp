package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iboxapp.ibox.R;

public class OrderInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButtonDelete;
    private android.widget.Button mButtonLogisticlnfo;
    private Button mButtonAddToIbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.order_info));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonDelete = (Button) findViewById(R.id.activity_order_info_delete);
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                Intent intent = getIntent();
                //通过Intent对象返回结果，调用setResult方法
                setResult(2, intent);
                delay(3000);
                finish();//结束当前的activity的生命周期
            }
        });


        mButtonLogisticlnfo = (Button) findViewById(R.id.activity_order_info_logisticlnfo);
        mButtonLogisticlnfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderInfoActivity.this, LogisticInfoActivity.class);
                startActivity(intent);
            }
        });

        mButtonAddToIbox = (Button) findViewById(R.id.activity_order_info_addto_ibox);
        mButtonAddToIbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "成功加入ibox", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
