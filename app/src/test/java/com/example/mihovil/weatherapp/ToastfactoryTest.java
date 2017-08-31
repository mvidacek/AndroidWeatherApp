package com.example.mihovil.weatherapp;

import android.widget.Toast;

import com.example.mihovil.weatherapp.model.ToastFactory.GetToastFactory;
import com.example.mihovil.weatherapp.model.ToastFactory.Notificationfactory;
import com.example.mihovil.weatherapp.model.ToastFactory.NullToastMessage;
import com.example.mihovil.weatherapp.model.ToastFactory.ToastLongMessage;
import com.example.mihovil.weatherapp.model.ToastFactory.ToastShortMessage;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Mihovil on 31/08/2017.
 */

public class ToastfactoryTest implements TestStrategy {

    @Override
    public void setup() {

    }

    @Test
    public void runTestLong() {
        GetToastFactory toastFactory = new GetToastFactory();
        Notificationfactory message = toastFactory.getToast(Toast.LENGTH_LONG);
        Assert.assertTrue(message instanceof ToastLongMessage);
    }

    @Test
    public void runTestNull() {
        GetToastFactory toastFactory = new GetToastFactory();
        Notificationfactory message = toastFactory.getToast(156);
        Assert.assertTrue(message instanceof NullToastMessage);
    }

    @Test
    @Override
    public void runTest() {
        GetToastFactory toastFactory = new GetToastFactory();
        Notificationfactory message = toastFactory.getToast(Toast.LENGTH_SHORT);
        Assert.assertTrue(message instanceof ToastShortMessage);
    }
}
