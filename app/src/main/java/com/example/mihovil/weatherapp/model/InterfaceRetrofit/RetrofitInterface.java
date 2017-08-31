package com.example.mihovil.weatherapp.model.InterfaceRetrofit;

import com.example.mihovil.weatherapp.model.RetrofitClasses.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mihovil on 26/01/2017.
 */

//http://api.openweathermap.org/data/2.5/forecast/daily?q=zagreb&units=metric&cnt=3&lang=en&appid=4b014e050376eb5193e72a52b7785d29

public interface RetrofitInterface {
    @GET("forecast/daily?appid=4b014e050376eb5193e72a52b7785d29&units=metric")
    Call<WeatherData> getPrognoza(@Query("q") String cityName, @Query("cnt") int count);

}
