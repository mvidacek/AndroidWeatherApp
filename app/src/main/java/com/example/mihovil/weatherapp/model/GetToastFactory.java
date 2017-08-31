package com.example.mihovil.weatherapp.model;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Mihovil on 31/08/2017.
 */

public class GetToastFactory {

    public Notificationfactory getToast(int length, Context context){

        if(length == Toast.LENGTH_SHORT){
            return new ToastShortMessage(context);
        }
        else if(length == Toast.LENGTH_LONG){
            return new ToastLongMessage(context);
        }
        else{
            return new NullToastMessage(context);
        }
    }

    public Notificationfactory getToast(int length){

        if(length == Toast.LENGTH_SHORT){
            return new ToastShortMessage();
        }
        else if(length == Toast.LENGTH_LONG){
            return new ToastLongMessage();
        }
        else{
            return new NullToastMessage();
        }
    }
}
