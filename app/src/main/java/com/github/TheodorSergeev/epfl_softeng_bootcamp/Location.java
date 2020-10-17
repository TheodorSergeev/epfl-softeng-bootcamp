package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.util.Log;
import android.util.Pair;

import androidx.core.app.ActivityCompat;

import java.io.IOException;


public class Location implements LocationService {
    // location coordinates
    private double longitude;
    private double latitude;
    private String name;

    private static final double DEFAULT_LOCATION_LATITUDE = 46.5197;
    private static final double DEFAULT_LOCATION_LONGITUDE = 6.6323;
    private static final String DEFAULT_LOCATION_NAME = "Lausanne";

    GeocodingService geocoder;

    public Location(Context context, GeocodingService geocod_serv) throws IOException {
        geocoder = geocod_serv;

        LocationManager loc_manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria crit = new Criteria();
        crit.setPowerRequirement(Criteria.POWER_MEDIUM);
        String provider = loc_manager.getBestProvider(crit, true);
        provider = null;

        Log.e("Fedor", "todo: fix provider");

        if (provider != null) {
            android.location.Location last_known_loc = loc_manager.getLastKnownLocation(provider);

            latitude = last_known_loc.getLatitude();
            longitude = last_known_loc.getLongitude();
            name = geocoder.getNameByCoords(latitude, longitude);
        } else {
            Log.e("Fedor", "null provider");
            setDefaultLocation();
        }
    }

    public void setDefaultLocation() {
        latitude  = DEFAULT_LOCATION_LATITUDE;
        longitude = DEFAULT_LOCATION_LONGITUDE;
        name      = DEFAULT_LOCATION_NAME;
    }

    @Override
    public void setLocation(String loc_name) throws IOException {
        try {
            Pair<Double, Double> coords = geocoder.getCoordsByName(loc_name);

            name = loc_name;
            latitude  = coords.first;
            longitude = coords.second;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }
}
