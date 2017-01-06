package org.xboot.test;

import org.xboot.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class TSActivity extends Activity implements OnTouchListener {
	private Button btnTouchTest;		

	@Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_ts);

        btnTouchTest=(Button)findViewById(R.id.btnTouchScreen); 
        btnTouchTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(TSActivity.this, TSDrawActivity.class), 1);
			}
		});
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}
}
