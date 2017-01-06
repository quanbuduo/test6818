package org.xboot.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class BatteryActivity extends Activity {
	private TextView mTextViewPresent;
	private TextView mTextViewPowerType;
	private TextView mTextViewStatus;
	private TextView mTextViewCap;
	private TextView mTextViewVol;
	private TextView mTextViewCur;
	private Handler handler;
	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_battery);
		mTextViewPresent = (TextView)findViewById(R.id.batteryPresent);
		mTextViewPowerType = (TextView)findViewById(R.id.batteryPowerType);
		mTextViewStatus = (TextView)findViewById(R.id.batteryStatus);
		mTextViewCap = (TextView)findViewById(R.id.batteryCap);
		mTextViewVol = (TextView)findViewById(R.id.batteryVol);
		mTextViewCur = (TextView)findViewById(R.id.batteryCur);
		
		handler = new Handler();
		runnable = new Runnable() {
			@Override
			public void run() {
				handler.postDelayed(this, 1000);
				updateStatus();
			}
		};
		handler.postDelayed(runnable, 1000);
	}
		
	private void updateStatus()
	{
		String present = DeviceIO.read("/sys/class/power_supply/battery/present").replaceAll("\r|\n", "");
		mTextViewPresent.setText("Battery Exist : " + present);

		String powertype1 = DeviceIO.read("/sys/class/power_supply/ac/online").replaceAll("\r|\n", "");
		int value1 = Integer.parseInt(powertype1,10);
		String powertype2 = DeviceIO.read("/sys/class/power_supply/usb/online").replaceAll("\r|\n", "");
		int value2 = Integer.parseInt(powertype2,10);
		if(value1==1)
		{
			mTextViewPowerType.setText("Battery PowerType : AC");
		}
		else if(value2==1)
		{
			mTextViewPowerType.setText("Battery PowerType : USB");
		}
		else
		{
			mTextViewPowerType.setText("Battery PowerType : No Power");
		}

		String status = DeviceIO.read("/sys/class/power_supply/battery/status").replaceAll("\r|\n", "");
		mTextViewStatus.setText("Battery Status : " + status);


		String capacity = DeviceIO.read("/sys/class/power_supply/battery/capacity").replaceAll("\r|\n", "");
		mTextViewCap.setText("Battery Capacity : " + capacity + "%");

		String voltage = DeviceIO.read("/sys/class/power_supply/battery/voltage_now").replaceAll("\r|\n", "");
		int VolValue = Integer.parseInt(voltage,10);
		VolValue = VolValue/1000;
		voltage = String.valueOf(VolValue);
		mTextViewVol.setText("Battery Vol : " + voltage + "mV");


		String current = DeviceIO.read("/sys/class/power_supply/battery/current_now").replaceAll("\r|\n", "");
		int CurValue = Integer.parseInt(current,10);
		CurValue = CurValue/1000;
		current = String.valueOf(CurValue);
		mTextViewCur.setText("Battery Current : " + current + "mA");
	}
	
	public void initial() {
		handler.postDelayed(runnable, 1000);
	}

	public void release() {
		handler.removeCallbacks(runnable);
	}
}
