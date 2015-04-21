package com.spendwell.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.budgetwell.R;
import com.spendwell.service.SharedService;
import com.spendwell.utils.LoginView;
import com.spendwell.utils.LoginView.onStatusListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * The login page which user see at first
 * 
 * @author Yifei Gao
 * 
 */
public class LoginActivity extends Activity {
	private ImageButton login_btn, safe_dial_btn;// Login Button
	private EditText user_et, pwd_et;// Username editText, Password editText
	private CheckBox rmb_cb;// Remember User Name check box
	private SharedService ss;// Shared Service used for storing username and
								// login tokens
	private View view_mask;
	private String user_name, user_pwd;
	private RequestQueue mQueue;
	private ProgressDialog dialog;
	private LoginView mLoginView;
	private SoundPool soundPool;
	private RelativeLayout login_box;

	// url for authentication
	private String login_url = "https://spendwell.herokuapp.com/api/token/?format=json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_main);
		// initialized all global variables
		ss = new SharedService(this);
		login_btn = (ImageButton) this.findViewById(R.id.login_btn);
		login_box = (RelativeLayout) this.findViewById(R.id.login_box);
		safe_dial_btn = (ImageButton) this.findViewById(R.id.login_safedial);
		user_et = (EditText) this.findViewById(R.id.user_name_et);
		pwd_et = (EditText) this.findViewById(R.id.pwd_et);
		rmb_cb = (CheckBox) this.findViewById(R.id.rmb_user_name_cb);
		mQueue = Volley.newRequestQueue(LoginActivity.this);
		view_mask = (View) this.findViewById(R.id.view_mask);
		mLoginView = (LoginView) this.findViewById(R.id.mLoginView);
		soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
		// pre load the sound effect for login using safe dial
		soundPool.load(getApplicationContext(), R.raw.lock_open, 1);

		// read the username if user ticked remember username box
		loadLastLogin();
		// set listener to each view
		initListener();

	}

	/**
	 * Load last login state in the Shared Preference
	 * 
	 * @author Yifei Gao
	 */
	private void loadLastLogin() {
		boolean isSaved = ss.getIsNameSave();
		// if user ticked the rmb_box, fill the username and checkbox
		if (isSaved) {
			user_et.setText(ss.readUserName());
		}
		rmb_cb.setChecked(isSaved);
	}

	/**
	 * Initialzed Listeners for each View
	 * 
	 * @author Yifei Gao
	 */
	public void initListener() {

		login_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Check for empty inputs
				if (TextUtils.isEmpty(user_et.getText())) {

					Toast.makeText(getApplicationContext(),
							R.string.name_empty_message, Toast.LENGTH_SHORT)
							.show();

				} else if (TextUtils.isEmpty(pwd_et.getText())) {

					Toast.makeText(getApplicationContext(),
							R.string.pwd_emptu_mesage, Toast.LENGTH_SHORT)
							.show();

				} else {

					user_name = user_et.getText().toString().trim();
					user_pwd = pwd_et.getText().toString().trim();

					Map<String, String> params = new HashMap<String, String>();
					params.put("username", user_name);
					params.put("password", user_pwd);

					JSONObject json = new JSONObject(params);
					// format the username and password into JsonObject and send
					// the authentication request
					showDiaLog();
					athentication(json);
				}
			}
		});

		safe_dial_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(user_et.getText().toString())) {
					// Check user name filed is not not empty
					Toast.makeText(getApplicationContext(),
							R.string.name_empty_message, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				// Display or dismiss the Safe dial
				if (mLoginView.isShow()) {
					mLoginView.dismiss();
				} else {
					mLoginView.show();
				}
			}
		});

		view_mask.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mLoginView.isShow) {
					// if the safe dial is show, show the mask, else do not show
					// the mask
					mLoginView.dismiss();
				}
			}
		});

		mLoginView.setOnStatusListener(new onStatusListener() {

			@Override
			public void onShow() {
				view_mask.setVisibility(View.VISIBLE);
			}

			@Override
			public void onDismiss() {
				view_mask.setVisibility(View.GONE);
			}
		});

		mLoginView.btn_send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String pattern = mLoginView.txt_1.getText().toString().trim()
						+ mLoginView.txt_2.getText().toString().trim()
						+ mLoginView.txt_3.getText().toString().trim()
						+ mLoginView.txt_4.getText().toString().trim();
				if (pattern.contains("*")) {
					// Check pattern do not contains empty value
					Toast.makeText(getApplicationContext(),
							"Please fill all patterns", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				showDiaLog();
				// pass the pattern and username for authentication
				athenticationViaSafeDial(pattern, user_et.getText().toString());

			}
		});

		login_box.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// hide Soft input window when click outside
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

			}
		});

	}

	/**
	 * Authentication using username and password
	 * 
	 * @author Yifei Gao
	 * @param json
	 *            JsonObject item, which contains the username and password
	 */
	private void athentication(JSONObject json) {

		JsonObjectRequest request = new JsonObjectRequest(login_url, json,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							dialog.dismiss();
							String req_token = response.getString("token");

							String username = user_et.getText().toString();
							boolean isNameSave = rmb_cb.isChecked();
							ss.save(username, req_token, isNameSave);
							// if authentication passed, stored the token and
							// start Loading Activity
							startMain();

						} catch (JSONException error) {
							error.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(),
								R.string.authentication_fail_message,
								Toast.LENGTH_SHORT).show();
						// if fail show the error message
					}
				});

		mQueue.add(request);
	}

	/**
	 * Authentication using the safedial pattern and username
	 * 
	 * @author Yifei Gao
	 * @param pattern
	 *            The safe dial pattern
	 * @param username
	 *            The username
	 */
	public void athenticationViaSafeDial(String pattern, String username) {

		String url = "http://spendwell.herokuapp.com/api/safedial/token/"
				+ pattern + "/" + username;

		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						dialog.dismiss();
						try {

							String req_token = response.getString("key");
							String name = user_et.getText().toString().trim();
							boolean isSaved = rmb_cb.isChecked();
							ss.save(name, req_token, isSaved);
							soundPool.play(1, 1, 1, 0, 0, 1);
							// if authentication passed, play the sound effect
							// and start the loading activity
							startMain();
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						dialog.dismiss();
						Toast.makeText(getApplicationContext(),
								R.string.authentication_safe_dial_message,
								Toast.LENGTH_SHORT).show();
						// if fail show the error message
					}
				});
		mQueue.add(request);
	}

	/**
	 * Show hte Progess Dialog while sending request
	 * 
	 * @author Yifei Gao
	 */
	public void showDiaLog() {
		dialog = ProgressDialog.show(LoginActivity.this, "Authentication",
				"Processing your request...");
	}

	/**
	 * Start MainAcitvity with username in the intent
	 * 
	 * @author Yifei Gao
	 */
	public void startMain() {
		Intent it = new Intent(getApplicationContext(), LoadingActivity.class);
		it.putExtra("name", user_et.getText().toString());
		startActivity(it);
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * Override this method to dismiss the safe dial when user press back
	 */
	@Override
	public void onBackPressed() {
		if (mLoginView.isShow) {
			mLoginView.dismiss();
			return;
		}

		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
