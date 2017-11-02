package com.example.churl.teamproject_app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

/**
 * Created by churl on 2017-11-01.
 */

public class IntroActivity extends Activity{

    Handler h;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro_activity);

        h = new Handler();
        h.postDelayed(mrun,2000);

    }

    Runnable mrun = new Runnable() {
        @Override
        public void run() {
            Log.d("Runnable","why");
            Intent i = new Intent(IntroActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        }
    };

    public void onBackPressed()
    {
        super.onBackPressed();
        h.removeCallbacks(mrun);
    }

}
