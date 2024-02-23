package com.example.miaplicacionmovil;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Abrimos el menú
                Intent intent = new Intent(SplashActivity.this, ActivitySlide.class);
                startActivity(intent);
                finish();
            }
        }, 2000); // Duración del splash en milisegundos (en este caso, 2000 ms o 2 segundos)
    }
}
