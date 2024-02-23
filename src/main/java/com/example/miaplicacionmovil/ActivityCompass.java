package com.example.miaplicacionmovil;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class ActivityCompass extends Fragment implements SensorEventListener {

    private ImageView compassimg;
    private SensorManager sensorManager;

    public ActivityCompass() {
        // Required empty public constructor
    }

    public static ActivityCompass newInstance() {
        return new ActivityCompass();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        compassimg = view.findViewById(R.id.compassImageView);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensorList) {
            Log.d("Sensor", s.toString());
        }
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int degree = Math.round(event.values[0]);
        compassimg.setRotation(-degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}