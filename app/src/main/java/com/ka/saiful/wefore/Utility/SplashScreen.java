package com.ka.saiful.wefore.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.ka.saiful.wefore.MainActivity;
import com.ka.saiful.wefore.R;

/**
 * Created by ASUS on 12/1/2017.
 */

public class SplashScreen extends Activity{

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            SplashScreen.this.startActivity(intent);
            SplashScreen.this.finish();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("first-launch", false)){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("first-launch", true);
            editor.commit();
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            this.startActivity(i);
            this.finish();
        }else{
            this.setContentView(R.layout.splash_screen);
            handler.sendEmptyMessageDelayed(0, 2000);
        }

    }
}
