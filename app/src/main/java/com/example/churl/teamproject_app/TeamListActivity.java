package com.example.churl.teamproject_app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by churl on 2017-11-05.
 */

public class TeamListActivity extends Activity{

    private ArrayList<User> myTeam = new ArrayList<User>();
    // Team member's data list

    private FloatingActionButton fab;

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    Room myRoom = null;
    String p_id = "";
    String u_id = "";

    private static final int AddMemberMsg = 3; // Activity 전달에 쓰인다.


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.teamlist_activity);

        fab = (FloatingActionButton) findViewById(R.id.float_add2);

        myRoom = (Room) getIntent().getSerializableExtra("RoomData");


        p_id = myRoom.getRoom_id();

        Log.d("이언지병맛",p_id);

        GetTeamMember getTeamMember = new GetTeamMember();
        getTeamMember.execute(p_id);
        //myTeam.add(new User("김난희","01067006753","imhee196@naver.com"));


        fab.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                // 여기서 추가관련 부분을 작성하여야 한다.
                //  앞에다가 추가할 필요가 있다. 그리고 정렬은?

                //Intent i = new Intent(TeamListActivity.this,AddMemberActivity.class);

                //startActivityForResult(i,AddMemberMsg);

                AlertDialog.Builder ad = new AlertDialog.Builder(TeamListActivity.this);

                ad.setTitle("팀 멤버 추가");
                //ad.setMessage("추가하려는 팀 멤버 이메일을 적으세요");

                final EditText et = new EditText(TeamListActivity.this);
                et.setHint("이메일");
                ad.setView(et);

                ad.setPositiveButton("초대", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // 초대
                        // 전송하고 창닫기, 만약 존재하지않다면 Toast를 띄우고 끝내기
                        GetAccount getAccount = new GetAccount();
                        getAccount.execute(et.getText().toString());

                    }
                });

                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소
                        dialogInterface.dismiss();
                    }
                });

                ad.show();

                //items.add(new Room(R.drawable.logo,"자료구조설계",3));
                //items.add(0,new Room(R.drawable.logo,aa[i++],3));
                Log.d("Add","addSuccessful");

            }
        });

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);


        // StaggeredGrid 레이아웃을 사용한다
        //layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); //
        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);
        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeamListActivity.MyAdpater(myTeam, mContext);
        recyclerView.setAdapter(adapter);


    }
    public class WriteuserProject extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            myTeam.clear();

            GetTeamMember getTeamMember = new GetTeamMember();

            getTeamMember.execute(p_id);
        }

        public String doInBackground(String ...params)
        {
            try{

                String input_number = params[0];


                String url = "http://35.201.138.226/write_userProject.php?u_id=" + input_number + "&p_id=" + p_id + "&info=" +
                        "";

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
                        Toast.makeText(getApplicationContext(), "존재하지 않는 이메일입니다", Toast.LENGTH_SHORT).show(); // makeText를 한다.
                    }
                    else
                    {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i); // Object를 가져오고

                            String idfromServer = item.getString("u_id");

                            WriteuserProject writeuserProject = new WriteuserProject();

                            writeuserProject.execute(idfromServer);


                        }
                    }


                }catch(JSONException e){}
            }

        }
    }

    public class GetTeamMember extends AsyncTask<String,Void,String> {

        public String doInBackground(String ...params)
        {
            try{
                String input_pid = params[0]; // input email은 string [0]
                String url = "http://35.201.138.226/read_team_member.php?p_id=" + input_pid;

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
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i); // Object를 가져오고

                            String namefromServer = item.getString("name");
                            String emailfromServer = item.getString("email");
                            String numberfromServer = item.getString("number");
                            String uidfromServer = item.getString("u_id");

                            myTeam.add(0,new User(namefromServer,numberfromServer,emailfromServer));

                            Log.d("ddddd",numberfromServer);
                        }

                        adapter.notifyDataSetChanged();
                    }


                }catch(JSONException e){}
            }

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("RequestCod?",String.valueOf(requestCode));

        if(requestCode == AddMemberMsg) { // 이 경우가, 결국 Add Activity 를 갔다왔을 때의 경우다.
            if (resultCode == RESULT_OK) {
                // 여기서는 추가작업을 해주어야 한다.
                //String projectName = data.getStringExtra("Name");
                //String projectInfo = data.getStringExtra("Info");
                //String projectdueDate = data.getStringExtra("Date");
                //String projectIcon = data.getStringExtra("Icon");


                ////myTeam.add(0, new Room(projectIcon, projectName, projectInfo, projectdueDate));

                adapter.notifyDataSetChanged();

                // 이 부분에서 서버에 올려야한다!!!
                // We need to upload data to server
                // 서버에 올리고나면,  room_id가 부여될 것인데, 이 roomid를 다시 가져와서 객체에 넣어야한다.


                int serverResultid;

                // 여기다가 Async를 익명클래스나 이너클래스로 작성한다.  그리고 serverResultid를 통해 0번 객체의 id를 부여해야한다.
                // 중요!!!!

            } else if (resultCode == RESULT_CANCELED) {
                // 취소한 경우 아무것도 하지않아도 된다.
            } else {
                Log.d("Nothing", "What?");
            }
        }
    }



    class MyAdpater extends RecyclerView.Adapter {
        private Context context;
        private ArrayList<User> mItems;

        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        public MyAdpater(ArrayList<User> items, Context mContext) {
            mItems = items;
            context = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teamview, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(((ViewHolder)holder).imageView == null)
            {
                Log.d("dDddasdasda","Asdasdasda");
            }
            ((ViewHolder)holder).imageView.setImageResource(R.drawable.default_icon);
            ((ViewHolder)holder).member_name.setText(mItems.get(position).getName());
            ((ViewHolder)holder).member_num.setText(mItems.get(position).getPhonenum());
            ((ViewHolder)holder).member_email.setText(mItems.get(position).getEmail());


            setAnimation(((ViewHolder)holder).imageView, position);

        }



        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView member_name;
            public TextView member_num;
            public TextView member_email;


            public ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.image_member);
                member_name = (TextView) view.findViewById(R.id.member_name);
                member_num = (TextView) view.findViewById(R.id.member_num);
                member_email = (TextView) view.findViewById(R.id.member_email);


                view.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v)
                    {
                        // 그 채팅방으로 들어가는 기능이 필요하다.

                        // 전화 하는 기능이 필요하다

                        int pos = getPosition();

                        String tel = "tel:" + mItems.get(pos).getPhonenum();


                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE); // CALL_PHONE 확인하고

                            if(permissionResult == PackageManager.PERMISSION_DENIED) {
                                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(TeamListActivity.this);
                                    dialog.setTitle("권한이 필요합니다")
                                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"전화걸기\" 권한이 필요합니다. 계속 하시겠습니까?")
                                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                                    }
                                                }
                                            })
                                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Toast.makeText(TeamListActivity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .create()
                                            .show();
                                } else {
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                }
                            }

                            else
                            {
                                Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(tel));
                                startActivity(i);
                            }

                        }
                        else
                        {
                            Intent i = new Intent(Intent.ACTION_CALL,Uri.parse(tel));
                            startActivity(i);
                        }


                    }
                });

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
