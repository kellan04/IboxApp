package com.iboxapp.ibox.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.iboxapp.ibox.R;

public class OrderInfoUnActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info_un);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.order_info));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
