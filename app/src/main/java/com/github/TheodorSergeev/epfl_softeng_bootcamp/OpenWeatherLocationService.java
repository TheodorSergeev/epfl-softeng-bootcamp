package com.github.TheodorSergeev.epfl_softeng_bootcamp;

import android.content.Context;
import android.location.Geocoder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

public final class OpenWeatherLocationService implements WeatherService {
    private static final String SERVICE_URL_STR = "https://api.openweathermap.org/data/2.5/weather?";
    private static String openweather_api_key;

    private static final int READ_TIMEOUT    = 3000; // in ms
    private static final int CONNECT_TIMEOUT = 3000; // in ms
    private static final int MAX_STR_LENGTH  = 3000; // number of characters

    public OpenWeatherLocationService(String _openweather_api_key) {
        openweather_api_key = _openweather_api_key;
    }

    // Converts the contents of an InputStream to a String
    private String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }

    private String fetchWeatherData(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;

        try {
            connection = (HttpsURLConnection) url.openConnection();

            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECT_TIMEOUT);

            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");

            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);

            // Open communications link (network traffic occurs here).
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Retrieve the response body as an InputStream.
            stream = connection.getInputStream();

            if (stream != null)
            {
                // Converts Stream to String with max length of 500.
                result = readStream(stream, MAX_STR_LENGTH);
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    private double parseTemperature(JSONObject json) throws JSONException {
        return json.getJSONObject("main").getDouble("temp");
    }

    @Override
    public Weather getWeather(LocationService location) throws IOException {
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        String api_call = SERVICE_URL_STR + "lat=" + latitude + "&lon=" + longitude + "&appid=" + openweather_api_key;

        URL url = new URL(api_call);
        String weather_json = fetchWeatherData(url);

        JSONObject json_object = null;
        try {
            json_object = (JSONObject) new JSONTokener(weather_json).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        double temperature = -1.23;
        try {
            temperature = parseTemperature(json_object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Log.i("Fedor", String.valueOf(temperature));
        return new Weather(temperature);
    }
}