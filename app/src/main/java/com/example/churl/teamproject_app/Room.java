package com.example.churl.teamproject_app;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by churl on 2017-11-02.
 */

public class Room implements Serializable{

    int image;
    String imagetitle;
    private String room_id;
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

    public Room(int image, String imagetitle,String room_id)
    {
        this.image = image;
        this.imagetitle = imagetitle;
        this.room_id = room_id; // room info를 가져오기 위함.
    }

    public Room(String icon_name, String projecttitle, String projectInfo, String projectDue,String room_id)
    {

        this.room_name = projecttitle;
        this.room_date = projectDue;
        this.room_info = projectInfo;
        this.room_icon = icon_name;
        this.imagetitle = projecttitle;
        this.room_id = room_id;

        setImageID();
    }



    public void setRoom_id(String room_id)
    {
        this.room_id = room_id;
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
        else if(room_icon.equals(""))
            image = R.drawable.default_icon;
    }

    public void setImage(int image)
    {
        this.image = image;
    }

    public void setImagetitle(String imagetitle)
    {
        this.imagetitle = imagetitle;
    }

    public String getRoom_id()
    {
        return this.room_id;
    }
    public void setRoom_name(String room_name)
    {
        this.room_name = room_name;
    }
    public String getRoom_name()
    {
        return this.room_name;
    }
    public void setRoom_info(String room_info)
    {
        this.room_info = room_info;
    }
    public String getRoom_info()
    {
        return this.room_info;
    }
    public void setRoom_date(String room_date)
    {
        this.room_date = room_date;
    }
    public String getRoom_date()
    {
        return this.room_date;
    }
    public void setRoom_icon(String room_icon)
    {
        this.room_icon = room_icon;
    }

}
