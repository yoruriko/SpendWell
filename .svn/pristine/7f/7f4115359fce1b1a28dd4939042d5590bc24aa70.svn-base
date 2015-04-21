package com.spendwell.activity;

import com.example.budgetwell.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * This Activity Display and direct user to the help page of the website
 * 
 * @author Yifei Gao
 * 
 */
public class HelpActivity extends Activity {

	private WebView webView;
	// url of the help page
	private String url = "http://spendwell.herokuapp.com/help/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.help_activity_layout);
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);

		// set Client for the webView
		webView.setWebViewClient(new MyWebViewClient());
		// pass the url
		webView.loadUrl(url);
		// Set the actionbar layout
		setActionBar();
	}

	/**
	 * Override onKeyDown event so when the webView can go back, do not
	 * terminate this Acitivity
	 * 
	 * @author Yifei Gao
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (webView.canGoBack()) {
				webView.goBack();
			} else {
				finish();
			}
		}
		return false;
	}

	/**
	 * Override shouldOverrideUrlLoading to avoid opening browsers
	 * 
	 * @author Yifei Gao
	 * 
	 */

	class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Help");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
