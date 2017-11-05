package com.example.churl.teamproject_app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by churl on 2017-11-05.
 */

public class ProjectInfo extends Activity {

    TextView name = null;
    TextView info = null;
    TextView due = null;

    ImageView infoicon = null;

    Room myRoom = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.projectinfo_activity);

        name = (TextView) findViewById(R.id.projectinfo_name2);
        info = (TextView) findViewById(R.id.projectinfo_info2);
        due = (TextView) findViewById(R.id.projectinfo_due2);
        infoicon = (ImageView) findViewById(R.id.projectInfo_icon2);

        myRoom = (Room) getIntent().getSerializableExtra("RoomData");

        name.setText(myRoom.getRoom_name());
        info.setText(myRoom.getRoom_info());
        due.setText(myRoom.getRoom_date().substring(0,10) + " 까지");

        infoicon.setImageResource(myRoom.getImage());
    }


}
