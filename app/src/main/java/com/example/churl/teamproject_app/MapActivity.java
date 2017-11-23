package com.example.churl.teamproject_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class MapActivity extends NMapActivity {

    private final String ClientID = "8gjNGh9hIzm5W3wtF5kf";
    private final String ClientSecret = "CsYoL5fXuJ";

    private NMapView mMapView;
    private NMapController mMapController;

    private NMapViewerResourceProvider mMapViewerResourceProvider = null;

    private NMapOverlayManager mapOverlayManager = null;

    private ArrayList<MapData> overlist = new ArrayList<MapData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = new NMapView(this);
        setContentView(mMapView);
        mMapView.setClientId(this.ClientID);
        mMapView.setClickable(true);


        //overlist.add(new MapData(127.0630205,37.5091300,"이언지"));



        mMapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
                if(nMapError == null){
                    // success
                    Log.d("InitSuccess","Success");
                    mMapController.setMapCenter(new NGeoPoint(126.956954,37.504147),11);

                    mMapView.setBuiltInZoomControls(true,null);

                    // 여기서 통신을 통하여, 네트워크에서 마커들을 가져와야한다.


                    int markerId = NMapPOIflagType.PIN;

                    NMapPOIdata poIdata = new NMapPOIdata(1,mMapViewerResourceProvider);
                    poIdata.beginPOIdata(1);
                    for(int i=0;i<overlist.size();i++) {

                        Log.d("Add Data","Add");

                        poIdata.addPOIitem(overlist.get(i).getLatitude(),overlist.get(i).getLongitude(),overlist.get(i).getName(),markerId,0);
                    }
                    //poIdata.addPOIitem(127.0630205,37.5091300,"이언지",markerId,0);
                    //poIdata.addPOIitem(127.061,37.51,"김난희",markerId,0);
                    poIdata.endPOIdata();

                    NMapPOIdataOverlay poIdataOverlay = mapOverlayManager.createPOIdataOverlay(poIdata,null);
                    poIdataOverlay.showAllPOIdata(0);

                    mMapView.setScalingFactor(2.0f);
                }
                else{
                    // fail
                    Log.e("wrong","error");
                }
            }

            @Override
            public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

            }

            @Override
            public void onMapCenterChangeFine(NMapView nMapView) {

            }

            @Override
            public void onZoomLevelChange(NMapView nMapView, int i) {

            }

            @Override
            public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

            }
        });

        mMapView.setOnMapViewTouchEventListener(new NMapView.OnMapViewTouchEventListener() {

            @Override
            public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {

            }

            @Override
            public void onLongPressCanceled(NMapView nMapView) {

            }

            @Override
            public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

            }

            @Override
            public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

            }

            @Override
            public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

            }

            @Override
            public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

            }
        });

        mMapController = mMapView.getMapController();

        String data = getIntent().getStringExtra("PID"); // Key 를 수정해야한다.
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mapOverlayManager = new NMapOverlayManager(this,mMapView,mMapViewerResourceProvider);
        //overlist.add(new MapData(127.0630205,37.5091300,"이언지"));
        GetLocation getLocation = new GetLocation();
        getLocation.execute(data);
    }

    public class GetLocation extends AsyncTask<String,Void,String> {

        public String doInBackground(String ...params)
        {
            try{
                String input_pid = params[0]; // input email은 string [0]
                String url = "http://35.201.138.226/read_current_location.php?p_id=" + input_pid;

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("결과는",s);
            if(s!= null)
            {
                // null 이 아닐경우 추가한다.
                try {
                    JSONObject jsonObject = new JSONObject(s); // s를 json화

                    JSONArray jsonArray = jsonObject.getJSONArray("data"); // data를 기준으로 가져온다.


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i); // Object를 가져오고

                        String namefromServer = item.getString("name");
                        String longfromServer = item.getString("longitude");
                        String latfromServer = item.getString("latitude");

                        Log.d("위치정보",namefromServer + " " + longfromServer + " " + latfromServer);

                        if(Double.valueOf(latfromServer)!=0 || Double.valueOf(longfromServer)!=0)
                            overlist.add(new MapData(Double.valueOf(longfromServer),Double.valueOf(latfromServer),namefromServer));
                    }

                    //mMapController.notifyMapCenterLocation();
                    //mMapView.notifyAll();


                }catch(JSONException e){}
            }

        }
    }

}
