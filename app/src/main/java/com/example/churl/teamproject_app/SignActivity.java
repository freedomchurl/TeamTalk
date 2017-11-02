package com.example.churl.teamproject_app;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by churl on 2017-11-02.
 */



public class SignActivity extends Activity {

    EditText email = null;
    EditText name = null;
    EditText num = null;
    EditText passwd = null;

    Button signIn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_activity);

        this.email = (EditText) findViewById(R.id.t_email);
        this.name = (EditText) findViewById(R.id.t_name);
        this.num = (EditText) findViewById(R.id.t_num);
        this.passwd = (EditText) findViewById(R.id.t_pwd);

        this.signIn = (Button) findViewById(R.id.b_login);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_email = email.getText().toString();
                String s_name = name.getText().toString();
                String s_num = num.getText().toString();
                String s_passwd = passwd.getText().toString();

                if(s_email.length() == 0 || s_name.length()==0 || s_num.length() == 0 || s_passwd.length() == 0 )
                {
                    // 이중 하나라도 길이가 0이면 Toast 메시지 띄우기
                    Toast.makeText(getApplicationContext(),"모든 칸을 채워주세요",Toast.LENGTH_LONG).show();
                }
                else
                {
                    // 여기서 우선, 값을 가져와야 한다.
                    GetAccount getAc = new GetAccount();
                    getAc.execute(s_email); // s_email 을 넘겨준다.
                }
            }
        });

    }

    public class GetAccount extends AsyncTask<String,Void,String>{

        public String doInBackground(String ...params)
        {
            try{
                String input_email = params[0]; // input email은 string [0]
                String url = "http://35.201.138.226/read_account.php?email=" + input_email;

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

                    if(jsonArray.length()==0)
                    {
                        // 데이터를 추가해야한다.
                        String s_email = email.getText().toString();
                        String s_name = name.getText().toString();
                        String s_num = num.getText().toString();
                        String s_passwd = passwd.getText().toString();

                        WriteAccount writeAccount = new WriteAccount();
                        writeAccount.execute(s_num,s_name,s_passwd,s_email);

                        Toast.makeText(getApplicationContext(),"성공적으로 가입하였습니다",Toast.LENGTH_LONG).show();

                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"이미 존재하는 이메일입니다",Toast.LENGTH_LONG).show(); // 출력한다.
                    }

                }catch(JSONException e){}
            }

        }
    }

    public class WriteAccount extends AsyncTask<String,Void,String>{

        public String doInBackground(String ...params)
        {
            try{
                String input_email = params[3]; // input email은 string [0]
                String input_number = params[0];
                String input_name = params[1];
                String input_pwd = params[2];

                String url = "http://35.201.138.226/write_account.php?number=" + input_number + "&name=" + input_name + "&pwd=" + input_pwd + "&email=" + input_email;

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
}
