package com.example.miaplicacionmovil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ActivityBecasyErasmus extends AppCompatActivity {

    CardView becaministerio;
    CardView becaugr;
    CardView erasmus;

    // Needed for browser
    WebView web;
    WebSettings webSettings;

    // To check if we are looking for a website
    boolean webUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_becasyreasmus);

        // Configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        becaministerio = findViewById(R.id.becaministerio);
        becaugr = findViewById(R.id.becaugr);
        erasmus = findViewById(R.id.erasmus);

        // To visualize websites inside the app using JS
        web = new WebView(getApplicationContext());
        webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

        becaministerio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webUsed = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_becaministerio));
            }
        });

        becaugr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webUsed = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_becaugr));
            }
        });

        erasmus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webUsed = true;
                setContentView(web);
                web.loadUrl(getString(R.string.url_erasmus));
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
