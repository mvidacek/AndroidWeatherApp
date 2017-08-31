package com.example.mihovil.weatherapp.model.ToastFactory;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mihovil on 31/08/2017.
 */

public class ToastLongMessage extends Notificationfactory {

    public  ToastLongMessage(){}

    @Override
    public int getLength() {
        length = Toast.LENGTH_LONG;
        return length;
    }

    public ToastLongMessage(Context context){
        super(context);
        length = Toast.LENGTH_LONG;
    }
}
