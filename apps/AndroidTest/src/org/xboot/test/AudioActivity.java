package org.xboot.test;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AudioActivity extends Activity {
	private Button mBtnPlaySound;
	private SoundPool soundPool;
	private int mSound;
	private boolean mLoadComplete = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio);
		mBtnPlaySound = (Button) findViewById(R.id.playSound);

		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				mLoadComplete = true;
			}
		});
		mSound = soundPool.load(this, R.raw.sample, 1);

		mBtnPlaySound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mLoadComplete) {
					soundPool.play(mSound, 1, 1, 1, 0, 1);
				}
			}
		});
	}
}
