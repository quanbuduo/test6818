package org.xboot.test;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import org.xboot.test.R;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {
	private View lcdView;
	private View tsView;
	private View ledView;
	private View beepView;
	private View backlightView;
	private View keyBoardView;
	private View batteryView;
	private View adcView;
	private View gsensorView;
	private View audioView;
	private View cameraView;
	private View wifiView;
	private View networkView;
	private View serialView;
	private View sdcardView;
	private View udiskView;
	private ArrayList<View> views;
	private ViewPager mViewPager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		views = new ArrayList<View>();
		mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
		mViewPager.setOnPageChangeListener(null);
		mViewPager.setActivated(true);
		initView();
		addViews();

		PagerAdapter mPagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public Object instantiateItem(View container, int position) {
				View view = views.get(position);
				if(view == cameraView)
				{
					CameraActivity CActivity = (CameraActivity) (cameraView.getContext());
					CActivity.initial();
				}
				else if(view == batteryView)
				{
					BatteryActivity BActivity = (BatteryActivity) (batteryView.getContext());
					BActivity.initial();
				}
				else if(view == adcView)
				{
					AdcActivity AActivity = (AdcActivity) (adcView.getContext());
					AActivity.initial();
				}
				else if(view == gsensorView)
				{
					GSensorActivity GActivity = (GSensorActivity) (gsensorView.getContext());
					GActivity.initial();
				}
				((ViewPager) container).addView(view);
				return view;
			}
			
			@Override
			public void destroyItem(View container, int position, Object object) {
				View view = views.get(position);
				if(view == cameraView)
				{
					CameraActivity CActivity = (CameraActivity) (cameraView.getContext());
					CActivity.release();
				}
				else if(view == batteryView)
				{
					BatteryActivity BActivity = (BatteryActivity) (batteryView.getContext());
					BActivity.release();
				}
				else if(view == adcView)
				{
					AdcActivity AActivity = (AdcActivity) (adcView.getContext());
					AActivity.release();
				}
				else if(view == gsensorView)
				{
					GSensorActivity GActivity = (GSensorActivity) (gsensorView.getContext());
					GActivity.release();
				}
				((ViewPager) container).removeView(view);
			}
		};
		mViewPager.setAdapter(mPagerAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		CameraActivity CActivity = (CameraActivity) (cameraView.getContext());
		CActivity.release();
		
		BatteryActivity BActivity = (BatteryActivity) (batteryView.getContext());
		BActivity.release();
		
		AdcActivity AActivity = (AdcActivity) (adcView.getContext());
		AActivity.release();
		
		GSensorActivity GActivity = (GSensorActivity) (gsensorView.getContext());
		GActivity.release();
	}

	public void initView() {
		lcdView = getViews(LCDActivity.class, "one");
		tsView = getViews(TSActivity.class, "one");
		ledView = getViews(LedActivity.class, "one");
		beepView = getViews(BeepActivity.class, "one");
		backlightView = getViews(BacklightActivity.class, "one");
		keyBoardView = getViews(KeyBoardActivity.class, "one");
		batteryView = getViews(BatteryActivity.class, "one");
		adcView = getViews(AdcActivity.class, "one");
		gsensorView = getViews(GSensorActivity.class, "one");
		audioView = getViews(AudioActivity.class, "one");
		cameraView = getViews(CameraActivity.class, "one");
		wifiView=getViews(WifiActivity.class,"one");
		networkView = getViews(NetworkActivity.class, "one");
		serialView = getViews(SerialActivity.class, "one");
		sdcardView = getViews(SdcardActivity.class, "one");
		udiskView = getViews(UdiskActivity.class, "one");
	}

	public void addViews() {
		views.add(lcdView);
		views.add(tsView);
		views.add(ledView);
		views.add(beepView);
		views.add(backlightView);
		views.add(keyBoardView);
		views.add(batteryView);
		views.add(adcView);
		views.add(gsensorView);
		views.add(audioView);
		views.add(cameraView);
		views.add(wifiView);
		views.add(networkView);
		views.add(serialView);
		views.add(sdcardView);
		views.add(udiskView);
	}

	public View getViews(Class<?> cls, String pageid) {
		return getLocalActivityManager().startActivity(
				pageid,
				new Intent(MainActivity.this, cls)
						.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT))
				.getDecorView();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (keyBoardView == views.get(mViewPager.getCurrentItem())) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					|| event.getAction() == KeyEvent.ACTION_UP) {
				Context context = keyBoardView.getContext();
				if (context instanceof Activity) {
					KeyBoardActivity activity = (KeyBoardActivity) context;
					Message message = new Message();
					message.what = 1;
					Bundle data = new Bundle();
					data.putInt("action", event.getAction());
					data.putInt("code", event.getKeyCode());
					message.setData(data);
					activity.mHandler.sendMessage(message);
				}
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			this.getLocalActivityManager().getCurrentActivity()
					.openOptionsMenu();
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}