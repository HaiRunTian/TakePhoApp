package com.alan.hairun.takephoapp.config;

import android.app.Application;

/**
 * @author: Alan
 * @date: 2020/5/8 0008
 * @time: 下午 11:29
 * @deprecated:
 */
public class MyApplication extends Application {

    private  static MyApplication  INSTANT = null;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANT = this;
        initGreenDao();

    }


    private void initGreenDao() {

    }

    public MyApplication getINSTANT(){
        return INSTANT;
    }


}
