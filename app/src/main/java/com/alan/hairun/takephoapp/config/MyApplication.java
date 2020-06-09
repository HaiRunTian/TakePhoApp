package com.alan.hairun.takephoapp.config;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alan.hairun.gen.DaoMaster;
import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.takephoapp.R;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
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
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);
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
