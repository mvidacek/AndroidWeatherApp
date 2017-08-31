package com.example.mihovil.weatherapp.model.RetrofitClasses;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mihovil on 26/01/2017.
 */

public class WeatherData {

    private City city;
    private String cod;
    private Double message;
    private Integer cnt;
    private java.util.List<List> list = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<List> getList() {
        return list;
    }

    public void setList(java.util.List<List> list) {
        this.list = list;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}