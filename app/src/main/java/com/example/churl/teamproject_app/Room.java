package com.example.churl.teamproject_app;

import android.graphics.drawable.Drawable;

/**
 * Created by churl on 2017-11-02.
 */

public class Room {

    int image;
    String imagetitle;
    private int room_id;
    private String room_name;
    private String room_info;
    private String room_date;
    private String room_icon;

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

    public Room(String icon_name, String projecttitle, String projectInfo, String projectDue)
    {

        this.room_name = projecttitle;
        this.room_date = projectDue;
        this.room_info = projectInfo;
        this.room_icon = icon_name;
        this.imagetitle = projecttitle;

        setImageID();
    }

    public void setImageID()
    {
        if(room_icon.equals("bulb"))
            image = R.drawable.bulb;
        else if(room_icon.equals("checklist"))
            image = R.drawable.checklist;
        else if(room_icon.equals("document"))
            image = R.drawable.document;
        else if(room_icon.equals("handshake"))
            image = R.drawable.handshake;
        else if(room_icon.equals("whiteboard"))
            image = R.drawable.whiteboard;
        else if(room_icon.equals("user"))
            image = R.drawable.user;
    }

}
