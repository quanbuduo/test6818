package org.xboot.test;

import java.io.IOException;
import org.xboot.test.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class BeepActivity extends Activity {
	private Button mBtnBeep;
	final private String mBeep = "/sys/devices/platform/x6818-beep/state";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beep);
		mBtnBeep = (Button) findViewById(R.id.btnBeep);
	
		mBtnBeep.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					startBeep();
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					stopBeep();
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	void startBeep() {
		try {
			DeviceIO.write(mBeep, 1 + "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void stopBeep() {
		try {
			DeviceIO.write(mBeep, 0 + "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
