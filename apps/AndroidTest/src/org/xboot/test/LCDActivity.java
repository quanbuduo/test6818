package org.xboot.test;

import org.xboot.test.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View.OnClickListener;
import android.view.View;

public class LCDActivity extends Activity {
	private View mBackView;
	private int colorIndex = 0;
	private int[] colorArray =  {Color.RED, Color.GREEN, Color.BLUE, Color.WHITE, Color.BLACK};
	GestureDetector gestureScanner; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lcd);
		
		mBackView = (View)findViewById(R.id.backView);
		mBackView.setBackgroundColor(colorArray[colorIndex]); 
		mBackView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				colorIndex = (colorIndex + 1) % colorArray.length;
				mBackView.setBackgroundColor(colorArray[colorIndex]);
			}
		});
	}
}
