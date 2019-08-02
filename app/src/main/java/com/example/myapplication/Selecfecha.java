package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

public class Selecfecha extends Activity {

String lafechaseleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecfecha);

        CalendarView calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            public void onSelectedDayChange(@NonNull CalendarView calendarView, int anioFecha, int mesFecha, int diaFecha) {
                lafechaseleccionada = diaFecha + "/" + (mesFecha + 1) + "/" + anioFecha;
                finish();
            }
        });

    }

    public void finish(){

        Intent valorRetornado = new Intent();
        valorRetornado.putExtra("retorno",lafechaseleccionada);
        setResult(RESULT_OK,valorRetornado);
        super.finish();
    }


}