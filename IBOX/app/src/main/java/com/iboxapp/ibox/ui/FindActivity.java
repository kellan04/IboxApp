package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iboxapp.ibox.IshowFragment;
import com.iboxapp.ibox.R;

public class FindActivity extends AppCompatActivity {

    private Button mButton;
    private static final String ACTIVITY_TAG="LogDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Log.i(FindActivity.ACTIVITY_TAG, "This is Information1");
        mButton = (Button) findViewById(R.id.Button9);
        Log.i(FindActivity.ACTIVITY_TAG, "This is Information2");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(FindActivity.ACTIVITY_TAG, "This is Information3");
                Intent intent = new Intent(FindActivity.this, MultiCategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
