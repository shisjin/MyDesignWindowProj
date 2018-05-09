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
    //SD卡缓存
    DiskCache mDiskCache;
    //双缓存
    DoubleCache mDoubleCache;
    //线程池，线程数量CPU的数量
    ExecutorService mExecutorService;
    //是否使用SD卡缓存
    boolean isUseDiskCache=false;
    //使用双缓存
    boolean isUseDoubleCache= false;
    //
    public ImageLoader(){
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        mImageCache= new ImageCache();
        mDiskCache= new DiskCache();
        mDoubleCache= new DoubleCache();
    }

    public void dispalyImage(final String url , final ImageView imageView){
        Bitmap bitmap=null;
        if (isUseDoubleCache){
          bitmap=  mDoubleCache.get(url);
        }else if (isUseDiskCache) {
            bitmap = mDiskCache.get(url);
        }else {
            bitmap  = mImageCache.get(url);
        }
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
                if (isUseDoubleCache){
                    mDoubleCache.put(url,bitmap);
                    return;
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

    public void setIsUseDoubleCahec(boolean isUseDoubleCache){
     this.isUseDoubleCache=isUseDoubleCache;
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
