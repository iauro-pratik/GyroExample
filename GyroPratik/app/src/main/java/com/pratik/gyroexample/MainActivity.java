package com.pratik.gyroexample;

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.WindowManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private BubbleView bubbleView;

    private SensorManager manager;

    private Sensor gyroscopeSensor;
    private SensorEventListener gyroscopeEventListener = null;
    private List list;

    private final double radToDeg = 57.29577951;
    private double pitch = 0, roll = 0;
    private int stepMoveFactor = 1;
    private int moveX = 0, moveY = 0, x = 0, y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        bubbleView = new BubbleView(this);
        setContentView(bubbleView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        //list = manager.getSensorList(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        gyroscopeSensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
//        gyroscopeSensor = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        implementSensorEventListener();
//        list = manager.getSensorList(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
        list = manager.getSensorList(Sensor.TYPE_GYROSCOPE);
        if (list.size() > 0) {
            Log.d(TAG, "Yes Gyroscope");
            implementSensorEventListener();
        } else {
            Log.d(TAG, "No Gyroscope");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
//        manager.registerListener(gyroscopeEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        if (list.size() > 0) {
            manager.registerListener(gyroscopeEventListener, (Sensor) list.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
//        manager.unregisterListener(gyroscopeEventListener);
        if (list.size() > 0) {
            manager.unregisterListener(gyroscopeEventListener);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
//        manager.unregisterListener(gyroscopeEventListener);
        if (list.size() > 0) {
            manager.unregisterListener(gyroscopeEventListener);
        }
        super.onStop();
    }


    private void implementSensorEventListener() {

        gyroscopeEventListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float[] values = sensorEvent.values;
                Log.d(TAG, "x: " + values[0] + "\ny: " + values[1] + "\nz: " + values[2]);

                pitch = calcPitch(sensorEvent.values[0]); // x
                Log.d(TAG, "pitch " + pitch);
                roll = calcRoll(sensorEvent.values[1]); // y
                Log.d(TAG, "roll " + roll);

                moveX = (int) (bubbleView.getxPos() + (pitch * stepMoveFactor));
                Log.d(TAG, "moveX " + moveX);
                moveY = (int) ((bubbleView.getyPos() + bubbleView.getDiameter()) - (roll * stepMoveFactor));
                Log.d(TAG, "moveY " + moveY);

                if (moveX > 0 && moveX < bubbleView.getSbWidth() && Math.abs(pitch) > 3) {
                    x = (int) (bubbleView.getxPos() + (pitch * stepMoveFactor));
                }

                if (moveY > 0 && moveY < bubbleView.getSbHeight() && Math.abs(roll) > 3) {
                    y = (int) (bubbleView.getyPos() - (roll * stepMoveFactor));
                }

                if (x > bubbleView.getSbWidth()) {
                    Log.d(TAG, "X : x > bubbleView.getMaxWidth()");
                    x = bubbleView.getSbWidth();
                } else if (x < 0) {
                    Log.d(TAG, "X : x < 0");
                    x = 0;
                }

                if (y > bubbleView.getSbHeight()) {
                    Log.d(TAG, "Y : y > bubbleView.getMaxHeight()");
                    y = bubbleView.getSbHeight();
                } else if (y < 0) {
                    Log.d(TAG, "Y : y < 0");
                    y = 0;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bubbleView.move(x, y);
                        bubbleView.invalidate();
                    }
                });
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

    private int getXPosition(float xRad) {
        int xValue = bubbleView.getDxPos();
        xValue = (int) (bubbleView.getxPos() + xRad * 20);
        Log.d(TAG, "xRad " + xRad);
        Log.d(TAG, "Default X Position " + xValue);
        Log.d(TAG, "New X Position " + xValue);
        return xValue;
    }

    private int getYPosition(float yRad) {
        int yValue = bubbleView.getDyPos();
        yValue = (int) (bubbleView.getyPos() + yRad * 20);
        Log.d(TAG, "yRad " + yRad);
        Log.d(TAG, "Default Y Position " + yValue);
        Log.d(TAG, "New Y Position " + yValue);
        return yValue;
    }

    private int getXPosition(double pitch) {
        int xValue = bubbleView.getDxPos();
        xValue = (int) (bubbleView.getxPos() + (pitch * stepMoveFactor));
        Log.d(TAG, "Default X Position " + bubbleView.getDxPos());
        Log.d(TAG, "New X Position " + xValue);
        return xValue;
    }

    private int getYPosition(double roll) {
        int yValue = bubbleView.getDyPos();
        yValue = (int) (bubbleView.getyPos() + (roll * stepMoveFactor));
        Log.d(TAG, "Default Y Position " + bubbleView.getDyPos());
        Log.d(TAG, "New Y Position " + yValue);
        return yValue;
    }

    private double calcPitch(float xRad) {
        return (xRad * radToDeg);
        //return  xRad;
    }

    private double calcRoll(float yRad) {
        return (yRad * radToDeg);
        //return yRad;
    }
}
