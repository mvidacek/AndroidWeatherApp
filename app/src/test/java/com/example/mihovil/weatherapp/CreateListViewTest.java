package com.example.mihovil.weatherapp;

import com.example.mihovil.weatherapp.model.Prognoza;
import com.example.mihovil.weatherapp.model.PrognozaBuilder;
import com.example.mihovil.weatherapp.model.Temp;
import com.example.mihovil.weatherapp.model.Weather;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Mihovil on 16/08/2017.
 */

public class CreateListViewTest implements TestStrategy {


    @Mock
    Temp tempMock;

    @Mock
    Weather tempWeather;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){
        tempMock = mock(Temp.class);
        tempWeather = mock(Weather.class);

        when(tempMock.getMin()).thenReturn(12.0d);
        when(tempMock.getMax()).thenReturn(18.0d);
        when(tempWeather.getDescription()).thenReturn("Zagreb");

        prognozaList = new ArrayList<>();
    }

    private java.util.List<Prognoza> prognozaList;

    @Test
    public void runTest(){
        prepareList("Zagreb", 1);
        Assert.assertTrue(!prognozaList.isEmpty());
    }

    private void prepareList(String city, int count) {
        for(int i = 0; i < count; i++){
            String date = getDate(i);
            String minTemp = tempMock.getMin().toString();
            String maxTemp = tempMock.getMax().toString();
            String desc = tempWeather.getDescription();
            addToList(city, minTemp, maxTemp, desc, date);
        }
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

    private void addToList(String cityName, String minTemp, String maxTemp, String desc, String date) {

        PrognozaBuilder pb = new PrognozaBuilder(minTemp, maxTemp, desc, cityName, date);

        Prognoza p = pb.createPrognoza();

        prognozaList.add(p);
    }

}
