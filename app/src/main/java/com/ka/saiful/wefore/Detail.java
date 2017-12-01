package com.ka.saiful.wefore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ka.saiful.wefore.Weather.Weather;

/**
 * Created by ASUS on 11/28/2017.
 */

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView pressure = findViewById(R.id.tv_pressure_content);
        TextView humidity = findViewById(R.id.tv_humidity_content);
        TextView main = findViewById(R.id.tv_main_content);
        TextView desc = findViewById(R.id.tv_description_content);

        Weather cuaca = getIntent().getParcelableExtra("info-weather");
        pressure.setText(String.valueOf(cuaca.getPressure()));
        humidity.setText(String.valueOf(cuaca.getHumidity()));
        main.setText(cuaca.getMain());
        desc.setText(cuaca.getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.info:
                new AlertDialog.Builder(this)
                        .setTitle("About Creator")
                        .setMessage("Saiful Kurniawan A.\nCopyright 2017")
                        .show();
                break;
        }
        return true;
    }

}

