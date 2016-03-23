package com.iboxapp.ibox.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iboxapp.ibox.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText  phone;
    private EditText  code;
    private Button get_code;
    private Button forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        phone = (EditText)findViewById(R.id.edit_forget_phoneNum);
        code = (EditText)findViewById(R.id.edit_code);
        get_code = (Button)findViewById(R.id.button_get_code);
        forget_password = (Button)findViewById(R.id.forget_password_button);

        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //调用获取验证码的接口
            }
        });
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phone.getText().toString().equals(null) || code.getText().toString().equals(null)){
                    Toast toast = Toast.makeText(getApplicationContext(), "手机号或验证码不能为空", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    //先调用接口判断，验证码是否正确。根据返回信息，toast出不同的信息，并跳转
                    startActivity(new Intent(ForgetPasswordActivity.this,ForgetPassword2Activity.class));
                }
            }
        });
    }


}
