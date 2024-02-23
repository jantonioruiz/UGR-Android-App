package com.example.miaplicacionmovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityComedor extends AppCompatActivity {

    CardView localizacion;
    CardView acceso;

    // Needed for browser
    WebView web;
    WebSettings webSettings;

    // To check if we are looking for a website
    boolean webUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor);

        // Configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        localizacion = findViewById(R.id.localizacioncomedor);
        acceso = findViewById(R.id.accesocomedores);

        // To visualize websites inside the app using JS
        web = new WebView(getApplicationContext());
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webUsed = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_localizacioncomedor));
            }
        });

        acceso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webUsed = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_accesocomedores));
            }
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
