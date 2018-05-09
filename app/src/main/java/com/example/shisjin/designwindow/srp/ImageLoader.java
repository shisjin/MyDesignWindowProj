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
   private ImageCache mImageCahe ;
    //线程池，线程数量CPU的数量
    ExecutorService mExecutorService;
    public ImageLoader(){
        mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        mImageCahe = new  MemoryCache();
    }
    /*注入缓存实现*/
    public void setImageCache(ImageCache cache){
        this.mImageCahe= cache;
    }

    public void dispalyImage(final String url , final ImageView imageView){
        Bitmap bitmap  = mImageCahe.get(url);
        if (bitmap!=null){
            mSetDisplayImage.setImageView(bitmap);
            return;
        }
        //没有图片缓存，提交到线程池中下载图片
        submitLoadRequest(url, imageView);
    }

    private void submitLoadRequest(final String url, final ImageView imageView) {
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
                mImageCahe.put(url,bitmap);
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

    public void setlisetnerLoad(SetDisplayImage mSetDisplayImage){
     this.mSetDisplayImage=mSetDisplayImage;
    }
    private SetDisplayImage  mSetDisplayImage;

    public interface  SetDisplayImage{
        void  setImageView(Bitmap bitmap);
    }

}
