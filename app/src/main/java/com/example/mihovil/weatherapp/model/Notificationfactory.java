package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mihovil on 31/08/2017.
 */

public abstract class Notificationfactory {

    protected Context context;

    protected int length;

    public abstract int getLength();

    public Notificationfactory(Context context){
        this.context = context;
    }

    public void writeToastMessage(String text){
        Toast.makeText(context, text, length).show();
    }

}
