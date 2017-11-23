package com.example.churl.teamproject_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by churl on 2017-11-06.
 */

public class ScheduleActivity extends Activity {

    String p_id = null;
    ArrayList<ScheduleData> mySchedule = new ArrayList<ScheduleData>();

    List<String> spinner_items = new ArrayList<String>();

    boolean [] isClicked = new boolean[91];

    ImageButton[] myButtons = new ImageButton[13*7];

    String info = null;

    Spinner  mySpinner = null;

    Button okButton = null;
    Button cancelButton = null;

    ArrayAdapter<String> spinner_adapter = null;
    String u_id = "";
    String name = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);

        this.setButtonID();



        for(int i=0;i<91;i++) {
            isClicked[i] = false;
        }

        addButtonListener();

        okButton = (Button) findViewById(R.id.okay);
        okButton.setOnClickListener(new View.OnClickListener(){



            @Override
            public void onClick(View view) {
                    // 지금 이걸 서버로 보내야한다.
                WriteuserProject writeuserProject = new WriteuserProject();

                info = MakeInfo();
                writeuserProject.execute(info,u_id,p_id);
                finish();
            }
        });
        cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mySpinner = (Spinner) findViewById(R.id.spinner); // Spinner 가져오고
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d("asdasda","asdasd");

                    // 버튼 셋팅이 필요하다.
                    info = mySchedule.get(i).info;
                    Log.d("InfoValue",info);
                    setButtonColorInit(mySchedule.get(i).info);

                    if(!mySchedule.get(i).name.equals(name))
                    {
                        okButton.setClickable(false);
                        cancelButton.setClickable(false);
                        // 버튼들도 안댐
                        setButtonClickable(false);
                    }
                    else
                    {
                        setButtonClickable(true);
                        okButton.setClickable(true);
                        cancelButton.setClickable(true);
                    }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                    Log.d("asdasdad12e12312","asdasdasdasd");
            }

        });

        p_id = getIntent().getStringExtra("PID");
        u_id = getIntent().getStringExtra("UID");
        name = getIntent().getStringExtra("NAME");

        // p_id를 가져왔다.


        GetSchedule getSchedule = new GetSchedule();
        getSchedule.execute(p_id);

        spinner_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinner_items);


    }

    public String MakeInfo()
    {
        String returnValue = "";

        for(int i=0;i<91;i++)
        {
            if(isClicked[i]==true)
                returnValue += "1";
            else
                returnValue += "0";
        }

        return returnValue;
    }

    public void setButtonClickable(boolean input)
    {
        for(int i=0;i<91;i++)
        {
            myButtons[i].setClickable(input);//BackgroundColor(Color.parseColor("#3886E8"));
        }

    }

    public void setButtonColorInit(String input)
    {
        char [] car = input.toCharArray();

        for(int i=0;i<91;i++)
        {
            myButtons[i].setBackgroundColor(Color.parseColor("#FF4081"));
        }



        for(int i=0;i<car.length;i++){
            if(car[i]=='0')
            {
                myButtons[i].setBackgroundColor(Color.parseColor("#FF4081"));
                isClicked[i] = false;

            }
            else
            {
                isClicked[i] = true;
                myButtons[i].setBackgroundColor(Color.parseColor("#3886E8"));
            }
        }
    }


    class customButton implements  View.OnClickListener
    {
        int i=0;

        customButton(int i)
        {
            this.i = i;
        }

        @Override
        public void onClick(View view) {
            if(isClicked[i] == true)
            {
                isClicked[i] = false;
                myButtons[i].setBackgroundColor(Color.parseColor("#FF4081"));
            }
            else
            {
                isClicked[i] = true;
                myButtons[i].setBackgroundColor(Color.parseColor("#3886E8"));
            }
        }
    }

    public void addButtonListener()
    {
        for(int i=0;i<91;i++)
        {
            myButtons[i].setOnClickListener(new customButton(i));
        }
    }

    public class WriteuserProject extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

        public String doInBackground(String ...params)
        {
            try{

                String input_info = params[0];
                String input_uid = params[1];
                String input_pid = params[2];

                String url = "http://35.201.138.226/update_schedule.php?u_id=" + input_uid + "&p_id=" + input_pid + "&info=" + input_info+
                        "";

                URL obj = new URL(url); // URL 객체로 받고,

                Log.d("ddddResult",url);



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


    public void setButtonID()
    {
        myButtons[0] = (ImageButton) findViewById(R.id.dayMon1);
        myButtons[1] = (ImageButton) findViewById(R.id.dayMon2);
        myButtons[2] = (ImageButton) findViewById(R.id.dayMon3);
        myButtons[3] = (ImageButton) findViewById(R.id.dayMon4);
        myButtons[4] = (ImageButton) findViewById(R.id.dayMon5);
        myButtons[5] = (ImageButton) findViewById(R.id.dayMon6);
        myButtons[6] = (ImageButton) findViewById(R.id.dayMon7);
        myButtons[7] = (ImageButton) findViewById(R.id.dayMon8);
        myButtons[8] = (ImageButton) findViewById(R.id.dayMon9);
        myButtons[9] = (ImageButton) findViewById(R.id.dayMon10);
        myButtons[10] = (ImageButton) findViewById(R.id.dayMon11);
        myButtons[11] = (ImageButton) findViewById(R.id.dayMon12);
        myButtons[12] = (ImageButton) findViewById(R.id.dayMon13);
        myButtons[13] = (ImageButton) findViewById(R.id.dayTue1);
        myButtons[14] = (ImageButton) findViewById(R.id.dayTue2);
        myButtons[15] = (ImageButton) findViewById(R.id.dayTue3);
        myButtons[16] = (ImageButton) findViewById(R.id.dayTue4);//dayMon1);
        myButtons[17] = (ImageButton) findViewById(R.id.dayTue5);
        myButtons[18] = (ImageButton) findViewById(R.id.dayTue6);
        myButtons[19] = (ImageButton) findViewById(R.id.dayTue7);
        myButtons[20] = (ImageButton) findViewById(R.id.dayTue8);
        myButtons[21] = (ImageButton) findViewById(R.id.dayTue9);
        myButtons[22] = (ImageButton) findViewById(R.id.dayTue10);
        myButtons[23] = (ImageButton) findViewById(R.id.dayTue11);
        myButtons[24] = (ImageButton) findViewById(R.id.dayTue12);
        myButtons[25] = (ImageButton) findViewById(R.id.dayTue13);
        myButtons[26] = (ImageButton) findViewById(R.id.dayWed1);
        myButtons[27] = (ImageButton) findViewById(R.id.dayWed2);
        myButtons[28] = (ImageButton) findViewById(R.id.dayWed3);
        myButtons[29] = (ImageButton) findViewById(R.id.dayWed4);
        myButtons[30] = (ImageButton) findViewById(R.id.dayWed5);
        myButtons[31] = (ImageButton) findViewById(R.id.dayWed6);
        myButtons[32] = (ImageButton) findViewById(R.id.dayWed7);
        myButtons[33] = (ImageButton) findViewById(R.id.dayWed8);
        myButtons[34] = (ImageButton) findViewById(R.id.dayWed9);
        myButtons[35] = (ImageButton) findViewById(R.id.dayWed10);
        myButtons[36] = (ImageButton) findViewById(R.id.dayWed11);
        myButtons[37] = (ImageButton) findViewById(R.id.dayWed12);
        myButtons[38] = (ImageButton) findViewById(R.id.dayWed13);
        myButtons[39] = (ImageButton) findViewById(R.id.dayThu1);
        myButtons[40] = (ImageButton) findViewById(R.id.dayThu2);
        myButtons[41] = (ImageButton) findViewById(R.id.dayThu3);
        myButtons[42] = (ImageButton) findViewById(R.id.dayThu4);
        myButtons[43] = (ImageButton) findViewById(R.id.dayThu5);
        myButtons[44] = (ImageButton) findViewById(R.id.dayThu6);
        myButtons[45] = (ImageButton) findViewById(R.id.dayThu7);
        myButtons[46] = (ImageButton) findViewById(R.id.dayThu8);
        myButtons[47] = (ImageButton) findViewById(R.id.dayThu9);
        myButtons[48] = (ImageButton) findViewById(R.id.dayThu10);
        myButtons[49] = (ImageButton) findViewById(R.id.dayThu11);
        myButtons[50] = (ImageButton) findViewById(R.id.dayThu12);
        myButtons[51] = (ImageButton) findViewById(R.id.dayThu13);
        myButtons[52] = (ImageButton) findViewById(R.id.dayFri1);
        myButtons[53] = (ImageButton) findViewById(R.id.dayFri2);
        myButtons[54] = (ImageButton) findViewById(R.id.dayFri3);
        myButtons[55] = (ImageButton) findViewById(R.id.dayFri4);
        myButtons[56] = (ImageButton) findViewById(R.id.dayFri5);
        myButtons[57] = (ImageButton) findViewById(R.id.dayFri6);
        myButtons[58] = (ImageButton) findViewById(R.id.dayFri7);
        myButtons[59] = (ImageButton) findViewById(R.id.dayFri8);
        myButtons[60] = (ImageButton) findViewById(R.id.dayFri9);
        myButtons[61] = (ImageButton) findViewById(R.id.dayFri10);
        myButtons[62] = (ImageButton) findViewById(R.id.dayFri11);
        myButtons[63] = (ImageButton) findViewById(R.id.dayFri12);
        myButtons[64] = (ImageButton) findViewById(R.id.dayFri13);
        myButtons[65] = (ImageButton) findViewById(R.id.daySat1);
        myButtons[66] = (ImageButton) findViewById(R.id.daySat2);
        myButtons[67] = (ImageButton) findViewById(R.id.daySat3);
        myButtons[68] = (ImageButton) findViewById(R.id.daySat4);
        myButtons[69] = (ImageButton) findViewById(R.id.daySat5);
        myButtons[70] = (ImageButton) findViewById(R.id.daySat6);
        myButtons[71] = (ImageButton) findViewById(R.id.daySat7);
        myButtons[72] = (ImageButton) findViewById(R.id.daySat8);
        myButtons[73] = (ImageButton) findViewById(R.id.daySat9);
        myButtons[74] = (ImageButton) findViewById(R.id.daySat10);
        myButtons[75] = (ImageButton) findViewById(R.id.daySat11);
        myButtons[76] = (ImageButton) findViewById(R.id.daySat12);
        myButtons[77] = (ImageButton) findViewById(R.id.daySat13);
        myButtons[78] = (ImageButton) findViewById(R.id.daySun1);
        myButtons[79] = (ImageButton) findViewById(R.id.daySun2);
        myButtons[80] = (ImageButton) findViewById(R.id.daySun3);
        myButtons[81] = (ImageButton) findViewById(R.id.daySun4);
        myButtons[82] = (ImageButton) findViewById(R.id.daySun5);
        myButtons[83] = (ImageButton) findViewById(R.id.daySun6);
        myButtons[84] = (ImageButton) findViewById(R.id.daySun7);
        myButtons[85] = (ImageButton) findViewById(R.id.daySun8);
        myButtons[86] = (ImageButton) findViewById(R.id.daySun9);
        myButtons[87] = (ImageButton) findViewById(R.id.daySun10);
        myButtons[88] = (ImageButton) findViewById(R.id.daySun11);
        myButtons[89] = (ImageButton) findViewById(R.id.daySun12);
        myButtons[90] = (ImageButton) findViewById(R.id.daySun13);



    }


    public class GetSchedule extends AsyncTask<String,Void,String> {

        public String doInBackground(String ...params)
        {
            try{
                String input_pid = params[0]; // input email은 string [0]
                String url = "http://35.201.138.226/read_schedule.php?p_id=" + input_pid;

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

                    if(jsonArray.length() ==0) {

                        Toast.makeText(getApplicationContext(), "존재하지 않는 이메일입니다", Toast.LENGTH_SHORT).show(); // makeText를 한다.
                    }
                    else
                    {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i); // Object를 가져오고

                            String getu_id = item.getString("u_id");
                            String getname = item.getString("name");
                            String getinfo = item.getString("info");

                            mySchedule.add(new ScheduleData(getu_id,getname,getinfo));

                            spinner_items.add(getname); // 추가하고나서

                        }

                        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mySpinner.setAdapter(spinner_adapter);
                    }


                }catch(JSONException e){}
            }

        }
    }

    public class ScheduleData
    {
        String u_id;
        String name;
        String info;

        ScheduleData(String u_id, String name, String info)
        {
            this.info = info;
            this.u_id = u_id;
            this.name = name;
        }
    }

}
