package com.example.churl.teamproject_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by churl on 2017-11-06.
 */

public class Talk extends Activity{

    String pid;
    String uid;


    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ImageButton imgButton = null;
    ArrayList<Content> items = new ArrayList<Content>();

    GetIdeaList getProjectList = new GetIdeaList();

    EditText inputText = null;
    Button sendText = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.talk_activity);

        inputText = (EditText) findViewById(R.id.talkEdit);
        sendText = (Button) findViewById(R.id.send);

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mypid = String.valueOf(pid);
                String myuid = String.valueOf(uid);
                String mycontent = inputText.getText().toString();
                String currentDate = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date());
                inputText.setText("");

                WriteIdeaList writeIdeaList = new WriteIdeaList();
                writeIdeaList.execute(mypid,myuid,mycontent,currentDate);
            }
        });

        pid = getIntent().getStringExtra("PID");
        uid = getIntent().getStringExtra("UID");

        mContext = getApplicationContext();

        imgButton = (ImageButton) findViewById(R.id.refresh_talk);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProjectList = new GetIdeaList();
                getProjectList.execute(pid);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view23);
        recyclerView.setHasFixedSize(true);

                /*i.putExtra("PID",thisRoom.getRoom_id());
        i.putExtra("UID",u_id);
        i.putExtra("NAME",name);*/
        // StaggeredGrid 레이아웃을 사용한다
        //layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); //
        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);
        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Talk.MyAdpater(items, mContext);
        recyclerView.setAdapter(adapter);


        getProjectList.execute(pid);
    }


    public class WriteIdeaList extends AsyncTask<String,Void,String> {

        public String doInBackground(String ...params)
        {
            try{
                String input_pid = params[0]; // input email은 string [0]
                //String input_pid = params[1];
                String input_uid = params[1];
                String input_content = params[2];
                String input_date = params[3];


                String url = "http://35.201.138.226/write_content.php?p_id=" + input_pid + "&content=" + input_content + "&u_id=" + input_uid + "&date=" + input_date;

                Log.d("내URL",url);

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
            getProjectList = new GetIdeaList();
            getProjectList.execute(pid);
        }
    }

    public class GetIdeaList extends AsyncTask<String,Void,String> {

        public String doInBackground(String ...params)
        {
            try{
                String input_pid = params[0]; // input email은 string [0]
                //String input_pid = params[1];


                String url = "http://35.201.138.226/read_newcontent.php?p_id=" + input_pid;

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

                    }
                    else
                    {
                        items.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i); // Object를 가져오고

                            String name = item.getString("name");
                            String date = item.getString("date");
                            String content = item.getString("content");


                            int uid = item.getInt("u_id");
                            int colorID = R.color.color2;

                            if(uid%6==0)
                                colorID = R.color.color1;
                            else if(uid%6==1)
                                colorID = R.color.color2;
                            else if(uid%6==2)
                                colorID = R.color.color3;
                            else if(uid%6==3)
                                colorID = R.color.color4;
                            else if(uid%6==4)
                                colorID = R.color.color5;
                            else if(uid%6==5)
                                colorID = R.color.color6;

                            items.add(0,new Content(name,date,content,colorID));

                        }

                        adapter.notifyDataSetChanged();
                    }


                }catch(JSONException e){}
            }

        }
    }

    class MyAdpater extends RecyclerView.Adapter {
        private Context context;
        private ArrayList<Content> mItems;

        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        public MyAdpater(ArrayList<Content> items, Context mContext) {
            mItems = items;
            context = mContext;
        }

        @Override
        public Talk.MyAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_talkcard, parent, false);
            Talk.MyAdpater.ViewHolder holder = new Talk.MyAdpater.ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((Talk.MyAdpater.ViewHolder)holder).textView.setText(mItems.get(position).getInner());
            ((Talk.MyAdpater.ViewHolder)holder).writeName.setText(mItems.get(position).getName());
            ((Talk.MyAdpater.ViewHolder)holder).contentDate.setText(mItems.get(position).getDate());
            ((Talk.MyAdpater.ViewHolder)holder).writeName.setBackgroundResource(mItems.get(position).getColorID());

        }



        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {


            public TextView textView;
            public TextView  writeName;
            public TextView contentDate;
            public ViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.imagetitle);
                writeName = (TextView) view.findViewById(R.id.talkid);
                contentDate = (TextView) view.findViewById(R.id.contentDate);
                // 이 부분에서, 드래그 삭제 기능을 넣어야한다.
            }



        }

        private void setAnimation(View viewToAnimate, int position) {
            // 새로 보여지는 뷰라면 애니메이션을 해줍니다
            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }


    }

}
