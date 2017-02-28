package com.cool.hello.surfaceview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cool.hello.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CameraActivity extends Activity implements CameraInterface.CamOpenOverCallback {
    CameraSurfaceView surfaceView = null;
    Button shutterBtn;
    float previewRate = -1f;
    boolean isQuit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread openThread = new Thread() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
            }
        };
        openThread.start();
        setContentView(R.layout.activity_demo);
        initView();
        initViewParams();
        shutterBtn.setOnClickListener(new BtnListeners());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isQuit) {
            Thread openThread = new Thread() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    CameraInterface.getInstance().doOpenCamera(CameraActivity.this);
                }
            };
            openThread.start();
        }
        isQuit = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isQuit = true;
    }

    private void initView() {
        surfaceView = (CameraSurfaceView) findViewById(R.id.camera_surfaceview);
        shutterBtn = (Button) findViewById(R.id.btn_shutter);
    }

    private void initViewParams() {
        ViewGroup.LayoutParams params = surfaceView.getLayoutParams();
        Point p = DisplayUtil.getScreenMetrics(this);
        params.width = p.x;
        params.height = p.y;
        previewRate = DisplayUtil.getScreenRate(this); //默认全屏的比例预览
        surfaceView.setLayoutParams(params);
    }

    @Override
    public void cameraHasOpened() {
        SurfaceHolder holder = surfaceView.getSurfaceHolder();
        CameraInterface.getInstance().doStartPreview(holder, previewRate);
    }

    private class BtnListeners implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.btn_shutter:
//                    CameraInterface.getInstance().doTakePicture();
                    Bitmap picture = surfaceView.getPicture();
                    File fileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    FileOutputStream fileOutputStream;
                    try {
                        fileOutputStream = new FileOutputStream(fileDir);
                        picture.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

    }

}