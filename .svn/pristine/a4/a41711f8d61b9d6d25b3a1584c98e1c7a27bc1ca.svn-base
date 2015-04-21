package com.spendwell.activity;

import java.util.Date;

import com.example.budgetwell.R;
import com.spendwell.entity.ReminderItem;
import com.spendwell.service.ReminderReceiver;
import com.spendwell.service.SharedService;
import com.spendwell.utils.ReminderSQLServiceImpl;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

/**
 * This activity shows the details of the reminder item, handle the pay and
 * alarm setting of the reminder, id of the item is required in the intent
 * 
 * @author Yifei Gao
 * 
 */
public class ReminderDetailActivity extends Activity {

	private ImageView type_img;
	private TextView type_txt, amount_txt, pay_type, pay_state, date_txt,
			target_txt, description;
	private ImageButton pay_btn;
	private Switch alarm_switch;
	private int item_id;
	private ReminderSQLServiceImpl impl;
	private ReminderItem item;
	private SharedService ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reminder_detail_layout);

		// Initialized all global variables
		type_img = (ImageView) this.findViewById(R.id.type_img);
		type_txt = (TextView) this.findViewById(R.id.type_txt);
		amount_txt = (TextView) this.findViewById(R.id.amount_txt);
		pay_state = (TextView) this.findViewById(R.id.pay_state);
		pay_type = (TextView) this.findViewById(R.id.pay_type);
		date_txt = (TextView) this.findViewById(R.id.date_txt);
		target_txt = (TextView) this.findViewById(R.id.target_txt);
		description = (TextView) this.findViewById(R.id.description);
		pay_btn = (ImageButton) this.findViewById(R.id.pay_btn);
		alarm_switch = (Switch) this.findViewById(R.id.alarm_switch);
		ss = new SharedService(getApplicationContext());
		item_id = getIntent().getExtras().getInt("item_id");
		// obtain the id of the item

		impl = new ReminderSQLServiceImpl(this);
		item = impl.getItemById(item_id);
		// obtain from database using the id

		if (item != null) {
			// set the page content if the item exists
			setContent();
		}

		// set up the action bar
		setActionBar();
		setResult(300);
		// set result for the mainActivity to handle the change in item details
	}

	/**
	 * Set the page contents using the reminder item details
	 * 
	 * @author Yifei Gao
	 */
	public void setContent() {
		// set the type image and text
		switch (item.getType()) {
		case ReminderItem.MEAL:
			type_img.setImageResource(R.drawable.meal_type);
			type_txt.setText("Meal");
			break;
		case ReminderItem.DRINK:
			type_img.setImageResource(R.drawable.drink_type);
			type_txt.setText("Drink");
			break;
		case ReminderItem.UTILITIES_BILL:
			type_img.setImageResource(R.drawable.utilities_bill_tye);
			type_txt.setText("Utilities Bill");
			break;
		case ReminderItem.ACCOMMODATION:
			type_img.setImageResource(R.drawable.accommodation_type);
			type_txt.setText("Accommodation");
			break;
		case ReminderItem.TRAVEL:
			type_img.setImageResource(R.drawable.travel_type);
			type_txt.setText("Travel");
			break;
		case ReminderItem.SOCIAL:
			type_img.setImageResource(R.drawable.social_type);
			type_txt.setText("Social");
			break;
		case ReminderItem.TRANSPORT:
			type_img.setImageResource(R.drawable.transport_type);
			type_txt.setText("Transport");
			break;
		case ReminderItem.ELSE:
			type_img.setImageResource(R.drawable.else_type);
			type_txt.setText("Else");
			break;

		default:
			type_img.setImageResource(R.drawable.else_type);
			type_txt.setText("Else");
			break;
		}

		// set the pay type text
		if (item.getPayType() == ReminderItem.OWE) {
			pay_type.setText("Owe");
		} else if (item.getPayType() == ReminderItem.LEND) {
			pay_type.setText("Lend");
		}

		// if the item is paid, hide some contents in the page
		if (!item.isPaid()) {
			pay_btn.setVisibility(View.VISIBLE);
			pay_state.setText("Not Paid");

			pay_btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					showPayDialog();
				}
			});

		} else {
			pay_btn.setVisibility(View.GONE);
			pay_state.setText("Paid");
			alarm_switch.setVisibility(View.GONE);
		}

		amount_txt.setText(item.getAmount() + "");
		date_txt.setText(item.getDate());
		target_txt.setText(item.getTargetName());
		description.setText(item.getDescription());

		// if the date of the item is before today, set the alarm switch gone
		if (item.getAlarmTime().before(new Date(System.currentTimeMillis()))) {
			alarm_switch.setVisibility(View.GONE);
		}

		if (alarm_switch.getVisibility() == View.VISIBLE) {
			alarm_switch.setChecked(item.isAlarm());
			alarm_switch
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						// Handle the alarm switch change
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								impl.changeAlarm(item_id, isChecked);
								setAlarm();
							} else {
								impl.changeAlarm(item_id, isChecked);
								cancelAlarm();
							}
						}
					});
		}
	}

	/**
	 * Display the Alert Dialog when user try to pay the reminder
	 * 
	 * @author Yifei Gao
	 */
	public void showPayDialog() {

		AlertDialog.Builder builder = new Builder(this);

		builder.setMessage("Are you sure this reminder is paid?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				pay_btn.setVisibility(View.GONE);
				pay_state.setText("Paid");
				alarm_switch.setVisibility(View.GONE);
				item.setPaid(true);
				impl.changeAlarm(item_id, false);
				cancelAlarm();// cancel the alarm if the reminder is paid
				impl.changePay(item_id);// change the state in the database
			}
		});

		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		builder.create().show();
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Reminder");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	/**
	 * Set the alarm using the item date and id
	 * 
	 * @author Yifei Gao
	 */
	public void setAlarm() {
		Intent it = new Intent(getApplicationContext(), ReminderReceiver.class);

		it.setAction("com.spendwell.action.ReminderAlarm");

		it.putExtra("item_id", item_id);

		// Construct the pending Intent using the remidner item
		PendingIntent pit = PendingIntent.getBroadcast(getApplicationContext(),
				item_id, it, 0);

		// testing code
		// for testing(today+set alarm time)
		// Date date=new Date(2015-1900, 3, 12);
		// long time=date.getTime()+ss.getReminderTime();

		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		// item.getAlarmTime() return the 00:00 long at item's date
		am.set(AlarmManager.RTC_WAKEUP,
				item.getAlarmTime().getTime() + ss.getReminderTime(), pit);

		// am.set(AlarmManager.RTC_WAKEUP,time , pit);

	}

	/**
	 * Cancel the alarm using the item id and date
	 * 
	 * @author Yifei Gao
	 */
	public void cancelAlarm() {
		Intent it = new Intent(getApplicationContext(), ReminderReceiver.class);

		it.setAction("com.spendwell.action.ReminderAlarm");

		it.putExtra("item_id", item_id);

		//Reconstruct the pending Intent using the item
		PendingIntent pit = PendingIntent.getBroadcast(getApplicationContext(),
				item_id, it, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(pit);
	}

}
