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
import com.spendwell.service.SharedService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Loading Page after login
 * 
 * @author Yifei Gao
 * 
 */
public class LoadingActivity extends Activity {
	private int progress = 0;
	private ProgressBar progressBar;
	private TextView name;
	private SharedService ss;
	private RequestQueue mQueue;
	// url to obtain account details
	private String url = "http://spendwell.herokuapp.com/api/accounts/?format=json";
	private List<BankAccount> mList;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				if (progress <= 100) {
					progress += 1;
					progressBar.setProgress(progress);
					// increases counter by 1 if progress smaller than 100
					update();
				} else {
					// start MainActiviy when progress is 100
					Intent it = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(it);
					finish();
				}
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading_layout);
		progressBar = (ProgressBar) this.findViewById(R.id.progress);
		name = (TextView) this.findViewById(R.id.name);
		// Get username form intent that start this activity
		String username = getIntent().getExtras().getString("name");
		name.setText(username);
		ss = new SharedService(this);
		mQueue = Volley.newRequestQueue(this);
		readAccounts();
	}

	public void update() {
		// send a empty message to handler which will increase the counter by 1
		handler.obtainMessage(1).sendToTarget();
	}

	/**
	 * JsonObject Request to obtain user's Account details
	 * 
	 * @author Yifei Gao
	 */
	public void readAccounts() {
		JsonArrayRequest request = new JsonArrayRequest(url,
				new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						String json = response.toString();
						Gson gson = new Gson();
						mList = gson.fromJson(json,
								new TypeToken<List<BankAccount>>() {
								}.getType());
						// save user's account details into Shared Preference
						ss.saveAccountList(mList);
						// update the progress
						update();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						error.printStackTrace();
						Toast.makeText(getApplicationContext(),
								"Loading account details fail...",
								Toast.LENGTH_SHORT).show();
						// if fail show the fail message and return to the login
						// page
						Intent it = new Intent(getApplicationContext(),
								LoginActivity.class);
						startActivity(it);
						finish();

					}
				}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> header = new HashMap<String, String>();
				header.put("Authorization", "Token " + ss.getRequestToken());
				return header;
			}

		};
		mQueue.add(request);
	}

}
