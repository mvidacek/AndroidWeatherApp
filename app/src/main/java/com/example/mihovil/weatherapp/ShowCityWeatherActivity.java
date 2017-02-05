package com.example.mihovil.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.weatherapp.model.ApiManager;
import com.example.mihovil.weatherapp.model.City;
import com.example.mihovil.weatherapp.model.FetchWeatherData;
import com.example.mihovil.weatherapp.model.ListViewAdapter;
import com.example.mihovil.weatherapp.model.Prognoza;
import com.example.mihovil.weatherapp.model.RetrofitInterface;
import com.example.mihovil.weatherapp.model.WeatherData;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowCityWeatherActivity extends AppCompatActivity {

    private WeatherData model;

    @BindView(R.id.listViewPrognoza)
    ListView weatherView;
    @BindView(R.id.tvNazivGrada)
    TextView tvCityName;

    Handler threadHandler;
    private List<Prognoza> listWeatherReport = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_city_weather);

        ButterKnife.bind(this);
        Intent i = getIntent();

        threadHandler = new Handler();

        tvCityName.setText(i.getStringExtra("city"));

        final String cityName = i.getStringExtra("city");
        final int count = i.getIntExtra("count", 3);

        RetrofitInterface rInterface = ApiManager.getRetrofitInterface();

        Call<WeatherData> call = rInterface.getPrognoza(cityName, count);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(response.isSuccessful()){
                    WeatherData responseBody = (WeatherData) response.body();
                    model = response.body();
                    pripremiListu(cityName, count);
                    startListView();
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Greška u dohvaćanju podataka!", Toast.LENGTH_LONG);
            }

        });

    }

    private void pripremiListu(String cityName, int count) {

        for(int i = 0; i < count; i++){
            String date = getDate(i);
            String minTemp = String.valueOf(model.getList().get(i).getTemp().getMin());
            String maxTemp = String.valueOf(model.getList().get(i).getTemp().getMax());
            String desc = model.getList().get(i).getWeather().get(0).getDescription();
            listWeatherReport.add(new Prognoza(minTemp, maxTemp, desc, cityName, date));
        }

    }

    private void startListView() {
        weatherView.setAdapter(new ListViewAdapter(this, R.layout.listview_row_layout, listWeatherReport));

        weatherView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object o = weatherView.getItemAtPosition(position);
                Prognoza p = (Prognoza)o;
                Intent startYoutube = new Intent(getApplicationContext(), ShowYoutubeVideoActivity.class);
                startYoutube.putExtra("keywords", p.getDescription() + " " + p.getCityName());
                startActivity(startYoutube);
            }
        });
    }

    private String getDate(int daysToJump) {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, daysToJump);
        date = sdf.format(c.getTime());
        return date;
    }
}
