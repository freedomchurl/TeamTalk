package com.example.churl.teamproject_app;

/**
 * Created by churl on 2017-11-05.
 */

public class MapData {

    private double latitude;
    private double longitude;
    private String name;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    MapData(double lati, double longi, String name)
    {
        this.latitude =lati;
        this.longitude =longi;
        this.name = name;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
