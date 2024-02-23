package com.example.miaplicacionmovil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityInfo extends AppCompatActivity {
    Button buttonBecas;
    Button buttonComedor;
    Button buttonTransporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Becas y Erasmus
        buttonBecas = findViewById(R.id.becas);
        buttonBecas.setOnClickListener(view -> {
            // Nos vamos a la actividad de las Becas
            Intent becas = new Intent(getApplicationContext(), ActivityBecasyErasmus.class);
            startActivity(becas);
        });

        // Comedor
        buttonComedor = findViewById(R.id.comedor);
        buttonComedor.setOnClickListener(view -> {
            // Nos vamos a la actividad del Comedor
            Intent comedor = new Intent(getApplicationContext(), ActivityComedor.class);
            startActivity(comedor);
        });

        // Transporte
        buttonTransporte = findViewById(R.id.transporte);
        buttonTransporte.setOnClickListener(view -> {
            // Nos vamos a la actividad del Transporte
            Intent transporte = new Intent(getApplicationContext(), ActivityTransporte.class);
            startActivity(transporte);
        });

        // Brillo
        LightBrightness brillo = new LightBrightness(this, getApplicationContext());
        if (GlobalVariables.cambio){
            brillo.setAuto(true);
        }else{
            brillo.setAuto(false);
        }
    }
}

