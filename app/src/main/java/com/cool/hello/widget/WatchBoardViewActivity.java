package com.cool.hello.widget;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cool.hello.R;
import com.org.sleepgod.widget.CallFloatBoxView;

import java.lang.reflect.Method;

public class WatchBoardViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_board_view);
    }

    public void open(View v){
        //开启悬浮框前先请求权限
        if("Xiaomi".equals(Build.MANUFACTURER)){//小米手机
            requestPermission();
        }else if("Meizu".equals(Build.MANUFACTURER)){//魅族手机
            requestPermission();
        }else {//其他手机
            CallFloatBoxView.showFloatBox(this);
        }
    }

    public void close(View v){
        CallFloatBoxView.hideFloatBox();
    }

    /**
     * 请求用户给予悬浮窗的权限
     */
    public void requestPermission() {
        if(isFloatWindowOpAllowed(this)){//已经开启
            CallFloatBoxView.showFloatBox(this);
        }else {
            openSetting();
        }
    }

    /**
     * 打开权限设置界面
     */
    public void openSetting() {
        try {
            Intent localIntent = new Intent(
                    "miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter",
                            "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", getPackageName());
            startActivityForResult(localIntent,11);

        } catch (ActivityNotFoundException localActivityNotFoundException) {
            Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent1.setData(uri);
            startActivityForResult(intent1,11);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 11){
            if(isFloatWindowOpAllowed(this)){//已经开启
                CallFloatBoxView.showFloatBox(this);
            }else {
                Toast.makeText(this,"开启悬浮窗失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 判断悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24);  // AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                return true;
            } else {
                return false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {

                Class<?> spClazz = Class.forName(manager.getClass().getName());
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op,
                        Binder.getCallingUid(), context.getPackageName());
                Log.e("399"," property: " + property);

                if (AppOpsManager.MODE_ALLOWED == property) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e("399","Below API 19 cannot invoke!");
        }
        return false;
    }

}
