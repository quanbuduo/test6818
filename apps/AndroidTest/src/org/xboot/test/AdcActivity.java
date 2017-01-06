package org.xboot.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class AdcActivity extends Activity {
	private TextView mTextViewAdc0;
	private TextView mTextViewAdc1;
	private TextView mTextViewAdc2;
	private TextView mTextViewAdc3;
	private Handler handler;
	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adc);
		mTextViewAdc0 = (TextView)findViewById(R.id.adc0);
		mTextViewAdc1 = (TextView)findViewById(R.id.adc1);
		mTextViewAdc2 = (TextView)findViewById(R.id.adc2);
		mTextViewAdc3 = (TextView)findViewById(R.id.adc3);
		
		handler = new Handler();
		runnable = new Runnable() {
			@Override
			public void run() {
				handler.postDelayed(this, 2000);
				updateStatus();
			}
		};
		handler.postDelayed(runnable, 2000);
	}
		
	private void updateStatus()
	{
		String adc0 = DeviceIO.read("/sys/bus/platform/drivers/nxp-adc/nxp-adc/iio:device0/in_voltage0_raw").replaceAll("\r|\n", "");
		mTextViewAdc0.setText("ADC0 : " + adc0);
		String adc1 = DeviceIO.read("/sys/bus/platform/drivers/nxp-adc/nxp-adc/iio:device0/in_voltage1_raw").replaceAll("\r|\n", "");
		mTextViewAdc1.setText("ADC1 : " + adc1);
		String adc2 = DeviceIO.read("/sys/bus/platform/drivers/nxp-adc/nxp-adc/iio:device0/in_voltage2_raw").replaceAll("\r|\n", "");
		mTextViewAdc2.setText("ADC2 : " + adc2);
		String adc3 = DeviceIO.read("/sys/bus/platform/drivers/nxp-adc/nxp-adc/iio:device0/in_voltage3_raw").replaceAll("\r|\n", "");
		mTextViewAdc3.setText("ADC3 : " + adc3);
	}
	
	public void initial() {
		handler.postDelayed(runnable, 1000);
	}

	public void release() {
		handler.removeCallbacks(runnable);
	}
}
