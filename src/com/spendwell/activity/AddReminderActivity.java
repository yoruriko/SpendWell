package com.spendwell.activity;

import com.example.budgetwell.R;
import com.google.gson.Gson;
import com.spendwell.entity.ReminderItem;
import com.spendwell.utils.ReminderSQLServiceImpl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity used for adding reminder, Start by intent for result, result must be
 * set
 * 
 * @author Yifei Gao
 * 
 */
public class AddReminderActivity extends Activity {
	public static final String TAG = AddReminderActivity.class.getSimpleName();

	// Global variables for views in the layout
	private Spinner type, payType;
	private EditText target, amount, description;
	private DatePicker date;
	private ImageButton save;
	private RelativeLayout layout_box;

	// Instance of the SQLImpl service
	private ReminderSQLServiceImpl impl;

	// a day in millisecond
	private long oneday = 1000 * 60 * 60 * 24;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_reminder_layout);

		// Initialized global variables
		layout_box = (RelativeLayout) findViewById(R.id.layout_box);
		type = (Spinner) findViewById(R.id.type_spinner);
		payType = (Spinner) findViewById(R.id.pay_type_spinner);
		target = (EditText) findViewById(R.id.target_edt);
		amount = (EditText) findViewById(R.id.amount_edt);
		date = (DatePicker) findViewById(R.id.date_picker);
		// set the min date of the date picker to be one day after today
		date.setMinDate(System.currentTimeMillis() + oneday);
		description = (EditText) findViewById(R.id.description_edt);
		save = (ImageButton) findViewById(R.id.save_btn);

		impl = new ReminderSQLServiceImpl(this);

		// Initialized all listeners in the view
		initListener();
		// Set the included Actionbar in the layout
		setActionbar();
	}

	/**
	 * Initialized all listener and match them with given views
	 * 
	 * @author Yifei Gao
	 */

	public void initListener() {
		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// check for empty inputs
				if (TextUtils.isEmpty(target.getText())) {
					Toast.makeText(getApplicationContext(),
							"please fill the target name", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (TextUtils.isEmpty(amount.getText())
						|| Double.parseDouble(amount.getText().toString()) <= 0) {
					Toast.makeText(getApplicationContext(),
							"Amount must be filled and greater than 0",
							Toast.LENGTH_SHORT).show();
					return;
				}

				// create new item and fill its fileds
				ReminderItem item = new ReminderItem();
				item.setType(type.getSelectedItemPosition());
				item.setPayType(payType.getSelectedItemPosition());
				item.setAmount(Double.parseDouble(amount.getText().toString()
						.trim()));
				item.setTargetName(target.getEditableText().toString().trim());
				item.setDescription(description.getText().toString().trim());
				item.setAlarm(false);
				item.setPaid(false);

				// format the date String
				String mounth, day;
				if (date.getMonth() < 9) {
					mounth = "0" + (date.getMonth() + 1);
				} else {
					mounth = "" + (date.getMonth() + 1);
				}
				if (date.getDayOfMonth() < 10) {
					day = "0" + (date.getDayOfMonth());
				} else {
					day = date.getDayOfMonth() + "";
				}

				String dateString = date.getYear() + "-" + mounth + "-" + day;

				item.setDate(dateString);

				// Pass the Reminder Item as a Json format String
				Intent it = new Intent();
				Gson gson = new Gson();
				String json = gson.toJson(item);

				it.putExtra("item", json);
				setResult(200, it);

				Log.i(TAG, "Reminder item " + dateString + " is saved");
				// close this activity
				finish();
			}
		});

		// Hide sort input window when user click outside
		layout_box.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			}
		});
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionbar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("New Reminder");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

}
