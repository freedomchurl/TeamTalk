package com.example.churl.teamproject_app;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

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


        overlist.add(new MapData(127.0630205,37.5091300,"이언지"));
        overlist.add(new MapData(127.061,37.51,"김난희"));


        mMapView.setOnMapStateChangeListener(new NMapView.OnMapStateChangeListener() {
            @Override
            public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
                if(nMapError == null){
                    // success
                    mMapController.setMapCenter(new NGeoPoint(126.956954,37.504147),11);

                    mMapView.setBuiltInZoomControls(true,null);

                    // 여기서 통신을 통하여, 네트워크에서 마커들을 가져와야한다.


                    int markerId = NMapPOIflagType.PIN;

                    NMapPOIdata poIdata = new NMapPOIdata(1,mMapViewerResourceProvider);
                    poIdata.beginPOIdata(1);
                    for(int i=0;i<overlist.size();i++) {

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

        //String data = getIntent().getStringExtra("key"); // Key 를 수정해야한다.
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
        mapOverlayManager = new NMapOverlayManager(this,mMapView,mMapViewerResourceProvider);


    }
}
