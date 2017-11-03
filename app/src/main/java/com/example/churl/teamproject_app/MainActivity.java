package com.example.churl.teamproject_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private FloatingActionButton fab;

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    /*
        MainActivity에서 extra데이터를 가져와야 한다.
    */

    private static final int FirstMsg = 1; // Activity 전달에 쓰인다.
    private static final int ProjectMain = 2;

    ArrayList items = new ArrayList<Room>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab = (FloatingActionButton) findViewById(R.id.float_add);

        fab.setOnClickListener(new View.OnClickListener() {



            public void onClick(View v) {
                // 여기서 추가관련 부분을 작성하여야 한다.
                //  앞에다가 추가할 필요가 있다. 그리고 정렬은?

                Intent i = new Intent(MainActivity.this,AddActivity.class);

                startActivityForResult(i,FirstMsg);

                //items.add(new Room(R.drawable.logo,"자료구조설계",3));
                //items.add(0,new Room(R.drawable.logo,aa[i++],3));
                Log.d("Add","addSuccessful");

            }
        });

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);


        // StaggeredGrid 레이아웃을 사용한다
        //layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); //
        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);
        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdpater(items, mContext);
        recyclerView.setAdapter(adapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("RequestCod?",String.valueOf(requestCode));

        if(requestCode == FirstMsg) { // 이 경우가, 결국 Add Activity 를 갔다왔을 때의 경우다.
            if (resultCode == RESULT_OK) {
                // 여기서는 추가작업을 해주어야 한다.
                String projectName = data.getStringExtra("Name");
                String projectInfo = data.getStringExtra("Info");
                String projectdueDate = data.getStringExtra("Date");
                String projectIcon = data.getStringExtra("Icon");


                items.add(0, new Room(projectIcon, projectName, projectInfo, projectdueDate));

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
        else if (requestCode == ProjectMain)
        {

        }
    }

    class MyAdpater extends RecyclerView.Adapter {
        private Context context;
        private ArrayList<Room> mItems;

        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        public MyAdpater(ArrayList<Room> items, Context mContext) {
            mItems = items;
            context = mContext;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder)holder).imageView.setImageResource(mItems.get(position).image);
            ((ViewHolder)holder).textView.setText(mItems.get(position).imagetitle);

            setAnimation(((ViewHolder)holder).imageView, position);

        }



        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;
            public TextView textView;

            public ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.image);
                textView = (TextView) view.findViewById(R.id.imagetitle);

                view.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v)
                    {
                        // 그 채팅방으로 들어가는 기능이 필요하다.
                    Intent i = new Intent(MainActivity.this,ProjectMainActivity.class);

                    int pos = getPosition();


                    i.putExtra("RoomData",mItems.get(pos));

                    startActivityForResult(i,ProjectMain);

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
