package org.xboot.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.StatFs;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SdcardActivity extends Activity {
	private Button mBtnStart;
	private TextView mTextMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sdcard);

		mBtnStart = (Button) findViewById(R.id.sdcard);
		mTextMsg = (TextView) findViewById(R.id.sdcardText);
		mTextMsg.setMovementMethod(ScrollingMovementMethod.getInstance());

		mBtnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mTextMsg.setText("");

				for(int i = 1; i <= 2; i++)
				{
					String path = "/storage/sdcard" + i;
					final StatFs statfs = new StatFs(path);
					@SuppressWarnings("deprecation")
					long blocSize = statfs.getBlockSize();
					@SuppressWarnings("deprecation")
					long totalBlocks = statfs.getBlockCount();
					if (totalBlocks <= 0)
						break;
					@SuppressWarnings("deprecation")
					long availaBlock = statfs.getAvailableBlocks();

					mTextMsg.append("sdcard path : " + path + '\n');
					mTextMsg.append("sdcard capacity : "
						+ String.valueOf((availaBlock) * blocSize / 1024 / 1024)
						+ "MB / "
						+ String.valueOf(totalBlocks * blocSize / 1024 / 1024)
						+ "MB" + '\n');
				}
			}
		});
	}
}
