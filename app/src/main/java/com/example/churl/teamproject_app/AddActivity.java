package com.example.churl.teamproject_app;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;

/**
 * Created by churl on 2017-11-04.
 */



public class AddActivity extends Activity {

    EditText projectName= null;
    EditText projectInfo = null;
    ImageView icon1 = null;
    ImageView icon2  = null;
    ImageView icon3= null;
    ImageView icon4 = null;
    ImageView icon5 = null;
    ImageView icon6 = null;

    DatePicker myDate = null;
    Button addButton = null;
    Button cancelButton =null;

    private String clickedIcon = null;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        projectInfo = (EditText) findViewById(R.id.projectInfo);
        projectName = (EditText) findViewById(R.id.projectName);
        icon1=  (ImageView) findViewById(R.id.icon1);
        icon2 = (ImageView) findViewById(R.id.icon2);
        icon3 = (ImageView) findViewById(R.id.icon3);
        icon4 = (ImageView) findViewById(R.id.icon4);
        icon5 = (ImageView) findViewById(R.id.icon5);
        icon6 = (ImageView) findViewById(R.id.icon6);
        myDate = (DatePicker) findViewById(R.id.datePicker);

        addButton = (Button)findViewById(R.id.addProject);
        cancelButton = (Button)findViewById(R.id.cancelProject);



        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                // 데이터를 얻고, 밖으로 내보내야한다.

                Intent tmpIntent = new Intent(); // 임시 Intent를 만든다.
                // 데이터 put
                // 데이터 put을, Intent에다가 하는 구조

                String nameString = projectName.getText().toString();
                String infoString = projectInfo.getText().toString();

                tmpIntent.putExtra("Name",nameString);
                tmpIntent.putExtra("Info",infoString);
                tmpIntent.putExtra("Icon",clickedIcon);

                // 그리고 날짜를 넘겨주면 된다.

                String dateString = myDate.getYear() + "//" + (myDate.getMonth()+1) + "//" + myDate.getDayOfMonth();

                tmpIntent.putExtra("Date",dateString);

                setResult(RESULT_OK,tmpIntent);

                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                // Return Code , Error
                setResult(RESULT_CANCELED);

                finish();
            }
        });

        icon1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                clickedIcon = "whiteboard";
                IconBorderReset();
                icon1.setBackgroundColor(Color.parseColor("#FF8000"));
            }
        });

        icon2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                clickedIcon = "bulb";
                IconBorderReset();
                icon2.setBackgroundColor(Color.parseColor("#FF8000"));
            }
        });

        icon3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                clickedIcon = "checklist";
                IconBorderReset();
                icon3.setBackgroundColor(Color.parseColor("#FF8000"));
            }
        });

        icon4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                clickedIcon = "handshake";
                IconBorderReset();
                icon4.setBackgroundColor(Color.parseColor("#FF8000"));
            }
        });

        icon5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                clickedIcon = "user";
                IconBorderReset();
                icon5.setBackgroundColor(Color.parseColor("#FF8000"));
            }
        });

        icon6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                clickedIcon = "document";
                IconBorderReset();
                icon6.setBackgroundColor(Color.parseColor("#FF8000"));
            }
        });
    }

    public void IconBorderReset()
    {
        icon1.setBackgroundColor(Color.parseColor("#00ffffff"));
        icon2.setBackgroundColor(Color.parseColor("#00ffffff"));
        icon3.setBackgroundColor(Color.parseColor("#00ffffff"));
        icon4.setBackgroundColor(Color.parseColor("#00ffffff"));
        icon5.setBackgroundColor(Color.parseColor("#00ffffff"));
        icon6.setBackgroundColor(Color.parseColor("#00ffffff"));
    }
}
