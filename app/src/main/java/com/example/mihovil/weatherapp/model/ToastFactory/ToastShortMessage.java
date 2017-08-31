package com.example.mihovil.weatherapp.model.ToastFactory;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mihovil on 31/08/2017.
 */

public class ToastShortMessage extends Notificationfactory {

    public ToastShortMessage(){}

    @Override
    public int getLength() {
        length = Toast.LENGTH_SHORT;
        return length;
    }

    public ToastShortMessage(Context context){
        super(context);
        length = Toast.LENGTH_SHORT;
    }

}
