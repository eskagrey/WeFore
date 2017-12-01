package com.ka.saiful.wefore.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ka.saiful.wefore.R;
import com.ka.saiful.wefore.Utility.DBHandler;
import com.ka.saiful.wefore.Utility.Model.Person;

/**
 * Created by ASUS on 11/28/2017.
 */

public class Register extends AppCompatActivity {

    private EditText Name, Email, Pass;
    private final String TAG = Register.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Name = findViewById(R.id.et_name);
        Email = findViewById(R.id.et_email);
        Pass = findViewById(R.id.et_pass);

    }

    public void openLogin(View view) {
        finish();
    }

    public void doRegister(View view) {
        DBHandler databaseHandler = DBHandler.getInstance();
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String pass = Pass.getText().toString();
        Person person = new Person(name, email, pass);

        if (name.isEmpty()) {
            Name.setError("Email can't empty");
            Name.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            Email.setError("Email can't empty");
            Email.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            Pass.setError("password can't empty");
            Pass.requestFocus();
            return;
        }
        Log.d(TAG, "doRegister: " + person.toString());
        databaseHandler.addUser(person);
        Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Login.class));
        finish();
    }
}
