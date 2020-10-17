package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    //private static Context context;
    public static final String EXTRA_MESSAGE = "com.github.TheodorSergeev.epfl_softeng_bootcamp";
    public static final String TEMPERATURE_MESSAGE = "temperatureInLocation";
    public static final String LOCATION_MESSAGE = "locationName";

    private static MyGeocoder geocoder;
    private static OpenWeatherLocationService weather_serv;
    private static Location location;
    private static Weather meteo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Remove this line of code once I learn about asynchronous operations!
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String openweather_api_key = getResources().getString(R.string.openweather_api_key);

        weather_serv = new OpenWeatherLocationService(openweather_api_key);
        geocoder = new MyGeocoder(MainActivity.this);

        try {
            location = new Location(MainActivity.this, geocoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayGreeting(View view) {
        Intent intent = new Intent(this, GreetingActivity.class);
        EditText editText = findViewById(R.id.mainTextField);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void displayWeather(View view) {
        Intent intent = new Intent(this, MeteoActivity.class);

        EditText locationNameVal = findViewById(R.id.locationTextField);
        String locationName = locationNameVal.getText().toString();
        intent.putExtra(LOCATION_MESSAGE, locationName);

        String curr_temp = "Couldn't retrieve temperature";

        try {
            location.setLocation(locationName);
            meteo = weather_serv.getWeather(location);
            double temp = meteo.getTemperature();
            curr_temp = String.valueOf(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        intent.putExtra(TEMPERATURE_MESSAGE, curr_temp);

        startActivity(intent);
    }
}