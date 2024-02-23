package com.example.miaplicacionmovil;

import androidx.viewpager.widget.PagerAdapter;
import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SlideView extends PagerAdapter {
    Context ctx;

    public SlideView (Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view==object;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_screen, container, false);

        ImageView logo=view.findViewById(R.id.logo);
        ImageView boton1 = view.findViewById(R.id.boton1);
        ImageView boton2 = view.findViewById(R.id.boton2);
        ImageView boton3 = view.findViewById(R.id.boton3);
        ImageView boton4 = view.findViewById(R.id.boton4);
        ImageView boton5 = view.findViewById(R.id.boton5);
        ImageView boton6 = view.findViewById(R.id.boton6);
        ImageView boton7 = view.findViewById(R.id.boton7);

        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);

        ImageView next = view.findViewById(R.id.ic_next);
        ImageView back = view.findViewById(R.id.ic_back);
        Button botonComenzar = view.findViewById(R.id.botonComenzar);

        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySlide.viewPager.setCurrentItem(position+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySlide.viewPager.setCurrentItem(position-1);
            }
        });

        switch (position){
            case 0:
                logo.setImageResource(R.drawable.logougr);
                boton1.setImageResource(R.drawable.black_button);
                boton2.setImageResource(R.drawable.white_button);
                boton3.setImageResource(R.drawable.white_button);
                boton4.setImageResource(R.drawable.white_button);
                boton5.setImageResource(R.drawable.white_button);
                boton6.setImageResource(R.drawable.white_button);
                boton7.setImageResource(R.drawable.white_button);
                title.setText("ETSIIT Punto de Información");
                next.setImageResource(R.drawable.flechader);
                back.setImageResource(R.drawable.flechaizqsinrelleno);
                break;
            case 1:
                logo.setImageResource(R.drawable.logolectorqr);
                boton1.setImageResource(R.drawable.white_button);
                boton2.setImageResource(R.drawable.black_button);
                boton3.setImageResource(R.drawable.white_button);
                boton4.setImageResource(R.drawable.white_button);
                boton5.setImageResource(R.drawable.white_button);
                boton6.setImageResource(R.drawable.white_button);
                boton7.setImageResource(R.drawable.white_button);
                title.setText("Lector de QR");
                subtitle.setText("La aplicación dispone de un lector de códigos QR usando la cámara del dispositivo");
                next.setImageResource(R.drawable.flechader);
                back.setImageResource(R.drawable.flechaizq);
                break;
            case 2:
                logo.setImageResource(R.drawable.logomapas);
                boton1.setImageResource(R.drawable.white_button);
                boton2.setImageResource(R.drawable.white_button);
                boton3.setImageResource(R.drawable.black_button);
                boton4.setImageResource(R.drawable.white_button);
                boton5.setImageResource(R.drawable.white_button);
                boton6.setImageResource(R.drawable.white_button);
                boton7.setImageResource(R.drawable.white_button);
                title.setText("Mapa con marcadores");
                subtitle.setText("La aplicación dispone de acceso a Google Maps con marcadores de sitios de interés");
                next.setImageResource(R.drawable.flechader);
                back.setImageResource(R.drawable.flechaizq);
                break;
            case 3:
                logo.setImageResource(R.drawable.logobrujula);
                boton1.setImageResource(R.drawable.white_button);
                boton2.setImageResource(R.drawable.white_button);
                boton3.setImageResource(R.drawable.white_button);
                boton4.setImageResource(R.drawable.black_button);
                boton5.setImageResource(R.drawable.white_button);
                boton6.setImageResource(R.drawable.white_button);
                boton7.setImageResource(R.drawable.white_button);
                title.setText("Brújula");
                subtitle.setText("La aplicación dispone de una brújula para poder orientarnos");
                next.setImageResource(R.drawable.flechader);
                back.setImageResource(R.drawable.flechaizq);
                break;
            case 4:
                logo.setImageResource(R.drawable.logogiroscopio);
                boton1.setImageResource(R.drawable.white_button);
                boton2.setImageResource(R.drawable.white_button);
                boton3.setImageResource(R.drawable.white_button);
                boton4.setImageResource(R.drawable.white_button);
                boton5.setImageResource(R.drawable.black_button);
                boton6.setImageResource(R.drawable.white_button);
                boton7.setImageResource(R.drawable.white_button);
                title.setText("Giroscopio");
                subtitle.setText("La aplicación dispone de un cierre forzado rotando el dispositivo 180º");
                next.setImageResource(R.drawable.flechader);
                back.setImageResource(R.drawable.flechaizq);
                break;

            case 5:
                logo.setImageResource(R.drawable.logobrillo);
                boton1.setImageResource(R.drawable.white_button);
                boton2.setImageResource(R.drawable.white_button);
                boton3.setImageResource(R.drawable.white_button);
                boton4.setImageResource(R.drawable.white_button);
                boton5.setImageResource(R.drawable.white_button);
                boton6.setImageResource(R.drawable.black_button);
                boton7.setImageResource(R.drawable.white_button);
                title.setText("Brillo");
                subtitle.setText("La aplicación dispone de una funcionalidad para controlar el brillo de forma automática");
                next.setImageResource(R.drawable.flechader);
                back.setImageResource(R.drawable.flechaizq);
                break;

            case 6:
                logo.setImageResource(R.drawable.logobot);
                boton1.setImageResource(R.drawable.white_button);
                boton2.setImageResource(R.drawable.white_button);
                boton3.setImageResource(R.drawable.white_button);
                boton4.setImageResource(R.drawable.white_button);
                boton5.setImageResource(R.drawable.white_button);
                boton6.setImageResource(R.drawable.white_button);
                boton7.setImageResource(R.drawable.black_button);
                title.setText("Bot de ayuda");
                subtitle.setText("La aplicación dispone de un bot de ayuda con reconocimiento de voz al que puedes preguntar dudas");
                next.setImageResource(R.drawable.flechadersinrelleno);
                back.setImageResource(R.drawable.flechaizq);
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}