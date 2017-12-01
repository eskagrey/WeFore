package com.ka.saiful.wefore;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ka.saiful.wefore.Recycler.TListener;
import com.ka.saiful.wefore.Utility.DBHandler;
import com.ka.saiful.wefore.Utility.Model.Person;
import com.ka.saiful.wefore.Utility.SessionManager;
import com.ka.saiful.wefore.Weather.Adapter;
import com.ka.saiful.wefore.Weather.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ASUS on 11/28/2017.
 */

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private TextView tvCity;
    private RecyclerView rvWeather;
    List<Weather> weathers;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvUser = findViewById(R.id.tv_user);
        for(Person person : DBHandler.getInstance().getAllUser())
            tvUser.append(person.getName().toString());

        tvCity = findViewById(R.id.location);
        rvWeather = findViewById(R.id.recycler);
        weathers = new ArrayList<>();
        adapter = new Adapter(weathers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvWeather.setLayoutManager(layoutManager);
        rvWeather.setAdapter(adapter);

        rvWeather.addOnItemTouchListener(new TListener(this, rvWeather,
                new TListener.ClickListener(){
                    @Override
                    public void onClick(View view, int position) {
                        Weather weather = weathers.get(position);
                        Intent intent = new Intent(MainActivity.this, Detail.class);
                        intent.putExtra("info-weather", weather);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));

        String url = "https://andfun-weather.udacity.com/weather";

        try {
            String result = new FetchData().execute(url).get();

            JSONObject jsonObject = new JSONObject(result);

            JSONObject objectCity = jsonObject.getJSONObject("city");
            tvCity.setText(objectCity.getString("name"));

            JSONArray arrayData = jsonObject.getJSONArray("list");

            for (int i = 0; i < arrayData.length(); i++) {
                JSONObject object = arrayData.getJSONObject(i);

                Weather weather = new Weather();
                weather.setPressure(object.getDouble("pressure"));
                weather.setHumidity(object.getDouble("humidity"));
                JSONArray weatherArray = object.getJSONArray("weather");

                JSONObject weatherArrayObject = weatherArray.getJSONObject(0);
                weather.setMain(weatherArrayObject.getString("main"));
                weather.setDescription(weatherArrayObject.getString("description"));
                weathers.add(weather);
                Log.d(TAG, "onCreate: " + weather.toString());

                int deg = object.getInt("deg");
                JSONObject temp = object.getJSONObject("temp");

            }

            adapter.addAll(weathers);

            Log.d(TAG, "onCreate result : " + result);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    public class FetchData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result;
            String inputLine;

            try {
                URL myUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                result = stringBuilder.toString();

            } catch (IOException e) {
                result = null;
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    public void GoLogout(View view){
        finish();
        SessionManager.getInstance().clear();
    }

}
