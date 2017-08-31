package com.example.mihovil.weatherapp;

import android.net.ConnectivityManager;

import com.example.mihovil.weatherapp.model.ConnectionState;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.mock;

/**
 * Created by Mihovil on 30/08/2017.
 */

public class Connectivitytest {

    @Mock
    ConnectivityManager connManager;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup(){
        connManager = mock(ConnectivityManager.class);
    }


    @Test
    public void runTest() {

        ConnectionState connState = ConnectionState.getInstance(connManager);
        Assert.assertFalse(connState.isON());

    }

}
