package org.xboot.test;

import java.io.IOException;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
	private Button mBtnCamera;
	private SurfaceView mCameraPreview;
	private SurfaceHolder mHolder;
	private Camera mCamera = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.activity_camera);
		mBtnCamera = (Button) findViewById(R.id.btnCamera);
		mCameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
		mCameraPreview.setZOrderOnTop(true);
		mCameraPreview.getHolder().setFormat(PixelFormat.TRANSPARENT);
		mHolder = mCameraPreview.getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		mBtnCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mCamera == null) {
					mCamera = getCameraInstance();
					if (mCamera != null) {
						try {
							mCamera.setPreviewDisplay(mHolder);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						mCamera.startPreview();
					}
				}
			}
		});
	}

	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open();
		} catch (Exception e) {
			c = null;
		}
		return c;
	}
	
	public void initial()
	{
	}
	
	public void release()
	{
		mHolder = null;
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
	
	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		mHolder = surfaceholder;
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		release();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}
}