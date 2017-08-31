package com.example.mihovil.weatherapp.model.widgetService;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Mihovil on 28/01/2017.
 */

public class VrijemeServis extends IntentService {

    private boolean cancel = false;
    public VrijemeServis(){super("Vremenska prognoza servis");}


    @Override
    protected void onHandleIntent(Intent intent) {
        while (!cancel){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent i = new Intent(VrijemeWidgetProvider.UPDATEWIDGET);
            getApplication().sendBroadcast(i);
        }
    }

    @Override
    public void onDestroy() {
        cancel = true;
        super.onDestroy();
    }
}
