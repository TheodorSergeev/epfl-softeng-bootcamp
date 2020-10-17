package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Pair;

import java.io.IOException;

public class MyGeocoder implements GeocodingService {
    private static Geocoder geocoder;

    public MyGeocoder(Context context) {
        geocoder = new Geocoder(context);
    }

    @Override
    public String getNameByCoords(double lat, double lon) throws IOException {
        // not implemented!
        return null;
    }

    @Override
    public Pair<Double, Double> getCoordsByName(String location_name) throws IOException {
        Address addr = geocoder.getFromLocationName(location_name, 1).get(0);
        return new Pair<Double, Double>(addr.getLongitude(), addr.getLatitude());
    }
}
