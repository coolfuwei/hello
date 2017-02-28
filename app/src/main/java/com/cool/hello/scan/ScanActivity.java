package com.cool.hello.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cool.hello.R;
import com.xys.libzxing.zxing.activity.CaptureActivity;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class ScanActivity extends AppCompatActivity {

    private TextView mResultTextView;
    private EditText mInput;
    private ImageView mImageView;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mResultTextView = (TextView) findViewById(R.id.tv_result);
        mInput = (EditText) findViewById(R.id.et_input);
        mImageView= (ImageView) this.findViewById(R.id.img_shouw);
        mCheckBox= (CheckBox) this.findViewById(R.id.cb_logo);
    }

    /**
     * 扫描二维码
     * @param view
     */
    public void beginScan(View view){
        checkPermission();
    }

    public void createQRCode(View view){
        String input = mInput.getText().toString();
        if (input.equals("")){
            Toast.makeText(this,"输入不能为空",Toast.LENGTH_SHORT).show();
        }else{
            Bitmap qrCode = EncodingUtils.createQRCode(input, 500, 500,
                    mCheckBox.isChecked()? BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher):null);
            mImageView.setImageBitmap(qrCode);
        }
    }

    private void scan() {
        startActivityForResult(new Intent(this, CaptureActivity.class), 0);
    }

    private void checkPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            scan();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},0);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            scan();
        }else {
            Toast.makeText(this,"授权失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String result = bundle.getString("result");
                mResultTextView.setText(result);
            }
        }
    }



}
