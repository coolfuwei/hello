package com.cool.hello;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chinaso.greendaodemo.DaoMaster;
import com.chinaso.greendaodemo.DaoSession;

/**
 * Created by cool on 2017/2/15.
 */

public class App extends Application {

    public final static String dbName = "test_db";
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置数据库
        setupDatabase();
    }

    private void setupDatabase() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, dbName, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(Context context) {

        return daoSession;
    }
}
