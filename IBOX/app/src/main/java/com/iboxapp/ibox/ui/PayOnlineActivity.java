package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.iboxapp.ibox.R;

public class PayOnlineActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButtonPayEnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_online);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.pay_online));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonPayEnSure = (Button) findViewById(R.id.pay_online_sure_button);
        mButtonPayEnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                Intent intent = new Intent(PayOnlineActivity.this, OrderInfoUnActivity.class);
                startActivity(intent);

            }
        });
    }
}
