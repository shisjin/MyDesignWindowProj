package com.example.shisjin.designwindow.lod;

import java.util.ArrayList;
import java.util.List;

import static com.example.shisjin.designwindow.lod.Tenant.diffArea;
import static com.example.shisjin.designwindow.lod.Tenant.diffPrice;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Mediator {
    List<Room> mRooms = new ArrayList<>();
    public  Mediator(){
        for (int i = 0; i < 5; i++) {
            mRooms.add(new Room(14+i,(14+i)*150));
        }
    }

    public List<Room> getAllRooms(){
        return mRooms;
    }

    public Room rentOut(float area,float price){
       // List<Room> allRooms = mediator.getAllRooms();
        for (Room room:mRooms){
            if (isSuitable(area,price,room)){
                return room;
            }
        }
        return null;
    }


    private boolean isSuitable(float area,float price,Room room){
        return Math.abs(room.price- price)<diffPrice
                &&Math.abs(room.area-area)<diffArea;
    }


}
