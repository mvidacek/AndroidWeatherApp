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

import com.example.mihovil.weatherapp.model.ConnectionState;
import com.example.mihovil.weatherapp.model.GetToastFactory;
import com.example.mihovil.weatherapp.model.Notificationfactory;
import com.example.mylibrary.DebugTrace;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCityActivity extends AppCompatActivity {

    @BindView(R.id.etNazivGrada)
    EditText etNazivGrada;
    @BindView(R.id.npdays)
    NumberPicker npKolikoDana;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    private static ConnectionState connState;
    private ConnectivityManager checkConnection;
    private GetToastFactory toastFactory = new GetToastFactory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);

        ButterKnife.bind(this);

        connectToService();

        checkConnection();

        setDaysLimit();

        addBtnListener();


        Notificationfactory message = toastFactory.getToast(Toast.LENGTH_LONG, getApplicationContext());
        message.writeToastMessage("placeholder text");
    }

    private void addBtnListener() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( etNazivGrada.getText().toString() != ""){
                    if(connState.isON()){
                        GetWeather(etNazivGrada.getText().toString());
                    }
                }else{
                    Notificationfactory message = toastFactory.getToast(Toast.LENGTH_LONG, getApplicationContext());
                    message.writeToastMessage("Morate unjeti naziv grada");
                }
            }
        });
    }

    private void setDaysLimit() {
        npKolikoDana.setMinValue(1);
        npKolikoDana.setMaxValue(7);
        npKolikoDana.setValue(3);
    }


    @DebugTrace
    private void connectToService() {
        checkConnection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        try{
            Thread.sleep(100);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }

        connState = ConnectionState.getInstance((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE));

        connState.showNetworkInfo(this);
    }

    private boolean checkConnection() {
        if(checkConnection.getActiveNetworkInfo() == null){
            Notificationfactory message = toastFactory.getToast(Toast.LENGTH_LONG, getApplicationContext());
            message.writeToastMessage("Aplikacija nema pristup internetu. \nAplikacija nece moci pristupiti potrebnim materijalima");
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
