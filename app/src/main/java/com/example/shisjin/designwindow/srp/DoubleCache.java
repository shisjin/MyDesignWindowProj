package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/5/9.
 */

public class DoubleCache {
    ImageCache mMemoryCache = new ImageCache();
    DiskCache mDiskCache= new DiskCache();
    //先从内存缓存中获取图片，如果没有，在从SD卡中获取
    public Bitmap get (String url){
       Bitmap bitmap= mMemoryCache.get(url);
        if (bitmap==null){
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }
    //将图片缓存到内存和SD卡中
    public void put(String url, Bitmap bmp){
        mMemoryCache.put(url,bmp);
        mDiskCache.put(url,bmp);
    }

}
