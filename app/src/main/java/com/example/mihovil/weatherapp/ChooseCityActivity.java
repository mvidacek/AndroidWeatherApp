package com.example.mihovil.weatherapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCityActivity extends AppCompatActivity {

    @BindView(R.id.etNazivGrada) EditText etNazivGrada;
    @BindView(R.id.npdays) NumberPicker npKolikoDana;
    @BindView(R.id.btnConfirm) Button btnConfirm;

    private ConnectivityManager checkConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        ButterKnife.bind(this);

        checkConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        provijeriVezu();

        npKolikoDana.setMinValue(1);
        npKolikoDana.setMaxValue(7);
        npKolikoDana.setValue(3);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( etNazivGrada.getText().toString() != ""){
                    if(provijeriVezu()){
                        GetWeather(etNazivGrada.getText().toString());
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Morate unjeti naziv grada", Toast.LENGTH_LONG);
                }
            }
        });
    }

    private boolean provijeriVezu() {
        if(checkConnection.getActiveNetworkInfo() == null){
            Toast.makeText(this, "Aplikacija nema pristup internetu. \nAplikacija nece moci pristupiti potrebnim materijalima", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void GetWeather(String s) {
        Intent i = new Intent(this, ShowCityWeatherActivity.class);
        i.putExtra("city", etNazivGrada.getText().toString());
        i.putExtra("count", npKolikoDana.getValue());
        startActivity(i);
    }
}
