package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ImageLoader {
    //内存缓存
    ImageCache mImageCache;
    //线程池，线程数量CPU的数量
    ExecutorService mExecutorService;
    //SD卡缓存
    DiskCache mDiskCache;
    //是否使用SD卡缓存
    boolean isUseDiskCache=false;
    public ImageLoader(){
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        mImageCache= new ImageCache();
        mDiskCache= new DiskCache();
    }

    public void dispalyImage(final String url , final ImageView imageView){
        //首先从缓存里面取，如果为空就进行下载
        Bitmap bitmap =isUseDiskCache?mDiskCache.get(url):mImageCache.get(url);
        if (bitmap!=null){
            mSetDisplayImage.setImageView(bitmap);
            return;
        }
        imageView.setTag(url);
        //没有缓存，则提交给线程池进行下载
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
           Bitmap bitmap= downloadImage(url);
                if (bitmap==null)return;
                if (imageView.getTag().equals(url)){
                    mSetDisplayImage.setImageView(bitmap);
                   // imageView.setImageBitmap(bitmap);
                }
                //保存到缓存中
                mDiskCache.put(url,bitmap);
                mImageCache.put(url,bitmap);
            }
        });
    }

    public void  setIsUseDiskCahce(boolean isUseDiskCache){
        this.isUseDiskCache=isUseDiskCache;
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

    public void setlisetnerLoad(SetDisplayImage mSetDisplayImage){
     this.mSetDisplayImage=mSetDisplayImage;
    }
    private SetDisplayImage  mSetDisplayImage;

    public interface  SetDisplayImage{
        void  setImageView(Bitmap bitmap);
    }

}
