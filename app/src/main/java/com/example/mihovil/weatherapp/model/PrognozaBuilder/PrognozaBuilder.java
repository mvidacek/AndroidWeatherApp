package com.example.mihovil.weatherapp.model.PrognozaBuilder;

/**
 * Created by Mihovil on 11/08/2017.
 */

public class PrognozaBuilder
{
    private String minTemp;
    private String maxTemp;
    private String description;
    private String cityName;
    private String date;

    public PrognozaBuilder minTemp (String newMinTemp){
        this.minTemp = newMinTemp;
        return this;
    }

    public PrognozaBuilder(){}

    public PrognozaBuilder maxTemp (String newMaxTemp){
        this.maxTemp = newMaxTemp;
        return this;
    }

    public PrognozaBuilder description (String newDescription){
        this.description = newDescription;
        return this;
    }

    public PrognozaBuilder cityName (String newCityName){
        this.cityName = newCityName;
        return this;
    }

    public PrognozaBuilder date (String newDate){
        this.date = newDate;
        return this;
    }

    public PrognozaBuilder(String minTemp, String maxTemp, String description, String cityName, String date)
    {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
        this.cityName = cityName;
        this.date = date;
    }

    public Prognoza createPrognoza()
    {
        return new Prognoza(minTemp, maxTemp, description, cityName, date);
    }

    public void setPrognoza(String minTemp, String maxTemp, String description, String cityName, String date)
    {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
        this.cityName = cityName;
        this.date = date;
    }

    public Prognoza createEmptyPrognoza(){
        return new Prognoza();
    }
}
