package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2018/5/9.
 */
/*内存缓存*/
public class MemoryCache implements ImageCache {

   public MemoryCache(){
       initImageCache();
   }

    /*图片缓存*/
    public LruCache<String ,Bitmap> mImageCache;

    private void initImageCache() {
        //计算可使用的最大内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        //取四分之一的可用内存作为缓存
        int cacheSize= maxMemory/4;
        mImageCache=new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes()*bitmap.getHeight()/1024;
            }
        };
    }
    @Override
    public void put(String url, Bitmap bitmap) {
       mImageCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return mImageCache.get(url);
    }
}
