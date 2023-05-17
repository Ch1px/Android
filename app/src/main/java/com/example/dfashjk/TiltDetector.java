package com.example.dfashjk;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class TiltDetector implements SensorEventListener {

    private BallView ballView;

    public TiltDetector(BallView ballView) {
        this.ballView = ballView;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float ax = event.values[0];
        float ay = event.values[1];

        float fx = ax / 4;
        float fy = ay / 4;

        ballView.move(fx, fy);
    }
}