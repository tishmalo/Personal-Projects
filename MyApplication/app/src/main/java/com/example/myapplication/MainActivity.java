package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int REQUEST_CODE = 1;
    SensorManager sensorManager;
    Sensor sensor;

    private Boolean running=false;
    private TextView steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        steps=findViewById(R.id.steps);




        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)==PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACTIVITY_RECOGNITION
                },Sensor.TYPE_STEP_COUNTER);
            }
        }else {

            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

            PackageManager pm = getPackageManager();
            if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
                Toast.makeText(MainActivity.this, "sensor found", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Imekataa", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        running = true;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(MainActivity.this, "No sensor found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
    if() {
        steps.setText(String.valueOf(sensorEvent.values[0]));
        Toast.makeText(getApplicationContext(), String.valueOf(sensorEvent.values[0]), Toast.LENGTH_SHORT).show();
}

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}