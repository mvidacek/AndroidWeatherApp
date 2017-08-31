package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Mihovil on 10/08/2017.
 */

public class ConnectionState {

    private static ConnectionState instance = null;

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
            Toast.makeText(context, "You are connected to mobile network!", Toast.LENGTH_SHORT);
        }
        else if (checkConnection.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
            Toast.makeText(context, "You are connected to WIFI!", Toast.LENGTH_SHORT);
        }
        else{
            Toast.makeText(context, "You are not connected to the internet!\n Please enable connection!", Toast.LENGTH_SHORT);
        }
    }
}
