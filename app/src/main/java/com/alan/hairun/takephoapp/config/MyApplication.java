package com.alan.hairun.takephoapp.config;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import com.alan.hairun.gen.DaoMaster;
import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.takephoapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author: Alan
 * @date: 2020/5/8 0008
 * @time: 下午 11:29
 * @deprecated:
 */
public class MyApplication extends Application{

    private static MyApplication INSTANT = null;
    private static DaoSession daoSession;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANT = this;
        initGreenDao();
        MultiDex.install(this);

    }


    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "takephoapp.db", null);
        db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static MyApplication getINSTANT() {
        return INSTANT;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}
