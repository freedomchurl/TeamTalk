package com.example.churl.teamproject_app;

/**
 * Created by churl on 2017-11-24.
 */

public class Content {
    String name;
    String date;
    String inner;
    int colorID;

    Content(String name, String date, String inner, int colorID)
    {
        this.name = name;
        this.date = date;
        this.inner = inner;
        this.colorID = colorID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int colorID) {
        this.colorID = colorID;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getInner() {
        return inner;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setInner(String inner) {
        this.inner = inner;
    }
}
