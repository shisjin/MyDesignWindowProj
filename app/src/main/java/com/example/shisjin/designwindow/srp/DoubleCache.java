package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/5/9.
 */
/*双缓存*/
public class DoubleCache implements ImageCache {
    MemoryCache mMemoryCache = new MemoryCache();
    DiskCache mDiskCache= new DiskCache();
    //先从内存缓存中获取图片，如果没有，在从SD卡中获取
   @Override
    public Bitmap get (String url){
       Bitmap bitmap= mMemoryCache.get(url);
        if (bitmap==null){
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }
    //将图片缓存到内存和SD卡中
    @Override
    public void put(String url, Bitmap bmp){
        mMemoryCache.put(url,bmp);
        mDiskCache.put(url,bmp);
    }

}
