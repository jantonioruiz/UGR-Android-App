package com.example.miaplicacionmovil;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ActivityMaps extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, SensorEventListener {

    private GoogleMap map;
    private static final int REQUEST_CODE_LOCATION = 0;
    ImageView compassimg;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        createFragment();
        //configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        compassimg = findViewById(R.id.compassImageView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Brillo
        LightBrightness brillo = new LightBrightness(this, getApplicationContext());
        if (GlobalVariables.cambio){
            brillo.setAuto(true);
        }else{
            brillo.setAuto(false);
        }
    }

    private void createFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for( Sensor s: sensorList){
            Log.d("Sensor", s.toString() );
        }
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        //createPolyLines();
        createMarker();
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter(getLayoutInflater(),this));
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableLocation();
    }

    //metodo empleado para crear marcadores
    private void createMarker(){
        Marker new_marker;
        //añadimos los marcadores necesarios
        LatLng origen = new LatLng(37.184885, -3.603454);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(origen,13f));

        //añadimos marcadores a diferentes puntos de activacion
        LatLng cartuja = new LatLng(37.193501, -3.596922);
        new_marker=map.addMarker(new MarkerOptions().position(cartuja).title("Cartuja"));
        new_marker.setTag("f_cartuja");

        LatLng fuentenueva = new LatLng(37.180758, -3.608952);
        new_marker=map.addMarker(new MarkerOptions().position(fuentenueva).title("Campus Fuentenueva"));
        new_marker.setTag("f_ciencias");

        LatLng pts = new LatLng(37.148302, -3.604420);
        new_marker=map.addMarker(new MarkerOptions().position(pts).title("Campus PTS"));
        new_marker.setTag("f_pts");

        LatLng churreria = new LatLng(37.19764930898708,  -3.6235433973939144);
        new_marker=map.addMarker(new MarkerOptions().position(churreria).title("Churrería"));
        new_marker.setTag("f_churros");

        LatLng rectorado = new LatLng(37.18493171453019, -3.601012701524029);
        new_marker=map.addMarker(new MarkerOptions().position(rectorado).title("Rectorado"));
        new_marker.setTag("f_rectorado");

        LatLng Vcentenario = new LatLng(37.18700616435704, -3.6044563178051665);
        new_marker=map.addMarker(new MarkerOptions().position(Vcentenario).title("V Centenario"));
        new_marker.setTag("f_centenario");

        LatLng CITIC = new LatLng(37.19781506254951, -3.6243671668429402);
        new_marker=map.addMarker(new MarkerOptions().position(CITIC).title("CITIC-UGR"));
        new_marker.setTag("f_citic");

        LatLng comedorCiencias = new LatLng(37.182771336704164, -3.6058412715598345);
        new_marker=map.addMarker(new MarkerOptions().position(comedorCiencias).title("Comedor Ciencias"));
        new_marker.setTag("f_comedor");

    }

    private void createPolyLines() {
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(40.419173113350965, -3.705976009368897))
                .add(new LatLng(40.4150807746539, -3.706072568893432))
                .add(new LatLng(40.41517062907432, -3.7012016773223873))
                .add(new LatLng(40.41713105928677, -3.7037122249603267))
                .add(new LatLng(40.41926296230622, -3.701287508010864))
                .add(new LatLng(40.419173113350965, -3.7048280239105225))
                .width(15f)
                .color(ContextCompat.getColor(this, R.color.kotlin));
        map.addPolyline(polylineOptions).setClickable(true);

        map.setOnPolylineClickListener(polyline -> changeColor(polyline));
    }

    public void changeColor(Polyline polyline) {
        switch ((int) (Math.random() * 4)) {
            case 0:
                polyline.setColor(ContextCompat.getColor(this, R.color.red));
                break;
            case 1:
                polyline.setColor(ContextCompat.getColor(this, R.color.yellow));
                break;
            case 2:
                polyline.setColor(ContextCompat.getColor(this, R.color.green));
                break;
            case 3:
                polyline.setColor(ContextCompat.getColor(this, R.color.blue));
                break;
        }
    }

    private void enableLocation() {
        if (!mapInitialized()) return;

        if (isLocationPermissionGranted()) {
            map.setMyLocationEnabled(true);
        } else {
            requestLocationPermission();
        }
    }

    private boolean mapInitialized() {
        return map != null;
    }

    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mapInitialized() && !isLocationPermissionGranted()) {
            map.setMyLocationEnabled(false);
            Toast.makeText(this, "Vuelva a activar la localización", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Boton pulsado", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Estas en " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
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