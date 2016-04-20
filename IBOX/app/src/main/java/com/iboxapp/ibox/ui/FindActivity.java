package com.iboxapp.ibox.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.iboxapp.ibox.IshowFragment;
import com.iboxapp.ibox.R;

import java.io.InputStream;

public class FindActivity extends AppCompatActivity {

    private Button mButton;
    private static final String ACTIVITY_TAG="LogDemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        initButton();


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

    public void initButton() {
        mButton = (Button) findViewById(R.id.Button9);
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
