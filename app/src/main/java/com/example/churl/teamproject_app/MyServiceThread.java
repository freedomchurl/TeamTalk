package com.example.churl.teamproject_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by churl on 2017-11-06.
 */

public class MyServiceThread extends Thread{
    boolean isRun = true;
    String aa,bb;

    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    boolean isGetLoaction = false;

    Location location;
    double lat;
    double lon;
    MyService input;


    public MyServiceThread(String a, String b,MyService input){

        aa = a;
        bb = b;

        this.input = input;
    }

    public void stopForever(){
        synchronized (this){
            this.isRun = false;
        }
    }

    public void getMyLocation(){
        location = null;


    }



    public void run()
    {
        while(isRun){
            Log.d("김유정",aa+" ,"+bb);

            // 약속시간 1시간 이내인것을 체크해야한다!

            // GPS를 쓴다

            try{
                Thread.sleep(1000);
            }catch (Exception e){}
        }
    }

}
