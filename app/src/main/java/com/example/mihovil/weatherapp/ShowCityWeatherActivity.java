package com.example.mihovil.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mihovil.weatherapp.model.InterfaceRetrofit.ApiManager;
import com.example.mihovil.weatherapp.model.InterfaceRetrofit.RetrofitInterface;
import com.example.mihovil.weatherapp.model.ListViewAdapter;
import com.example.mihovil.weatherapp.model.PrognozaBuilder.Prognoza;
import com.example.mihovil.weatherapp.model.PrognozaBuilder.PrognozaBuilder;
import com.example.mihovil.weatherapp.model.RetrofitClasses.WeatherData;
import com.example.mihovil.weatherapp.model.ToastFactory.GetToastFactory;
import com.example.mihovil.weatherapp.model.ToastFactory.Notificationfactory;
import com.example.mylibrary.DebugTrace;

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

public class ShowCityWeatherActivity extends AppCompatActivity {

    private WeatherData model;
    private String cityName;
    private int count;
    private GetToastFactory toastFactory = new GetToastFactory();

    @BindView(R.id.listViewPrognoza)
    ListView weatherView;
    @BindView(R.id.tvNazivGrada)
    TextView tvCityName;

    private List<Prognoza> listWeatherReport = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_city_weather);

        ButterKnife.bind(this);
        Intent chooseCityIntent = getIntent();

        cityName = chooseCityIntent.getStringExtra("city");
        if(cityName == null || cityName.equals("")){
            finish();
        }
        count = chooseCityIntent.getIntExtra("count", 3);

        RetrofitInterface rInterface = ApiManager.getRetrofitInterface();

        Callback weatherDataCallBack = createCallBack();

        ApiManager.getWeatherData(this, cityName, count, weatherDataCallBack);
    }

    @DebugTrace
    private Callback createCallBack() {

        Callback weatherDataCallBack = new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                model = (WeatherData) response.body();
                prepareList(cityName, count);
                startListView();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Notificationfactory message = toastFactory.getToast(Toast.LENGTH_LONG, getApplicationContext());
                message.writeToastMessage("Neuspjeli poziv web servisa!");
                try {
                    Thread.sleep(3000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                finish();
            }
        };
        return weatherDataCallBack;
    }

    private void prepareList(String cityName, int count) {
        tvCityName.setText(model.getCity().getName());
        String date, minTemp, maxTemp, desc;

        for(int i = 0; i < count; i++){
            if(i > 0){
                if(model.getList().get(i) == model.getList().get(i-1)){
                    addToList((Prognoza) listWeatherReport.get(listWeatherReport.size()-1).getClone());
                    continue;
                }
            }
            date = getDate(i);
            minTemp = String.valueOf(model.getList().get(i).getTemp().getMin());
            maxTemp = String.valueOf(model.getList().get(i).getTemp().getMax());
            desc = model.getList().get(i).getWeather().get(0).getDescription();

            PrognozaBuilder pb = new PrognozaBuilder(minTemp, maxTemp, desc, cityName, date);

            Prognoza p = pb.createPrognoza();

            addToList(p);
        }
    }

    private void addToList(Prognoza p) {
        listWeatherReport.add(p);
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
