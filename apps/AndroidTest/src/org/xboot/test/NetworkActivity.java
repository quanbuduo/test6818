package org.xboot.test;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NetworkActivity extends Activity {
	private WebView mWVNetwork;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network);

		mWVNetwork = (WebView) findViewById(R.id.word_web_view);
		mWVNetwork.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		mWVNetwork.loadUrl("http://wap.hao123.com");
	}
}
