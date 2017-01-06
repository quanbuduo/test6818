package org.xboot.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import java.io.IOException;

import org.xboot.test.R;

public class LedActivity extends Activity {
	private CheckBox mCheckBoxLed1;
	private CheckBox mCheckBoxLed2;
	private CheckBox mCheckBoxLed3;
	private CheckBox mCheckBoxLed4;
	private CompoundButton.OnCheckedChangeListener mListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_led);

		mCheckBoxLed1 = (CheckBox) findViewById(R.id.led1);
		mCheckBoxLed2 = (CheckBox) findViewById(R.id.led2);
		mCheckBoxLed3 = (CheckBox) findViewById(R.id.led3);
		mCheckBoxLed4 = (CheckBox) findViewById(R.id.led4);
		mCheckBoxLed1.setChecked((DeviceIO.read("/sys/devices/platform/leds-gpio/leds/led1/brightness").charAt(0)) != '0');
		mCheckBoxLed2.setChecked((DeviceIO.read("/sys/devices/platform/leds-gpio/leds/led2/brightness").charAt(0)) != '0');
		mCheckBoxLed3.setChecked((DeviceIO.read("/sys/devices/platform/leds-gpio/leds/led3/brightness").charAt(0)) != '0');
		mCheckBoxLed4.setChecked((DeviceIO.read("/sys/devices/platform/leds-gpio/leds/led4/brightness").charAt(0)) != '0');

		mListener = new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				String fileName = "/sys/devices/platform/leds-gpio/leds/led"
						+ (arg0.getId() - mCheckBoxLed1.getId() + 1)
						+ "/brightness";
				String data = "" + (arg1 ? 1 : 0);
				try {
					DeviceIO.write(fileName, data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		mCheckBoxLed1.setOnCheckedChangeListener(mListener);
		mCheckBoxLed2.setOnCheckedChangeListener(mListener);
		mCheckBoxLed3.setOnCheckedChangeListener(mListener);
		mCheckBoxLed4.setOnCheckedChangeListener(mListener);
	}
}
