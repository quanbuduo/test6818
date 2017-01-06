package org.xboot.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;

public class KeyBoardActivity extends Activity {

	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyboard);

		mTextView = (TextView) findViewById(R.id.keyValue);
		mTextView.setText("Please press any key!");
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle data = msg.getData();
				String text = new String();
				int code = data.getInt("code");
				switch (code) {
				case KeyEvent.KEYCODE_MENU:
					text += "MENU";
					break;
				case KeyEvent.KEYCODE_BACK:
					text += "BACK";
					break;
				case KeyEvent.KEYCODE_VOLUME_UP:
					text += "VOLUME_UP";
					break;
				case KeyEvent.KEYCODE_VOLUME_DOWN:
					text += "VOLUME_DOWN";
					break;
				default:
					break;
				}
				int action = data.getInt("action");
				switch (action) {
				case KeyEvent.ACTION_DOWN:
					text += " : [KEY DOWN]";
					break;
				case KeyEvent.ACTION_UP:
					text += " : [KEY UP]";
					break;
				default:
					break;
				}
				mTextView.setText(text);
				break;
			}
		};
	};
}
