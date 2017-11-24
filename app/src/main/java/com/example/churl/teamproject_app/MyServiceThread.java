package com.example.churl.teamproject_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by churl on 2017-11-06.
 */

public class MyServiceThread extends Thread{
    boolean isRun = true;
    String aa,bb;

    final String serverIP = "10.210.60.61";
    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;

    boolean isGetLoaction = false;

    Location location;
    double lat;
    double lon;
    MyService input;

    Socket sock = null;
    DataOutputStream dos = null;
    OutputStream os = null;
    DataInputStream dis = null;
    InputStream is = null;

    public MyServiceThread(String a, String b,MyService input){

        aa = a;
        bb = b;

        this.input = input;



    }

    public void stopForever(){
        synchronized (this){
            this.isRun = false;
        }

        try {
            //dos.writeUTF("REMOVE///" + aa);
        }catch (Exception e){}
    }

    public void getMyLocation(){
        location = null;


    }



    public void run()
    {
        Log.d("시작했어?","언제");

        try {
            sock = new Socket(serverIP,7676);
            is= sock.getInputStream();
            os = sock.getOutputStream();
            dos = new DataOutputStream(os);
            dis = new DataInputStream(is);
        }catch(Exception e){Log.d("Socket open failed","failed");}

        try {
            dos.writeUTF("ADDUSER///" + aa);

        while(isRun){
            Log.d("김유정",aa+" ,"+bb);
            String input = "";
            // 약속시간 1시간 이내인것을 체크해야한다!

            // GPS를 쓴다
            if(dis.available()!=0)
                input = dis.readUTF();
            if(input.equals("RING"))
            {
                // 알람울리기시작
                this.input.Ringing();
            }


            try{
                Thread.sleep(500);
            }catch (Exception e){Log.d("2번오류","2");}
        }
        }catch (Exception e){Log.d("1번오류","1");}

        // isRun이 빠져나와진 곳
    }

}
