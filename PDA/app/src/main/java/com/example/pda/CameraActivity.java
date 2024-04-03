package com.example.pda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private final int REQUEST_CAMERA_PERMISSION = 201;
    private ToneGenerator toneGen1;

    private SurfaceView surfaceView;
    private Button backbutton;
    private String targetVariable;
    public static String barcodeData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_scherm);

        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        Intent intent = getIntent();
        targetVariable = intent.getStringExtra("TARGET_VARIABLE");

        InitWidgets();
        ActivateCam();
    }

    private void InitWidgets(){
        surfaceView = findViewById(R.id.surfaceView);
        backbutton = findViewById(R.id.backbutton);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go back
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    public void ActivateCam(){
        barcodeDetector = new BarcodeDetector.Builder(this.getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this.getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(CameraActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(CameraActivity.this, new
                                String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();


                if (barcodes.size() != 0) {
                    if (barcodes.valueAt(0).email != null) {
                        barcodeData = barcodes.valueAt(0).email.address;
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                    } else {

                        barcodeData = barcodes.valueAt(0).displayValue;
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                        Log.d("barcode", barcodeData);


                        // Prepare for Intent
                        if (barcodeData != null && barcodeData != ""){
                            Intent intent = new Intent();
                            intent.putExtra("BARCODE_DATA", barcodeData);
                            intent.putExtra("TARGET_VARIABLE", targetVariable);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }
                    }

                }

            }
        });

    }

}
