package com.example.churl.teamproject_app;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;



/**
 * Created by churl on 2017-11-04.
 */

public class ProjectMainActivity extends Activity{

    private Room thisRoom = null;

    TextView nameView = null;

    ImageButton menu1 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_main_page);

        nameView = (TextView) findViewById(R.id.projectmain_name);
        menu1 = (ImageButton) findViewById(R.id.menu1);


        thisRoom = (Room) getIntent().getSerializableExtra("RoomData");

        Log.d("방이름은",thisRoom.getRoom_name());

        nameView.setText(thisRoom.getRoom_name());

    }
}
