package com.example.miaplicacionmovil;

import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.URLUtil;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class ActivityQR extends AppCompatActivity {
    private final int PERMISSIONS_REQUEST_CAMERA = 0;

    // Lo usaremos para tratar QR duplicados
    private String qr = "";
    private String last_qr = "";

    BarcodeDetector barcodeDetector;
    SparseArray<Barcode> barcodes;
    CameraSource cameraSource;
    SurfaceView cameraView;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        layout = findViewById(R.id.qr_layout);

        //configuramos el giroscopio
        GyroscopicManager giroscopio = new GyroscopicManager(this);

        // Orientación vertical
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Inicializamos
        cameraView = (SurfaceView) findViewById(R.id.qr_view);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setAutoFocusEnabled(true).build();

        // Comprobamos que tenemos permisos de la cámara
        CheckPermissions();

        // Brillo
        LightBrightness brillo = new LightBrightness(this, getApplicationContext());
        if (GlobalVariables.cambio){
            brillo.setAuto(true);
        }else{
            brillo.setAuto(false);
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    // Obtenemos si el usuario nos ha otorgado los permisos de la cámara
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(layout, R.string.camera_permission_granted, Snackbar.LENGTH_SHORT).show();
                onRestart();
            } else {
                Snackbar.make(layout, R.string.camera_permission_denied, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    // Comprobamos si el usuario nos ha otorgado los permisos de la cámara
    private void CheckPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(layout, R.string.camera_permission_available, Snackbar.LENGTH_SHORT).show();
            starQR();

        } else {
            requestCameraPermission();
        }

    }

    // Pedimos los permisos necesarios para la cámara
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(layout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Solicitamos que nos otorgue los permisos de la cámara
                    ActivityCompat.requestPermissions(ActivityQR.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSIONS_REQUEST_CAMERA);
                }
            }).show();

        } else {
            Snackbar.make(layout, R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        }
    }

    // Función para realizar la visualización de la cámara una vez tengamos los permisos
    private void previewCameraView(){
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // Creamos la superficie de visualización si disponemos de los permisos, en caso contrario los pedimos
                if(ActivityCompat.checkSelfPermission(ActivityQR.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }else{
                    requestCameraPermission();
                }
            }

            // Needed
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }

    // Función que detectará códigos QR cuando los mostremos en la cámara
    private void detectorQR(){
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                barcodes = detections.getDetectedItems();
                // Si hemos encontrado un QR
                if (barcodes.size() > 0) {
                    qr = barcodes.valueAt(0).displayValue;

                    // Comprobamos que no sea igual al anterior (para evitar duplicados)
                    if (!qr.equals(last_qr)) {
                        last_qr = qr;

                        // Si el QR nos lleva a un enlace web, abrimos el navegador
                        if (URLUtil.isValidUrl(qr)) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(qr));
                            startActivity(browserIntent);
                        }

                        // Dejamos el método en espera a la espera de nuevos QR
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    synchronized (this) {
                                        wait(5000);
                                        last_qr = "";
                                    }
                                } catch (InterruptedException e) {
                                    Log.e("Error", "Waiting didnt work!!");
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }
            }
        });
    }

    private void starQR(){
        // Iniciamos la preview de la cámara
        previewCameraView();

        // Activamos el lector QR
        detectorQR();
    }
}
