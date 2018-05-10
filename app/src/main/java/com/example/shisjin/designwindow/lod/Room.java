package com.example.shisjin.designwindow.lod;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Room {
    public float area;//面积
    public float price;//租金

    public Room(float area,float price){
        this.area=area;
        this.price= price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "area=" + area +
                ", price=" + price +
                '}';
    }
}
