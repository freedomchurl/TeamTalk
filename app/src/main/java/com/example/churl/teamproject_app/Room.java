package com.example.churl.teamproject_app;

/**
 * Created by churl on 2017-11-02.
 */

public class Room {

    int image;
    String imagetitle;
    private int room_id;

    public int getImage()
    {
        return image;
    }
    public String getImagetitle()
    {
        return imagetitle;
    }

    public Room(int image, String imagetitle,int room_id)
    {
        this.image = image;
        this.imagetitle = imagetitle;
        this.room_id = room_id; // room info를 가져오기 위함.
    }

}
