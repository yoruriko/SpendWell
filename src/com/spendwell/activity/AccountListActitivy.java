package com.spendwell.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.budgetwell.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spendwell.entity.BankAccount;
import com.spendwell.fragment.AccountFragment;
import com.spendwell.service.SharedService;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The Activity show up after shake event, using for display account details
 * 
 * @author Yifei Gao
 * 
 */
public class AccountListActitivy extends FragmentActivity {

	// Url for reading account request
	private String url = "http://spendwell.herokuapp.com/api/accounts/?format=json";
	private String username;
	private SharedService ss;
	private RequestQueue mQueue;
	// Global variable to store the response
	private List<BankAccount> mList;
	private ViewPager viewPager;
	// Image views for dots under the viewPager
	private ImageView dots[];
	// Adapter for each Fragment page
	private AccountAdapter adapter;

	private LinearLayout viewGroup;

	// Handle the response and set up the adapter for fragments
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				setAdapter();
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.account_list_activity);
		// Initialized global variables
		ss = new SharedService(this);
		username = ss.readUserName();
		viewPager = (ViewPager) this.findViewById(R.id.view_pager);
		mQueue = Volley.newRequestQueue(this);
		viewGroup = (LinearLayout) this.findViewById(R.id.viewGroup);

		// Set result code for Shake fragment to start onShake listener
		setResult(200);

		// Set the included Actionbar in the layout
		setActionbar();

		// Read account list from server and apply them on the page
		perpareAccList();
	}

	/**
	 * Sending request via Volley and send the response to the handler
	 * 
	 * @author Yifei Gao
	 */
	public void perpareAccList() {

		// Construct the Request to given url, the response should be a
		// JsonArray
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {

						String json = response.toString();
						Gson gson = new Gson();
						mList = gson.fromJson(json,
								new TypeToken<List<BankAccount>>() {
								}.getType());
						// Use Gson to convert response Json into List of
						// BankAccount
						ss.saveAccountList(mList);
						// update the Account List in the Shared Preference
						Message m = mHandler.obtainMessage(1, null);
						m.sendToTarget();
						// send message to handler to set the adapter
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Toast.makeText(getApplicationContext(),
								"Loading account details fail...",
								Toast.LENGTH_SHORT).show();

					}
				}) {

			/**
			 * Override the Header to obtain the Authrioztion Token in the
			 * Header
			 * 
			 * @author Yifei Gao
			 */
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> header = new HashMap<String, String>();
				header.put("Authorization", "Token " + ss.getRequestToken());
				return header;
			}

		};

		// Add the reqest into the Request queue
		mQueue.add(request);
	}

	/**
	 * Set adapter to the viewpager using the response
	 * 
	 * @author Yifei Gao
	 */
	public void setAdapter() {
		adapter = new AccountAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(new AccountChangeListener());
		setDots();
	}

	/**
	 * Initialized the dots under the viewpager depends on the number of
	 * Accounts
	 * 
	 * @author Yifei Gao
	 */
	public void setDots() {
		// initialized the array to the size of the Account list
		dots = new ImageView[mList.size()];

		for (int i = 0; i < dots.length; i++) {
			// set margin and LayoutParams for each imageview
			LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			margin.setMargins(15, 0, 0, 0);

			ImageView view = new ImageView(this);
			view.setLayoutParams(new LayoutParams(25, 25));
			dots[i] = view;

			// the first dot is selected as default
			if (i == 0) {
				dots[i].setImageResource(R.drawable.dot_selected);
			} else {
				dots[i].setImageResource(R.drawable.dot_noraml);
			}

			// Add the imageView into the Viewgroup
			viewGroup.addView(dots[i], margin);
		}

	}

	/**
	 * Set the Text of the actionbar and onclick event of the back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionbar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Accounts");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	/**
	 * Inner Class that extends FragmentPagerAdapter to return AccountFragment
	 * 
	 * @author Yifei Gao
	 */
	public class AccountAdapter extends FragmentPagerAdapter {

		public AccountAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// return AccountFragment using response data
			AccountFragment fragment = new AccountFragment(mList.get(position),
					username);
			return fragment;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

	}

	/**
	 * Inner Class that implements OnPageChangeListener to Change the dots
	 * 
	 * @author asus
	 * 
	 */
	public class AccountChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			// Set the dot at current position selected, the rest as normal
			for (int i = 0; i < dots.length; i++) {
				if (i == position)
					dots[i].setImageResource(R.drawable.dot_selected);
				else
					dots[i].setImageResource(R.drawable.dot_noraml);
			}
		}

	}

}
