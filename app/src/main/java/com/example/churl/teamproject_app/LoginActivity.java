package com.example.churl.teamproject_app;

import android.app.Activity;
import android.content.Intent;
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

public class LoginActivity extends Activity{


    public Button signButton = null;
    public Button loginButton = null;

    public EditText e_email = null;
    public EditText e_pwd = null;

    private String pwdfromServer = null;
    private String namefromServer = null;
    private String numfromServer = null;
    private String idfromServer = null;

    private boolean hasEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        this.e_email = (EditText)findViewById(R.id.login_email);
        this.e_pwd = (EditText) findViewById(R.id.login_pwd);

        this.signButton = (Button)findViewById(R.id.sign); // 가입 버튼을 가져오고
        this.signButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v)
            {
                // 클릭 이벤트. 이때 새로운 창을 띄워야 한다.
                Log.d("Get","Sign in Page");

                Intent i = new Intent(LoginActivity.this,SignActivity.class);
                startActivity(i);
            }
        });

        this.loginButton = (Button) findViewById(R.id.login);
        this.loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                // 로그인을 해결해야한다.
                String s_email = e_email.getText().toString();

                // 우선 값들을 다 가져온다.

                GetAccount getAccount = new GetAccount();
                getAccount.execute(s_email); // email을 바탕으로 서버와 통신한다



            }
        });
    }

    public class GetAccount extends AsyncTask<String,Void,String> {

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

                    if(jsonArray.length() ==0) {
                        hasEmail = false; // Login fail.
                        Toast.makeText(getApplicationContext(), "존재하지 않는 이메일입니다", Toast.LENGTH_SHORT).show(); // makeText를 한다.
                    }
                    else
                    {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i); // Object를 가져오고

                            namefromServer = item.getString("name");
                            numfromServer = item.getString("number");
                            pwdfromServer = item.getString("pwd");
                            idfromServer = item.getString("u_id");

                            String emailfromServer = item.getString("email");


                            hasEmail = true; // Success in Login.

                            if(hasEmail == true) // 존재하고
                            {
                                String s_pwd = e_pwd.getText().toString();
                                if(pwdfromServer.equals(s_pwd)) // 비밀번호가 일치하다면
                                {
                                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show(); // makeText를 한다.
                                    // 로그인 성공, 다음 Activity로 넘어가야한다
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("i_email",emailfromServer);
                                    intent.putExtra("i_name",namefromServer);
                                    intent.putExtra("i_num",numfromServer);
                                    intent.putExtra("i_nid",idfromServer);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }


                }catch(JSONException e){}
            }

        }
    }

}
