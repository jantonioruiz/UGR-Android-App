package com.example.miaplicacionmovil;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuActivity extends AppCompatActivity {
    FloatingActionButton buttonQR;
    FloatingActionButton buttonBrightness;
    Button buttonMap;

    Button buttonFacultades;

    Button buttonInformacion;

    FloatingActionButton buttonHelp;

    LightBrightness brillo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // Mapa puntos interés
        buttonMap = findViewById(R.id.puntosinteres);
        buttonMap.setOnClickListener(view -> {
            // Nos vamos a la actividad del Mapa
            Intent maps = new Intent(getApplicationContext(), ActivityMaps.class);
            startActivity(maps);
        });

        // Información
        buttonInformacion = findViewById(R.id.informacion);
        buttonInformacion.setOnClickListener(view -> {
            // Nos vamos a la actividad de la Información
            Intent info = new Intent(getApplicationContext(), ActivityInfo.class);
            startActivity(info);
        });

        // Lector QR
        buttonQR = findViewById(R.id.fb_qr);
        buttonQR.setOnClickListener(view -> {
            // Nos vamos a la actividad del QR
            Intent qr = new Intent(getApplicationContext(), ActivityQR.class);
            startActivity(qr);
        });

        buttonFacultades = findViewById(R.id.facultades);
        buttonFacultades.setOnClickListener(view -> {
            // Nos vamos a la lista de puntos de activacion de la TUI
            Intent list = new Intent(getApplicationContext(), ListFacultyActivity.class);
            startActivity(list);
        });

        buttonHelp = findViewById(R.id.fb_help);
        buttonHelp.setOnClickListener(view -> {
            // Nos vamos a la lista de puntos de activacion de la TUI
            Intent list = new Intent(getApplicationContext(), FragmentDialogFlow.class);
            startActivity(list);
        });

        // Brillo
        buttonBrightness = findViewById(R.id.fb_brightness);
        brillo = new LightBrightness(this, getApplicationContext());
        buttonBrightness.setOnClickListener(view -> {
            // Cambiamos el color del botón cuando lo queramos usar
            if (!GlobalVariables.cambio){
                buttonBrightness.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.naranja_claro));
                buttonBrightness.setImageResource(R.drawable.logo_autobrightness);
                brillo.setAuto(true);
                // Invertimos valor de variable booleana
                GlobalVariables.cambio = true;
                GlobalVariables.auto = true;
            }else{
                buttonBrightness.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.naranja));
                buttonBrightness.setImageResource(R.drawable.logo_brightness);
                brillo.setAuto(false);
                // Invertimos valor de variable booleana
                GlobalVariables.cambio = false;
                GlobalVariables.auto = false;
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (Settings.System.canWrite(getApplicationContext())) {
                Toast.makeText(this, "Permisos de escritura otorgados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permisos de escritura NO otorgados", Toast.LENGTH_SHORT).show();
            }
        }
    }
}