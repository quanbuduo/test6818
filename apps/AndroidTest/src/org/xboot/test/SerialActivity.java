package org.xboot.test;

import com.android.serialport.SerialPort;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SerialActivity extends Activity {
	private Button mBtnStart;
	private TextView mTextMsg;
	final String mTestString = "0123456789";

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String data = msg.getData().getString("RECV");
				mTextMsg.append("--> " + data);
				break;
			default:
				break;
			}
		}
	};

	class ReadThread extends Thread {
		SerialPort mSerialPort = null;
		String mDevice = null;
		FileInputStream mInputStream = null;

		ReadThread(SerialPort port, String device) {
			mSerialPort = port;
			mDevice = device;
			mInputStream = (FileInputStream) mSerialPort.getInputStream();
		}

		@Override
		public void run() {
			super.run();

			readSerial();
			readSerial();
			readSerial();
			readSerial();
			readSerial();
			readSerial();

			if (mSerialPort != null) {
				mSerialPort.close();
				mSerialPort = null;
			}
			if (!isInterrupted())
				interrupt();
		}

		void readSerial() {
			int size;
			try {
				if (mInputStream == null)
					return;

				size = mInputStream.available();
				if (size <= 0) {
					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}

				byte[] buffer = new byte[256];
				size = mInputStream.read(buffer);
				if (size > 0) {
					String data = new String(buffer, 0, size);
					if (mTestString.equals(data)) {
						Message msg = new Message();
						msg.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("RECV", new String(mDevice
								+ " RECV : " + data));
						msg.setData(bundle);
						mHandler.sendMessage(msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_serial);

		mBtnStart = (Button) findViewById(R.id.btnSerial);
		mTextMsg = (TextView) findViewById(R.id.serialText);
		mTextMsg.setMovementMethod(ScrollingMovementMethod.getInstance());

		mBtnStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mTextMsg.setText("");
				serialTest("/dev/ttySAC0");
				serialTest("/dev/ttySAC1");
				serialTest("/dev/ttySAC2");
				serialTest("/dev/ttySAC3");
			}
		});
	}

	public void serialTest(String device) {
		SerialPort mSerialPort = null;
		FileOutputStream mOutputStream = null;
		ReadThread mReadThread = null;

		try {
			mSerialPort = new SerialPort(new File(device), 115200, 0);
			mOutputStream = (FileOutputStream) mSerialPort.getOutputStream();

			mReadThread = new ReadThread(mSerialPort, device);
			mReadThread.start();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			mTextMsg.append(device + " SEND : " + mTestString + "\n");
			mOutputStream.write(mTestString.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
