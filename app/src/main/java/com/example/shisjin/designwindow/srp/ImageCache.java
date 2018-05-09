package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;
/*图片缓存接口*/
public interface ImageCache {
    public void put(String url , Bitmap bitmap);
    public Bitmap get(String url);
}