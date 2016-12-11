package com.samuel.AndroidAccelerometerExample;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

//private float lastX, lastY, lastZ;

	private SensorManager sensorManager;
	private Sensor accelerometer;

        private SensorManager sensorManager2;
        private Sensor gyro;
        
        private SensorManager sensorManager3;
        private Sensor compass;
        
	private float deltaXMax = 0;
	private float deltaYMax = 0;
	private float deltaZMax = 0;

	private float deltaX = 0;
	private float deltaY = 0;
	private float deltaZ = 0;

        private float gyroX = 0;
	private float gyroY = 0;
	private float gyroZ = 0;

        private float degree=0;
        
        
	private float vibrateThreshold = 0;

	private TextView currentX, currentY, currentZ, maxX, maxY, maxZ,currentgyroX,currentgyroY,currentgyroZ,tvHeading;
        

	public Vibrator v;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initializeViews();

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
			// success! we have an accelerometer

			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
			vibrateThreshold = accelerometer.getMaximumRange() / 2;
		} else {
			// fai! we dont have an accelerometer!
		}
		
                
               sensorManager2=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
                if(sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED)!=null){
                    
                    gyro=sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
                    sensorManager2.registerListener(this, gyro,SensorManager.SENSOR_DELAY_NORMAL);
                }else{
                    
                }
                
                sensorManager3=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
              
                
               
		//initialize vibration
		v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

	}

	public void initializeViews() {
		//currentX = (TextView) findViewById(R.id.currentX);
		currentY = (TextView) findViewById(R.id.currentY);
		//currentZ = (TextView) findViewById(R.id.currentZ);

		/*maxX = (TextView) findViewById(R.id.maxX);
		maxY = (TextView) findViewById(R.id.maxY);
		maxZ = (TextView) findViewById(R.id.maxZ);
                
                currentgyroX=(TextView)findViewById(R.id.currentgyroX);
                currentgyroY=(TextView)findViewById(R.id.currentgyroY);
                currentgyroZ=(TextView) findViewById(R.id.currentgyroZ);*/
                
                tvHeading=(TextView)findViewById(R.id.tvHeading);
	}

	//onResume() register the accelerometer for listening the events
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager2.registerListener(this, gyro,SensorManager.SENSOR_DELAY_NORMAL);
                sensorManager3.registerListener(this, sensorManager3.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL); 
	}

	//onPause() unregister the accelerometer for stop listening the events
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this);
                sensorManager2.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {

                Sensor sensor = event.sensor;
		// clean current values
		displayCleanValues();
		// display the current x,y,z accelerometer values
		displayCurrentValues();
		// display the max x,y,z accelerometer values
		//displayMaxValues();

		// get the change of the x,y,z values of the accelerometer
		if(sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                    deltaX = event.values[0];
                    deltaY = event.values[1];
                    deltaZ = event.values[2];
                }
                else if(sensor.getType()==Sensor.TYPE_GYROSCOPE_UNCALIBRATED){
                    gyroX = Math.abs(event.values[0]);
                    gyroY = Math.abs(event.values[1]);
                    gyroZ = Math.abs(event.values[2]);
                }
                else if(sensor.getType()==Sensor.TYPE_ORIENTATION){
                    degree = Math.round(event.values[0]);
                    
                }
                

		// if the change is below 2, it is just plain noise
		/*if (deltaX < 2 && deltaX>-2)
			deltaX = 0;
		if (deltaY < 2 && deltaY>-2)
			deltaY = 0;
		if ((deltaX > vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
			//v.vibrate(50);
		}*/
                
                
		
                
		
                
	}

	public void displayCleanValues() {
		//currentX.setText("0.0");
		currentY.setText("0.0");
		//currentZ.setText("0.0");
                
                /**currentgyroX.setText("0.0");
                currentgyroY.setText("0.0");
                currentgyroZ.setText("0.0");**/
	}

	// display the current x,y,z accelerometer values
	public void displayCurrentValues() {
		 //transform value
                float Ydegree=deltaY;
                float Zdegree=deltaZ;
                float angle = (float) (90/10.5);
                float Y = Ydegree*angle;
                
                if(Y>90){
                    Y=90;
                }
                else if(Y<0.5 && Y>-0.5){
                    Y=0;
                }
            
                //currentX.setText(Float.toString(deltaX));
		currentY.setText(Float.toString(Y)+ " degrees");
		//currentZ.setText(Float.toString(deltaZ));
                
                /**currentgyroX.setText(Float.toString(gyroX));
                currentgyroY.setText(Float.toString(gyroY));
                currentgyroZ.setText(Float.toString(gyroZ));**/
                
                tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");
	}

	// display the max x,y,z accelerometer values
	public void displayMaxValues() {
		if (deltaX > deltaXMax) {
			deltaXMax = deltaX;
			maxX.setText(Float.toString(deltaXMax));
		}
		if (deltaY > deltaYMax) {
			deltaYMax = deltaY;
			maxY.setText(Float.toString(deltaYMax));
		}
		if (deltaZ > deltaZMax) {
			deltaZMax = deltaZ;
			maxZ.setText(Float.toString(deltaZMax));
		}
	}
}
