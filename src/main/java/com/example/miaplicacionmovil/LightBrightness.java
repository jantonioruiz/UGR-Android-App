package com.example.miaplicacionmovil;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class LightBrightness implements SensorEventListener {
    private static Context context;
    private final SensorManager sensorManager;
    private final Sensor sensor;

    boolean isAuto = false;

    private final Activity activity;

    public LightBrightness(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (sensor != null) {
            // Se ha detectado el sensor de luz ambiental...
            Log.d("LightBrightnessManager", "Registrando listener del sensor de luz...");
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void setAuto(boolean auto) {
        this.isAuto = auto;

        if (!Settings.System.canWrite(context)){
            GlobalVariables.unaVez = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int valor_brillo;
        float porcentaje;

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            if(GlobalVariables.auto) {
                if (event.values[0] > 5000) {
                    permission();
                    if (Settings.System.canWrite(context)) {
                        if (event.values[0] > 40000) {
                            setBrightness(255);
                        } else {
                            porcentaje = (event.values[0] - 5000) / (40000 - 5000);
                            valor_brillo = Math.round(100 + porcentaje * (40000 - 5000));
                            setBrightness(valor_brillo);
                        }
                    }

                } else if (event.values[0] < 5000 && event.values[0] > 1000){
                    permission();
                    if (Settings.System.canWrite(context)) {
                        porcentaje = (event.values[0] - 1000) / (5000 - 1000);
                        valor_brillo = Math.round(50 + porcentaje * (5000 - 1000));
                        setBrightness(valor_brillo);
                    }
                } else if (event.values[0] < 1000 && event.values[0] > 15){
                    permission();
                    if (Settings.System.canWrite(context)) {
                        porcentaje = (event.values[0] - 15) / (1000 - 15);
                        valor_brillo = Math.round(0 + porcentaje * (1000 - 15));
                        setBrightness(valor_brillo);
                    }
                } else{
                    permission();
                    if (Settings.System.canWrite(context)) {
                        setBrightness(0);
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void permission()
    {
        if(!GlobalVariables.unaVez){
            boolean value;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                value = Settings.System.canWrite(context);

                if (value)
                {
                    GlobalVariables.success = true;
                }else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:"+context.getPackageName()));
                    activity.startActivityForResult(intent, 100);
                }
            }
            GlobalVariables.unaVez = true;
        }
    }

    private void setBrightness(int brightness)
    {
        if (brightness < 0)
        {
            brightness = 0;
        }else if (brightness > 255){
            brightness = 255;
        }

        ContentResolver contentResolver = context.getContentResolver();
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
    }
}


