package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iboxapp.ibox.R;

public class ForgetPassword2Activity extends AppCompatActivity {

    private EditText  password;
    private EditText  password2;
    private Button modify;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.update_password));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        password = (EditText)findViewById(R.id.edit_modify_password);
        password2 = (EditText)findViewById(R.id.edit_modify_password2);
        modify = (Button)findViewById(R.id.modify_button);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(null) || password2.getText().toString().equals(null)){
                    Toast toast= Toast.makeText(getApplicationContext(),"手机号或密码不能为空",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else if(!password.getText().toString().equals(password2.getText().toString())){
                    Toast toast= Toast.makeText(getApplicationContext(),"两次密码不一致",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else{
                    //调用接口
                    startActivity(new Intent(ForgetPassword2Activity.this,LoginActivity.class));
                }
            }
        });
    }



}
