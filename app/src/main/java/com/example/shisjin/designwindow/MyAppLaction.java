package com.example.shisjin.designwindow;

import android.app.Application;

/**
 * Created by Administrator on 2018/5/9.
 */

public class MyAppLaction extends Application {
    public static  MyAppLaction mContext;

    public static  MyAppLaction  getInstance(){
        if (mContext==null){
            return new MyAppLaction();
        }
        return mContext;
    }

}
