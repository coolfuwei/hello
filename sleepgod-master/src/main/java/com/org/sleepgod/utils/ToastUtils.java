package com.org.sleepgod.utils;

import android.content.Context;
import android.widget.Toast;

/**
 *  单例吐司
 * Created by cool on 2016/12/29.
 */

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context context,String msg){
        if(mToast == null){
            mToast  = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }

}
