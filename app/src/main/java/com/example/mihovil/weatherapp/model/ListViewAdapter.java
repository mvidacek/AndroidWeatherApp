package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mihovil.weatherapp.R;
import com.example.mihovil.weatherapp.model.PrognozaBuilder.Prognoza;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Mihovil on 26/1/2017.
 */
public class ListViewAdapter extends ArrayAdapter<Prognoza> {

    public ListViewAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public ListViewAdapter(Context context, int resource, List<Prognoza> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.listview_row_layout, null);
        }

        Prognoza p = getItem(position);

        if(p != null){
            TextView date = (TextView)v.findViewById(R.id.tvWeatherDate);
            TextView description = (TextView)v.findViewById(R.id.tvWeatherDescription);
            TextView minTemp = (TextView)v.findViewById(R.id.tvWeatherMinTemp);
            TextView maxtemp = (TextView)v.findViewById(R.id.tvWeatherMaxTemp);
            ImageView weatherIcon = (ImageView)v.findViewById(R.id.ivweather);

            weatherIcon.setImageResource(setImage(p.getDescription()));

            date.setText(p.getDate());
            description.setText(p.getDescription());
            minTemp.setText(parseTemp(p.getMinTemp()));
            maxtemp.setText(parseTemp(p.getMaxTemp()));
        }

        return v;
    }

    private String parseTemp(String temp) {

        int i = 0;
        try {
            i=(NumberFormat.getInstance().parse(temp)).intValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(i);
    }

    private int setImage(String description) {
        if(description.contains("cloud")){
            return R.drawable.cloudy;
        }else if(description.contains("rain")){
            return R.drawable.rainy;
        }else if(description.contains("snow")){
            return R.drawable.snowy;
        }else{
            return R.drawable.sunny;
        }
    }
}
