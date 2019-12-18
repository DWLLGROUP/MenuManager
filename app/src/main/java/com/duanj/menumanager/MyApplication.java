package com.duanj.menumanager;

import android.app.Application;

import com.duanj.menumanager.util.LoadTask;

/**
 * Author Mr.Duan
 * Date 2019/12/18
 * Description:MyApplication
 */
public class MyApplication extends Application{
 private String fileName="menulist";//路径：app/src/main/assets/menulist
    @Override
    public void onCreate() {
        super.onCreate();
        new LoadTask(getApplicationContext(),fileName);
    }
}
