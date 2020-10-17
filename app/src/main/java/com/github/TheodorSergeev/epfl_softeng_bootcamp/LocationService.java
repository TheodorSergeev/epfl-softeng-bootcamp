package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import android.graphics.Path;
import android.location.Geocoder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public interface LocationService {
    // public void setDefaultLocation();
    public void setLocation(String location_name) throws IOException;

    public double getLatitude();
    public double getLongitude();
}