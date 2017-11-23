package com.example.churl.teamproject_app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

/**
 * Created by churl on 2017-11-06.
 */

public class MyService extends Service implements LocationListener{

    public static String id="DEFAULT";
    public static boolean running = false;
    public static MyServiceThread runningThread = null;

    MyServiceThread myServiceThread = null;

    // 여기서 소리를 켜줘야한다

    MediaPlayer mp;
    // Alarm

    String uid;
    String name;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

       // startForeground(1,new Notification());
        startLocationService();

        uid = intent.getStringExtra("uid");
        name = intent.getStringExtra("name");

        myServiceThread = new MyServiceThread(uid,name,this);
        runningThread = myServiceThread;
        myServiceThread.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        runningThread.stopForever();
        runningThread = null;
    }

    private void startLocationService()
    {
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        /*
        //Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String msg = "Last Know Location -> Latitude : " + location.getLatitude() + "\nLongitude : "
                + location.getLongitude();

        Log.i("SampleLocation",msg);*/

        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

        long minTime = 10000;
        float minDistance = 0.5f;

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minDistance,this);

        //oast.makeText(getApplicationContext(),"Location Service started",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location)
    {
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();

        String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;

        //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

        UpdateLocation updateLocation = new UpdateLocation();
        updateLocation.execute(String.valueOf(longitude),String.valueOf(latitude),uid);

    }


    public class UpdateLocation extends AsyncTask<String,Void,String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }

        public String doInBackground(String ...params)
        {
            try{

                Log.d("UpdataeLocation","UPDATE");
                String input_long = params[0];
                String input_lat = params[1];
                String uid = params[2];

                String url = "http://35.201.138.226/update_location.php?long=" + input_long + "&lat=" + input_lat + "&uid=" + uid;

                URL obj = new URL(url); // URL 객체로 받고,

                HttpURLConnection conn = (HttpURLConnection) obj.openConnection(); // open connection

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

                String line;
                StringBuilder sb = new StringBuilder();

                while((line = reader.readLine())!=null)
                {
                    sb.append(line);
                }

                reader.close();
                return sb.toString();

            }catch(Exception e){

                e.printStackTrace();
            }

            return null;
        }


    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String provider){

    }
}
