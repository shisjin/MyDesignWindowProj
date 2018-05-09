package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ImageLoader {
    /*图片缓存*/
    LruCache<String ,Bitmap> mImageCache;
    //线程池，线程数量CPU的数量
    ExecutorService mExecutorService;
    public ImageLoader(){
         initImageCache();
    }

    private void initImageCache() {
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
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
    public void dispalyImage(final String url , final ImageView imageView){
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
           Bitmap bitmap= downloadImage(url);
                if (bitmap==null)return;
                if (imageView.getTag().equals(url))imageView.setImageBitmap(bitmap);
                mImageCache.put(url,bitmap);
            }
        });
    }

    private Bitmap downloadImage(String url) {
        Bitmap bitmap= null;
        HttpURLConnection urlConnection=null;
        try {
            URL Url= new URL(url);
             urlConnection = (HttpURLConnection) Url.openConnection();
             bitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return  bitmap;
    }

}
