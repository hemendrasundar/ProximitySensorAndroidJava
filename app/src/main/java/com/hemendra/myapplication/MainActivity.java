package com.hemendra.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView sensor_tv,data_tv;
    SensorManager sensorManager;
    Sensor proximitySensor;
    SensorEventListener PSEL= new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            WindowManager.LayoutParams params = MainActivity.this.getWindow().getAttributes();
            if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY)
            {
                if(sensorEvent.values[0]==0)
                {
                   params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                   params.screenBrightness =0;
                   getWindow().setAttributes(params);

                }
                else {
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness =-1f;
                    getWindow().setAttributes(params);
                }
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensor_tv = findViewById(R.id.proxmity_sensor);
        data_tv = findViewById(R.id.data);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if(proximitySensor == null)
        {
            sensor_tv.setText("no proximity sensor");
        }
        else{
            sensorManager.registerListener(PSEL,proximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        if(proximitySensor == null)
        {
            sensor_tv.setText("no proximity sensor");
        }
        else{
            sensorManager.unregisterListener(PSEL);
        }
        super.onStop();
    }
}