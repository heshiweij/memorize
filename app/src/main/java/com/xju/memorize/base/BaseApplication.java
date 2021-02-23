package com.xju.memorize.base;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getName();

    private static BaseApplication mApp;

    public static List<Activity> sActivities = new ArrayList<>();

    public static BaseApplication getApp() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        mApp = this;
    }


    /**
     * 获取 Activities 栈
     *
     * @return List<Activity>
     */
    public static List<Activity> getActivities() {
        return sActivities;
    }
}
