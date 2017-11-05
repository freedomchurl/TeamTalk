package com.example.churl.teamproject_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



/**
 * Created by churl on 2017-11-04.
 */

public class ProjectMainActivity extends Activity{

    private Room thisRoom = null;

    TextView nameView = null;

    ImageButton menu1 = null;

    ImageButton menu3 =null;
    ImageButton menu4 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_main_page);

        nameView = (TextView) findViewById(R.id.projectmain_name);
        menu1 = (ImageButton) findViewById(R.id.menu1);
        menu3 = (ImageButton) findViewById(R.id.menu3);
        menu4 = (ImageButton) findViewById(R.id.menu4);

        thisRoom = (Room) getIntent().getSerializableExtra("RoomData");

        Log.d("방이름은1",thisRoom.getRoom_name());
        Log.d("PID는1",thisRoom.getRoom_id());
        nameView.setText(thisRoom.getRoom_name());




        menu1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 프로젝트 소개 페이지로 넘어가야한다.

                Intent i = new Intent(ProjectMainActivity.this,ProjectInfo.class);

                i.putExtra("RoomData",thisRoom);

                startActivity(i);



            }
        });

        menu3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent i = new Intent(ProjectMainActivity.this,MapActivity.class);
                // room id를 put 해주어야 한다

                startActivity(i);
            }
        });

        menu4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProjectMainActivity.this,TeamListActivity.class);
                // room id를 put 해주어야 한다

                i.putExtra("RoomData",thisRoom);
                startActivity(i);
            }
        });

    }
}
