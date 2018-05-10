package com.example.shisjin.designwindow.srp;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/10.
 */

public class CloseUtils {
    private CloseUtils(){
    }
    public static  void  closeQuietly(Closeable closeable){
        if (null!=closeable){
            try{
                closeable.close();
            }catch (IOException E){
                E.printStackTrace();
            }
        }
    }

}
