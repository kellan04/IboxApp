package com.iboxapp.ibox.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.util.BitmapUtils;

/**
 * Created by gongchen on 2016/5/3.
 */
public class ImageTextButton extends RelativeLayout {

    private ImageView imgView;
    private TextView  textView;

    public ImageTextButton(Context context) {
        super(context,null);
    }

    public ImageTextButton(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.img_text_button_small, this,true);

        this.imgView = (ImageView)findViewById(R.id.img_text_bt_small_imageview);
        this.textView = (TextView)findViewById(R.id.img_text_bt_small_textview);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(int resourceID) {
//        this.imgView.setImageResource(resourceID);

        Bitmap bm = null;
        bm = BitmapUtils.decodeSampledBitmapFromResource(getResources(), resourceID, this.imgView.getWidth(), this.imgView.getHeight());
        this.imgView.setImageBitmap(bm);
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }

    public void setTextSize(float size) {
        this.textView.setTextSize(size);
    }
}
