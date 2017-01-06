package org.xboot.test;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class WifiActivity extends Activity {
	private WifiManager wm;
	List<ScanResult> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);
		ListView listView = (ListView) findViewById(R.id.listView_wifi);

		wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wm.isWifiEnabled()) {
			wm.startScan();
			list = wm.getScanResults();
			if (list != null) {
				listView.setAdapter(new MyAdapter(this, list));
			}
			else
			{
				ArrayList<String> msgs = new ArrayList<String>();
				msgs.clear();
				msgs.add(0, "Can't found any wireless router!");
				listView.setAdapter(new ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, android.R.id.text1, msgs));
			}
		}
		else
		{
			ArrayList<String> msgs = new ArrayList<String>();
			msgs.clear();
			msgs.add(0, "Wifi don't open!");
			listView.setAdapter(new ArrayAdapter<String> ( this, android.R.layout.simple_list_item_1, android.R.id.text1, msgs));
		}
	}

	public class MyAdapter extends BaseAdapter {
		LayoutInflater inflater;
		List<ScanResult> list;

		public MyAdapter(Context context, List<ScanResult> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			view = inflater.inflate(R.layout.wifi_list_item, null);
			ScanResult scanResult = list.get(position);
			TextView textView = (TextView) view.findViewById(R.id.textView);
			textView.setText(scanResult.SSID);
			TextView signalStrenth = (TextView) view
					.findViewById(R.id.signal_strenth);
			signalStrenth.setText(String.valueOf(Math.abs(scanResult.level)));
			ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
			if (Math.abs(scanResult.level) > 100) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.stat_sys_wifi_signal_0));
			} else if (Math.abs(scanResult.level) > 80) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.stat_sys_wifi_signal_1));
			} else if (Math.abs(scanResult.level) > 70) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.stat_sys_wifi_signal_1));
			} else if (Math.abs(scanResult.level) > 60) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.stat_sys_wifi_signal_2));
			} else if (Math.abs(scanResult.level) > 50) {
				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.stat_sys_wifi_signal_3));
			} else {
				imageView.setImageDrawable(getResources().getDrawable(
						R.drawable.stat_sys_wifi_signal_4));
			}
			return view;
		}
	}
}
