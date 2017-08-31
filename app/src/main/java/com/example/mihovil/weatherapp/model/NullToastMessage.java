package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mihovil on 31/08/2017.
 */

public class NullToastMessage extends Notificationfactory {
    @Override
    public int getLength() {
        length = Toast.LENGTH_LONG;
        return length;
    }

    public NullToastMessage(Context context){
        super(context);
        length = Toast.LENGTH_LONG;
    }

    @Override
    public void writeToastMessage(String text) {
        text = "Neuspjelo stvaranje Toast poruke!";
        super.writeToastMessage(text);
    }
}
