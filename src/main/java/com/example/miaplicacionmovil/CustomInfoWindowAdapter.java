package com.example.miaplicacionmovil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View window;
    private Context context;
    CustomInfoWindowAdapter(LayoutInflater inflater, Context context){
        window = inflater.inflate(R.layout.custom_info_window, null);
        this.context=context;
    }
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        //actualizamos el contenido de la ventana de informacion
        render(marker,window);
        return window;
    }

    private void render(Marker marker, View view) {
        //enlazamos los elementos de la vista usando la info del marcador
        TextView titulo = view.findViewById(R.id.custom_text);
        ImageView imagen = view.findViewById(R.id.custom_img);

        titulo.setText(marker.getTitle());
        //dependiendo de la etiqueta ("nombre del archivo")...
        if(marker.getTag()!=null){
            int resourceId= context.getResources().getIdentifier(marker.getTag().toString(),"drawable", context.getPackageName());
            if(resourceId!=0){
                imagen.setImageResource(resourceId);
            }
        }
    }

    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
