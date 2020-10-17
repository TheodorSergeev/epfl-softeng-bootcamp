package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MeteoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        Intent intent = getIntent();

        String loc_name = intent.getStringExtra(MainActivity.LOCATION_MESSAGE);
        TextView locationView = findViewById(R.id.meteoTitleTextView);
        locationView.setText(getResources().getString(R.string.meteo_main_text) + " " + loc_name);

        String temp_str = intent.getStringExtra(MainActivity.TEMPERATURE_MESSAGE);
        TextView tempView = findViewById(R.id.tempTextView);
        tempView.setText(temp_str);
    }
}