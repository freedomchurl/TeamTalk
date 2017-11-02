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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.float_add);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 여기서 추가관련 부분을 작성하여야 한다.
            }
        });

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        ArrayList items = new ArrayList<Item>();

        items.add(new Item(R.drawable.intro, "aaa"));
        items.add(new Item(R.drawable.intro, "aaa"));
        items.add(new Item(R.drawable.intro, "aaa"));
        items.add(new Item(R.drawable.intro, "aaa"));

        // StaggeredGrid 레이아웃을 사용한다
        //layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); //
        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);
        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdpater(items, mContext);
        recyclerView.setAdapter(adapter);


    }


    class MyAdpater extends RecyclerView.Adapter {
        private Context context;
        private ArrayList<Item> mItems;

        // Allows to remember the last item shown on screen
        private int lastPosition = -1;

        public MyAdpater(ArrayList<Item> items, Context mContext) {
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
