package com.example.shisjin.designwindow.srp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/9.
 */

public class DiskCache {
    public static final String ABSOLUTEPATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();//内存卡路径
    //手机自带储存路径
    public static final String DATADIRECTORY = Environment.getDataDirectory().getAbsolutePath();

    //从缓存中获取图片

    public Bitmap get(String url){
        return BitmapFactory.decodeFile(ABSOLUTEPATH+"/CacheImg/"+url.substring(url.indexOf("j"),url.length())+"img"+".jpg");
    }
    //将图片缓存到内存中
    public void  put(String url ,Bitmap bitmap){
        File file =new File(ABSOLUTEPATH+"/CacheImg/");
        if (!file.exists()){
            file.mkdir();
        }
        FileOutputStream fileOutputStream =null;
        try {

            fileOutputStream=new FileOutputStream(file.getAbsolutePath()+"/"+url.substring(url.indexOf("j"),url.length())+"img"+".jpg");
             bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
