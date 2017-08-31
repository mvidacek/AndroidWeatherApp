package com.example.mihovil.weatherapp.model.PrognozaBuilder;

/**
 * Created by Mihovil on 26/1/2017.
 */
public class Prognoza implements PrototypePtrn {

    private String minTemp;
    private String maxTemp;
    private String description;
    private String cityName;
    private String date;

    public Prognoza(String minTemp, String maxTemp, String description, String cityName, String date) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
        this.cityName = cityName;
        this.date = date;
    }

    public Prognoza(){

    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return date + ": " + description + "; minTemp: " + minTemp + "; maxTemp: " + maxTemp;
    }


    @Override
    public PrototypePtrn getClone() {

        PrognozaBuilder pb = new PrognozaBuilder(this.minTemp, this.maxTemp, this.description, this.cityName, this.date);

        return pb.createPrognoza();
    }
}
