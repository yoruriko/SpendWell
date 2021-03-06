package com.spendwell.activity;

import com.example.budgetwell.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This Activity Show all Social media page of spendwell
 * 
 * @author Yifei Gao
 * 
 */
public class LikeUsActivity extends Activity {

	private ImageButton facebook, twitter, instagram, youtube, google_plus,
			tumblr;

	// url for social websites
	private String facebook_url = "https://www.facebook.com/pages/SpendWell/471774039639991";
	private String twitter_url = "https://twitter.com/SpendWell_";
	private String instagram_url = "https://instagram.com/spendwell_/";
	private String youtube_url = "https://www.youtube.com/channel/UCgmQPAJAZv7tPYpGVTq9Gug?view_as=public";
	private String google_plus_url = "https://plus.google.com/u/0/102916740668963918074/about";
	private String tumblr_url = "http://spendwell.tumblr.com/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.like_us_layout);

		// Initialized all global variables
		facebook = (ImageButton) this.findViewById(R.id.facebook);
		twitter = (ImageButton) this.findViewById(R.id.twitter);
		instagram = (ImageButton) this.findViewById(R.id.instagram);
		youtube = (ImageButton) this.findViewById(R.id.youtube);
		google_plus = (ImageButton) this.findViewById(R.id.google_plus);
		tumblr = (ImageButton) this.findViewById(R.id.tumblr);
		// Set listeners to each Button
		initListener();
		// Set actionbar layout
		setActionBar();
	}

	/**
	 * Set onClick Listener to each ImageView, starting brower to load the url
	 * 
	 * @author Yifei Gao
	 */
	public void initListener() {
		facebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setData(Uri.parse(facebook_url));
				startActivity(it);
			}
		});

		twitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setData(Uri.parse(twitter_url));
				startActivity(it);
			}
		});

		instagram.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setData(Uri.parse(instagram_url));
				startActivity(it);
			}
		});

		youtube.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setData(Uri.parse(youtube_url));
				startActivity(it);
			}
		});

		google_plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setData(Uri.parse(google_plus_url));
				startActivity(it);
			}
		});

		tumblr.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW);
				it.setData(Uri.parse(tumblr_url));
				startActivity(it);
			}
		});
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Like us");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

}
