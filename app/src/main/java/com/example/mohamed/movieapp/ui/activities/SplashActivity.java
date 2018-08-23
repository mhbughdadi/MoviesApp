package com.example.mohamed.movieapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.movieapp.R;
import com.example.mohamed.movieapp.utils.Utils;

/**
 * Created by Mohamed on 12/1/2016.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Handler handler=new Handler();
        handler .postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in;
                if(Utils.isConnectionOpen(SplashActivity.this)) {
                    in = new Intent(SplashActivity.this, MainActivity.class);


                }
                else{
                    in=new Intent(SplashActivity.this, FavorateActivity.class);
                }
                startActivity(in);

            }
        },1000);

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
