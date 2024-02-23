package com.example.miaplicacionmovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityTransporte extends AppCompatActivity {
    Button buttonLineaU1;
    Button buttonLineaN3;
    Button buttonLinea4;
    Button buttonLinea9;
    WebView web;
    WebSettings webSettings;
    boolean webUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transporte);

        // Configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // To visualize websites inside the app using JS
        web = new WebView(getApplicationContext());
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Linea U1
        buttonLineaU1 = findViewById(R.id.lineau1);
        buttonLineaU1.setOnClickListener(view -> {
            webUsed = true;
            setContentView(web);
            web.loadUrl("https://moovitapp.com/index/es/transporte_p%C3%BAblico-line-u1-Granada-2422-850093-489101-0");
        });

        // Linea N3
        buttonLineaN3 = findViewById(R.id.linean3);
        buttonLineaN3.setOnClickListener(view -> {
            webUsed = true;
            setContentView(web);
            web.loadUrl("https://moovitapp.com/index/es/transporte_p%C3%BAblico-line-n3-Granada-2422-850093-754588-0");
        });

        // Linea 4
        buttonLinea4 = findViewById(R.id.linea4);
        buttonLinea4.setOnClickListener(view -> {
            webUsed = true;
            setContentView(web);
            web.loadUrl("https://moovitapp.com/index/es/transporte_p%C3%BAblico-line-4-Granada-2422-850093-754577-0");
        });

        // Linea 9
        buttonLinea9 = findViewById(R.id.linea9);
        buttonLinea9.setOnClickListener(view -> {
            webUsed = true;
            setContentView(web);
            web.loadUrl("https://moovitapp.com/index/es/transporte_p%C3%BAblico-line-9-Granada-2422-850093-754580-0");
        });

        // Brillo
        LightBrightness brillo = new LightBrightness(this, getApplicationContext());
        if (GlobalVariables.cambio){
            brillo.setAuto(true);
        }else{
            brillo.setAuto(false);
        }
    }

    @Override
    public void onRestart(){
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}