package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.mihovil.weatherapp.model.ToastFactory.GetToastFactory;
import com.example.mihovil.weatherapp.model.ToastFactory.Notificationfactory;

/**
 * Created by Mihovil on 10/08/2017.
 */

public class ConnectionState {

    private static ConnectionState instance = null;

    private static GetToastFactory toastFactory = new GetToastFactory();

    private ConnectionState(ConnectivityManager manager){
        checkConnection = manager;
    }

    public static ConnectionState getInstance(ConnectivityManager manager){
        if(instance == null){
            instance = new ConnectionState(manager);
        }
        return instance;
    }

    private ConnectivityManager checkConnection;

    public void startConnection(ConnectivityManager manager){
        checkConnection = manager;
    }

    public boolean isON() {
        NetworkInfo networkState = checkConnection.getActiveNetworkInfo();
        if(networkState != null){
            if(networkState.isConnected()){
                return true;
            }
        }
        return false;
    }

    public void showNetworkInfo(Context context) {
        if(checkConnection.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()){
            displayMessage("You are connected to mobile network!", context);
        }
        else if (checkConnection.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
            displayMessage("You are connected to WIFI!", context);
        }
        else{
            displayMessage("You are not connected to the internet!\n Please enable connection!", context);
        }
    }

    private void displayMessage(String s, Context context) {
        Notificationfactory message = toastFactory.getToast(Toast.LENGTH_SHORT, context);
        message.writeToastMessage(s);
    }
}
