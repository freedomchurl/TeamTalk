package com.example.churl.teamproject_app;

/**
 * Created by churl on 2017-11-02.
 */

public class Item {

    int image;
    String imagetitle;

    public int getImage()
    {
        return image;
    }
    public String getImagetitle()
    {
        return imagetitle;
    }

    public Item(int image, String imagetitle)
    {
        this.image = image;
        this.imagetitle = imagetitle;
    }

}
