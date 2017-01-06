package org.xboot.test;

import org.xboot.test.CircularSeekBar.OnCircularSeekBarChangeListener;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class BacklightActivity extends Activity {
	private SeekBar mSeekbar;
	private TextView mTextView;
	private CircularSeekBar mCircularSeekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_backlight);

		if (isAutoBrightness(getParent().getContentResolver()))
			stopAutoBrightness(getParent());

		mSeekbar = (SeekBar) findViewById(R.id.seekbar_backlight);
		mTextView = (TextView) findViewById(R.id.backlight);
		mCircularSeekBar = (CircularSeekBar) findViewById(R.id.circularSeekBar1);

		int normal = Settings.System.getInt(getParent().getContentResolver(),
				Settings.System.SCREEN_BRIGHTNESS, 255);
		mSeekbar.setProgress(normal);
		mCircularSeekBar.setProgress(normal);
		mTextView.setText(normal + "");

		mCircularSeekBar
				.setOnSeekBarChangeListener(new CircleSeekBarListener());
		ViewTreeObserver vto = mSeekbar.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			public boolean onPreDraw() {
				mSeekbar.getViewTreeObserver().removeOnPreDrawListener(this);
				return true;
			}
		});

		mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mTextView.setText(progress + "");
				mCircularSeekBar.setProgress(progress);
				setBrightness(getParent(), progress);
			}
		});

	}

	private static void setBrightness(Activity activity, int brightness) {
		WindowManager.LayoutParams wl = activity.getWindow().getAttributes();
		float tmpFloat = (float) brightness / 255;
		if (tmpFloat > 0 && tmpFloat <= 1) {
			wl.screenBrightness = tmpFloat;
		}
		activity.getWindow().setAttributes(wl);
	}

	public static boolean isAutoBrightness(ContentResolver aContentResolver) {
		boolean automicBrightness = false;
		try {
			automicBrightness = Settings.System.getInt(aContentResolver,
					Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
		} catch (SettingNotFoundException e) {
			e.printStackTrace();
		}
		return automicBrightness;
	}

	public static void stopAutoBrightness(Activity activity) {
		try {
			Settings.System.putInt(activity.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startAutoBrightness(Activity activity) {
		try {
			Settings.System.putInt(activity.getContentResolver(),

			Settings.System.SCREEN_BRIGHTNESS_MODE,
					Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class CircleSeekBarListener implements
			OnCircularSeekBarChangeListener {
		@Override
		public void onProgressChanged(CircularSeekBar circularSeekBar,
				int progress, boolean fromUser) {
			mTextView.setText(progress + "");
			mSeekbar.setProgress(progress);
			setBrightness(getParent(), progress);
		}
	}
}
