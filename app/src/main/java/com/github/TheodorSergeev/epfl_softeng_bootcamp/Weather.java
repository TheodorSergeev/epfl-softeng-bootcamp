package com.github.TheodorSergeev.epfl_softeng_bootcamp;

public class Weather {
    // private and additional functions will be required for checking the legality of new arguments
    private double temperature = 0.0; // in Kelvin

    public Weather(double temp) {
        temperature = temp;
    }

    public void setWeather(double temp) {
        temperature = temp;
    }

    public double getTemperature() {
        return temperature;
    }
}
