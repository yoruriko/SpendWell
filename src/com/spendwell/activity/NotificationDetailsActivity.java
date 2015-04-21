package com.spendwell.activity;

import com.example.budgetwell.R;
import com.spendwell.entity.NotificaitonItem;
import com.spendwell.service.SharedService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity shows the notification details after user click reminder item
 * in the reminder list, required intent contains the position of the
 * notification item
 * 
 * @author Yifei Gao
 */
public class NotificationDetailsActivity extends Activity {
	private ImageView level_img;
	private TextView title, text, date;
	private SharedService ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.notitification_item_details);
		// initialized all global variables
		level_img = (ImageView) findViewById(R.id.level_img);
		title = (TextView) findViewById(R.id.title);
		text = (TextView) findViewById(R.id.text);
		date = (TextView) findViewById(R.id.date);
		ss = new SharedService(this);

		// get position of the notification item from the intent
		initData();
		// set up the action bar
		setActionBar();
	}

	/**
	 * get position of the item from the intent, get item from the Shared
	 * Preferences
	 * 
	 * @author Yifei Gao
	 */
	public void initData() {
		int position = getIntent().getExtras().getInt("position");
		NotificaitonItem item = ss.getAllNotifications().get(position);
		// obtain the item from the shared service

		date.setText(item.getFormatDate());
		text.setText(item.getText());
		if (item.getLevel() == NotificaitonItem.INFO) {
			title.setText("New Infomation");
			level_img.setImageResource(R.drawable.info);
		} else if (item.getLevel() == NotificaitonItem.SUCCESS) {
			title.setText("New Transaction");
			level_img.setImageResource(R.drawable.success);
		} else if (item.getLevel() == NotificaitonItem.WARNING) {
			title.setText("Warning");
			level_img.setImageResource(R.drawable.warning);
		} else {
			title.setText("Danger!");
			level_img.setImageResource(R.drawable.danger);
		}
	}

	/**
	 * Set the title of the action bar and back button
	 * 
	 * @author Yifei Gao
	 */
	public void setActionBar() {
		TextView text = (TextView) this.findViewById(R.id.actionbar_text);
		text.setText("Details");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
