package org.xboot.test;

import org.xboot.test.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class GSensorActivity extends Activity {
	private TextView mTextViewX;
	private TextView mTextViewY;
	private TextView mTextViewZ;
	private SensorManager mSensorManager;

	private final SensorEventListener mSensorListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent se) {
			mTextViewX.setText("Gsensor X : " + se.values[0]);
			mTextViewY.setText("Gsensor Y : " + se.values[1]);
			mTextViewZ.setText("Gsensor Z : " + se.values[2]);
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gsensor);
		mTextViewX = (TextView) findViewById(R.id.gsensorX);
		mTextViewY = (TextView) findViewById(R.id.gsensorY);
		mTextViewZ = (TextView) findViewById(R.id.gsensorZ);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onPause();
	}
	
	public void initial() {
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_UI);
	}

	public void release() {
		mSensorManager.unregisterListener(mSensorListener);
	}
}
