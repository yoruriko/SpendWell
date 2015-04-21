package com.spendwell.activity;

import com.example.budgetwell.R;
import com.spendwell.service.SharedService;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * This activity change the setting flags in the Shared Service, allow user to
 * set the alarm item and disable the vibrate and sound of the reminder
 * 
 * @author Yifei Gao
 * 
 */
public class SettingActivity extends Activity {

	private Switch vibrate_switch, sound_switch;
	private SharedService ss;
	private RelativeLayout time_box;
	private TextView time_tv;
	private boolean isTimeSaved;

	private static final int TIME_PICKER_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setting_layout);

		ss = new SharedService(getApplicationContext());
		vibrate_switch = (Switch) this.findViewById(R.id.vibrate_switch);
		vibrate_switch.setChecked(ss.getVibrate());
		sound_switch = (Switch) this.findViewById(R.id.sound_switch);
		sound_switch.setChecked(ss.getSound());
		time_box = (RelativeLayout) this.findViewById(R.id.time_box);
		time_tv = (TextView) this.findViewById(R.id.time_tv);
		setTime();// initialized the time

		// initialized all listeners in the layout
		initListener();
		// set up the action bar
		setActionBar();

	}

	/**
	 * Set the listener to each switch and buttons
	 * 
	 * @author Yifei Gao
	 */
	public void initListener() {
		vibrate_switch
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						ss.setVibrate(isChecked);// change the flag in Shared
													// Service
					}
				});

		sound_switch
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						ss.setSound(isChecked);// change the flag in Shared
												// Service
					}
				});

		time_box.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimeDialog();// show the time picker dialog
			}
		});
	}

	/**
	 * Show the time picker dialog for user to select the alarm time
	 * 
	 * @author Yifei Gao
	 */
	public void showTimeDialog() {

		TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

				if (isTimeSaved) {
					long time = (hourOfDay * 60 * 60 * 1000)
							+ (minute * 60 * 1000);

					ss.setReminderTime(time);// store the alarm time if user
												// press done
					setTime();// display the new alarm time
				}

			}
		};

		long time = ss.getReminderTime();

		int hr = (int) time / 60 / 60 / 1000;
		int min = (int) ((time - (hr * 60 * 60 * 1000)) / 1000 / 60);

		TimePickerDialog dialog = new TimePickerDialog(this, listener, hr, min,
				true);
		dialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, "Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						isTimeSaved = false;
					}
				});
		dialog.setButton(TimePickerDialog.BUTTON_POSITIVE, "Done",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						isTimeSaved = true;
					}
				});

		dialog.show();
	}

	/**
	 * Read the time from the shared service, for the string and display on the
	 * textview
	 * 
	 * @author Yifei Gao
	 */
	public void setTime() {
		long time = ss.getReminderTime();
		int hr = (int) time / 60 / 60 / 1000;
		int min = (int) (time - (hr * 60 * 60 * 1000)) / 1000 / 60;

		String minute, hour;

		if (hr < 10) {
			hour = "0" + hr;
		} else {
			hour = "" + hr;
		}

		if (min < 10) {
			minute = "0" + min;
		} else {
			minute = min + "";
		}

		time_tv.setText(hour + ":" + minute);
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Setting");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

}