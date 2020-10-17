package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import java.io.IOException;

public interface WeatherService {
    public Weather getWeather(LocationService location) throws IOException;

    // public double getWeatherByCoords(int longitude, int latitude);
    // public double getWeatherByName(String city_name) throws IOException;
    // public double getWeatherByName(String city_name, String state_code);
    // public double getWeatherByName(String city_name, String state_code, String country_code);
}
