package com.example.shisjin.designwindow.lod;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Tenant {
    public float roomArea;
    public float roomPrice;
    public static final  float diffPrice=100.001f;
    public static final float diffArea= 0.00001f;

    public  void rentRoom(Mediator mediator){
        Room room = mediator.rentOut(roomArea, roomPrice);
    }


}
