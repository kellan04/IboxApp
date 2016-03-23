package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iboxapp.ibox.MainActivity;
import com.iboxapp.ibox.R;

public class LoginActivity extends AppCompatActivity {

    private EditText phone;
    private EditText  password;
    private TextView register;
    private TextView forgetPassword;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phone = (EditText)findViewById(R.id.edit_login_phoneNum);
        password = (EditText)findViewById(R.id.edit_login_password);
        login = (Button)findViewById(R.id.login_button);
        register = (TextView)findViewById(R.id.textview_register);
        forgetPassword = (TextView)findViewById(R.id.textview_forgetPassword);
        register.setClickable(true);
        forgetPassword.setClickable(true);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "手机号或密码不能为空", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }



    public void login(View view){
        if(phone.getText().toString().equals(null) || password.getText().toString().equals(null)){
            //提示不能为空
            Toast.makeText(getApplicationContext(), "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
        }else{
            //调用接口？

        }
    }
}
