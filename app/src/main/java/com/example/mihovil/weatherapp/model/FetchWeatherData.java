package com.example.mihovil.weatherapp.model;

import android.content.Context;

import com.example.mihovil.weatherapp.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mihovil on 26/1/2017.
 */
public class FetchWeatherData{

    private static final String ApiURL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&cnt=%s&lang=en";

    public static JSONObject getJson(Context context, String city, int count) {
        try {
            URL url = new URL(String.format(ApiURL, city, String.valueOf(count)));
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.addRequestProperty("x-api-key",
                    context.getString(R.string.weather_api_appID));

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);

            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            if (data.getInt("cod") != 200) {
                return null;
            }

            return data;
        } catch (Exception e) {
            return null;
        }
    }
}
