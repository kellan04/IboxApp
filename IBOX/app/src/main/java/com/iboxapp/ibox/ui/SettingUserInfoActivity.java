package com.iboxapp.ibox.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.iboxapp.ibox.R;
import com.iboxapp.ibox.util.ImageTools;
import com.iboxapp.ibox.util.SharedPreferencesUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SettingUserInfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButtonEnsure;
    private RelativeLayout switchAvatar;
    private ImageView faceImage;
    private String[] items = new String[] { "选择本地图片", "拍照" };
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user_info);

        mToolbar = (Toolbar) findViewById(R.id.simple_toolbar);
        mToolbar.setTitle(getResources().getString(R.string.navigation_userinfo));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mButtonEnsure = (Button) findViewById(R.id.setting_user_info_sure_button);
        mButtonEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingUserInfoActivity.this, "更改成功", Toast.LENGTH_SHORT).show();

            }
        });

        switchAvatar = (RelativeLayout) findViewById(R.id.userinfo);
        faceImage = (ImageView) findViewById(R.id.imageview_face);
        /*String photo = (String) SharedPreferencesUtils.getParam(this, SharedPreferencesUtils.PHOTO_PATH, "String");
        if(photo!=null){
            byte[] content = ImageTools.getImageContentInSize(photo, 310, 310);
            Bitmap b = ImageTools.Bytes2Bimap(content);
            if(b!=null)
                faceImage.setImageBitmap(b);
        }*/
        switchAvatar.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showDialog();
        }
    };

    /**
     * 显示选择对话框
     */
    private void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1:

                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (ImageTools.hasSdcard()) {

                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(),
                                                    IMAGE_FILE_NAME)));
                                }

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (ImageTools.hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory()
                                        + IMAGE_FILE_NAME);
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(SettingUserInfoActivity.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }

                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }
    /**
     * 保存裁剪之后的图片数据
     *
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            faceImage.setImageDrawable(drawable);
        }
    }

    private void savePic(Bitmap photo ){
        long l2 = System.currentTimeMillis();
        String fileName = l2 + ".jpg";
        String tempImgPath = getCacheDir().getAbsolutePath() + "/sysfiles/temp/" + fileName;
        String dir = getDir(tempImgPath);
        File dirFile = new File(dir);
        dirFile.mkdirs();
        if (!dirFile.exists()) {
            Toast.makeText(SettingUserInfoActivity.this, "无法创建SD卡目录,图片无法保存", Toast.LENGTH_LONG).show();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
            photo.compress(Bitmap.CompressFormat.JPEG, 75, bos);// (0 - 100)压缩文件
            SharedPreferencesUtils.setParam(SettingUserInfoActivity.this, SharedPreferencesUtils.PHOTO_PATH, tempImgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  String getDir(String filePath) {
        int lastSlastPos = filePath.lastIndexOf('/');
        return filePath.substring(0, lastSlastPos);
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
