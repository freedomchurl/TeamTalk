package com.example.churl.teamproject_app;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by churl on 2017-11-04.
 */



public class AddMemberActivity extends Activity {

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

    private String clickedIcon = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmember_activity);

        AlertDialog.Builder ad = new AlertDialog.Builder(AddMemberActivity.this);

        ad.setTitle("팀 멤버 추가");
        ad.setMessage("추가하려는 팀 멤버 이메일을 적으세요");

        final EditText et = new EditText(AddMemberActivity.this);
        ad.setView(et);

        ad.setPositiveButton("초대", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // 초대

            }
        });

        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 취소
            }
        });

        ad.show();

    }
}
