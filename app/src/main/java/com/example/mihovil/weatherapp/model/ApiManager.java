package com.example.mihovil.weatherapp.model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mihovil on 26/01/2017.
 */

public class ApiManager {

    private static RetrofitInterface rInterface;
    private static final String Api_URL = "http://api.openweathermap.org/data/2.5/";

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

}
