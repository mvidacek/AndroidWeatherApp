package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mihovil on 26/01/2017.
 */

public class ApiManager {

    private static RetrofitInterface rInterface;
    private static final String Api_URL = "http://api.openweathermap.org/data/2.5/";
    WeatherData tempData;

    private static Retrofit retrofit;

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(Api_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static RetrofitInterface getRetrofitInterface(){
        RetrofitInterface retrofitInterface = null;
        try{
            if(retrofit == null){
                retrofit = getRetrofit();
            }
            retrofitInterface = retrofit.create(RetrofitInterface.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return retrofitInterface;
    }

    public static void getWeatherData(final Context context, String cityName, int count, final Callback<WeatherData> weatherDataCallBack){

        rInterface = getRetrofitInterface();

        Call<WeatherData> call = rInterface.getPrognoza(cityName, count);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(response.isSuccessful()){
                    weatherDataCallBack.onResponse(call, response);
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Toast.makeText(context, "Greška u dohvaćanju podataka!", Toast.LENGTH_LONG);
            }

        });
    }

}
