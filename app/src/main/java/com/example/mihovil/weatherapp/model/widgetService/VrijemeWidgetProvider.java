package com.example.mihovil.weatherapp.model.widgetService;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.mihovil.weatherapp.R;
import com.example.mihovil.weatherapp.model.InterfaceRetrofit.ApiManager;
import com.example.mihovil.weatherapp.model.InterfaceRetrofit.RetrofitInterface;
import com.example.mihovil.weatherapp.model.PrognozaBuilder.Prognoza;
import com.example.mihovil.weatherapp.model.RetrofitClasses.WeatherData;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mihovil on 28/01/2017.
 */

public class VrijemeWidgetProvider extends AppWidgetProvider {

    public static final String UPDATEWIDGET =  "VrijemeWidgetProvider";
    private String nazivGrada = "dubrovnik";

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        osvjezi(context, appWidgetManager, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(UPDATEWIDGET.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), VrijemeWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            nazivGrada = intent.getStringExtra("nazivGrada");
            osvjezi(context, appWidgetManager, appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    private void osvjezi(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

        String cityName = "zagreb";
        int count = 1;

        RetrofitInterface rInterface = ApiManager.getRetrofitInterface();

        Call<WeatherData> call = rInterface.getPrognoza(nazivGrada, count);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if(response.isSuccessful()){
                    WeatherData responseBody = (WeatherData) response.body();
                    WeatherData model = response.body();
                    Prognoza p = pripremiPrognozu(nazivGrada, 1, model);
                    apdejtWidget(context, appWidgetManager, appWidgetIds, p);
                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Toast.makeText(context, "Vremenska prognoza Error", Toast.LENGTH_LONG);
            }
        });
    }

    private void apdejtWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Prognoza p) {
        for(int i = 0; i < appWidgetIds.length; i++){
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.prognoza_widget);

            remoteViews.setTextViewText(R.id.tvNazivGradaWidget, p.getCityName());
            remoteViews.setImageViewResource(R.id.ivWeatherWidget, setImage(p.getDescription()));
            remoteViews.setTextViewText(R.id.tvWeatherDescriptionWidget, p.getDescription());
            remoteViews.setTextViewText(R.id.tvWeatherDateWidget, p.getDate());
            remoteViews.setTextViewText(R.id.tvWeatherMaxTempWidget, p.getMaxTemp());
            remoteViews.setTextViewText(R.id.tvWeatherMinTempWidget, p.getMinTemp());
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
    }

    private int setImage(String description) {
        if (description.contains("cloud")) {
            return R.drawable.cloudy;
        } else if (description.contains("rain")) {
            return R.drawable.rainy;
        } else if (description.contains("snow")) {
            return R.drawable.snowy;
        } else {
            return R.drawable.sunny;
        }
    }

    private Prognoza pripremiPrognozu(String imeGrada, int count, WeatherData model) {
        List<Prognoza> lista = new ArrayList<>();

        String date = getDate();
        String minTemp = parseTemp(String.valueOf(model.getList().get(0).getTemp().getMin()));
        String maxTemp = parseTemp(String.valueOf(model.getList().get(0).getTemp().getMax()));
        String desc = model.getList().get(0).getWeather().get(0).getDescription();
        lista.add(new Prognoza(minTemp, maxTemp, desc, imeGrada, date));

        return  lista.get(0);
    }

    private String parseTemp(String temp) {

        int i = 0;
        try {
            i=((Number) NumberFormat.getInstance().parse(temp)).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(i);
    }

    private String getDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");

        String date = sdf.format(new Date());

        return date;
    }
}
