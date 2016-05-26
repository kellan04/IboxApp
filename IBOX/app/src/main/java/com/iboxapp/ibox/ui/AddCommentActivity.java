package com.iboxapp.ibox.ui;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iboxapp.ibox.R;

public class AddCommentActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButtonEnsure;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle("添加评论");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEditText = (EditText) findViewById(R.id.activity_add_comment_editText);

        mButtonEnsure = (Button) findViewById(R.id.comment_sure_button);
        mButtonEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditText.getText().toString().trim().length() == 0){
                    Snackbar.make(view, "评论为空", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else{
                    Snackbar.make(view, "评论完成", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    delay(1000);
                    finish();
                }

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
