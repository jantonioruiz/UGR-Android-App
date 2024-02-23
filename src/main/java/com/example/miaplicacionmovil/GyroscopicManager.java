package com.example.miaplicacionmovil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/*
Clase usada para gestionar el giroscopio, pasando como parámetro el contexto
de la activity que la instancia
 */
public class GyroscopicManager implements SensorEventListener {
    private final Context context;
    private final SensorManager sensorManager;
    private final Sensor gyroscope;
    private final float umbralGiro = 5f;

    public GyroscopicManager(Context context) {
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (gyroscope != null) {
            // Se ha detectado un giroscopio...
            Log.d("GyroscopicManager", "Registrando listener del giroscopio...");
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void cerrarAplicacion() {
        new AlertDialog.Builder(context)
                .setTitle("Cierre de aplicación")
                .setMessage("¿Desea cerrar la aplicación?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Finaliza la actividad actual
                        ((Activity) context).finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float rotationY = event.values[1];
        if (rotationY > umbralGiro) {
            cerrarAplicacion();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No necesariamente
    }
}
