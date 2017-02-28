package com.cool.hello.sign;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cool.hello.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Permission;

public class SignActivity extends AppCompatActivity {

    private TouchView mTouchView;
    private Bitmap bitmap;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        initView();

        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        imagePath = externalFilesDir.getAbsolutePath();
    }

    private void initView() {
        mTouchView = (TouchView) findViewById(R.id.touchView);
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////event handler//////////////////////////////////////

    /**
     * 保存
     * @param view
     */
    public void save(View view){
        bitmap = mTouchView.getBitmap();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            saveToSdCard();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    private void saveToSdCard() {
        File file = new File(imagePath + File.separator + "sign.jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,70,fileOutputStream);
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 清空
     * @param view
     */
    public void clear(View view){
        mTouchView.clear();
    }

    /**
     * 取消
     * @param view
     */
    public void cancel(View view){
        mTouchView.clear();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            saveToSdCard();
        }else {
            Toast.makeText(this,"授权失败",Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////
}
