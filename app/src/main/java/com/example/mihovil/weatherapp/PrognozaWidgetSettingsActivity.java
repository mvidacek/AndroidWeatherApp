package com.example.mihovil.weatherapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mihovil.weatherapp.model.VrijemeServis;
import com.example.mihovil.weatherapp.model.VrijemeWidgetProvider;

/**
 * Created by Mihovil on 28/01/2017.
 */

public class PrognozaWidgetSettingsActivity extends Activity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private String nazivGrada = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_settings);

        getNazivGrada();

        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().add(R.id
            .container, new PlaceholderFragment()).commit();
        }
    }

    private void getNazivGrada() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Naziv grada");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nazivGrada = input.getText().toString();
            }
        });

        builder.show();
    }

    public void radiSManagerom(View v){
        Intent intent = new Intent(VrijemeWidgetProvider.UPDATEWIDGET);
        intent.putExtra("nazivGrada", nazivGrada);
        pendingIntent = PendingIntent.getBroadcast(PrognozaWidgetSettingsActivity.this, 0, intent, 0);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar =  Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);

        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 6000, pendingIntent);
    }

    public void gasiManager(View v){
        if(alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    public void pokreniServis(View v){
        Intent intent = new Intent(PrognozaWidgetSettingsActivity.this, VrijemeServis.class);
        intent.putExtra("nazivGrada", nazivGrada);
        startService(intent);
    }

    public void zaustaviServis(View v){
        stopService(new Intent(PrognozaWidgetSettingsActivity.this, VrijemeServis.class));
    }

    public void uRedu(View v){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int wigID = AppWidgetManager.INVALID_APPWIDGET_ID;
        if(extras != null){
            wigID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, wigID);
        setResult(RESULT_OK, resultValue);
        finish();
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_widget_settings,
                    container, false);
            return rootView;
        }
    }
}
