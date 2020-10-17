package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import android.util.Pair;

import java.io.IOException;

public interface GeocodingService {
    public Pair<Double,Double> getCoordsByName(String loc_name) throws IOException;
    public String getNameByCoords(double lon, double lat) throws IOException;
}
