package com.example.mihovil.weatherapp;

import com.example.mihovil.weatherapp.model.InterfaceRetrofit.ApiManager;
import com.example.mihovil.weatherapp.model.InterfaceRetrofit.RetrofitInterface;
import com.example.mihovil.weatherapp.model.RetrofitClasses.WeatherData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mihovil on 13/08/2017.
 */
public class getRetrofitINterfaceTest implements TestStrategy {



    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){

    }


    @Test
    public void runTest() {
        RetrofitInterface rInterface = ApiManager.getRetrofitInterface();
        String city = "Zagreb";
        int count = 3;
        Call<WeatherData> call =  rInterface.getPrognoza(city, count);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                assertTrue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                assertTrue(false);
            }
        });
    }

}