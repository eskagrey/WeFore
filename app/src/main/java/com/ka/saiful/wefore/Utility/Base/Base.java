package com.ka.saiful.wefore.Utility.Base;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ka.saiful.wefore.Auth.Login;
import com.ka.saiful.wefore.MainActivity;
import com.ka.saiful.wefore.Utility.DBHandler;
import com.ka.saiful.wefore.Utility.SessionManager;

/**
 * Created by ASUS on 11/28/2017.
 */

public class Base extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferences user = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SessionManager.init(this);
        DBHandler.init(this);

        boolean LoggedIn = user.getBoolean("LoggedIn", false);
        if (LoggedIn){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
