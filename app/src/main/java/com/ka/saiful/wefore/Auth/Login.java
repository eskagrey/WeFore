package com.ka.saiful.wefore.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ka.saiful.wefore.MainActivity;
import com.ka.saiful.wefore.R;
import com.ka.saiful.wefore.Utility.DBHandler;
import com.ka.saiful.wefore.Utility.Model.Person;
import com.ka.saiful.wefore.Utility.SessionManager;

/**
 * Created by ASUS on 11/28/2017.
 */

public class Login extends AppCompatActivity {

    private final String TAG = Login.class.getSimpleName();
    private EditText etEmail, etPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_pass);

        if (SessionManager.getInstance().isLogin()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        for (Person person : DBHandler.getInstance().getAllUser()) {
            Log.d(TAG, "onCreate: " + person.toString());
        }
    }

    public void openRegister(View view) {
        startActivity(new Intent(this, Register.class));
    }

    public void doLogin(View view) {

        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Email can't empty");
            etEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            etPass.setError("password can't empty");
            etPass.requestFocus();
            return;
        }

        if (DBHandler.getInstance().checkUser(email, pass)) {
            SessionManager.getInstance().setLogin(true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show();
    }

}
