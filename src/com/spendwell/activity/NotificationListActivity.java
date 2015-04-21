package com.spendwell.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.budgetwell.R;
import com.spendwell.adapter.NotificationItemAdapter;
import com.spendwell.entity.NotificaitonItem;
import com.spendwell.service.SharedService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * Activity that shows basic detail of the notification in a listview
 * 
 * @author Yifei Gao
 * 
 */
public class NotificationListActivity extends Activity {
	private ListView listView;
	private List<NotificaitonItem> list;
	private NotificationItemAdapter adapter;
	private SharedService ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// find instance and match the global variables
		setContentView(R.layout.notification_list_layout);
		listView = (ListView) findViewById(R.id.notificaiton_list);
		ss = new SharedService(getApplicationContext());
		list = new ArrayList<NotificaitonItem>();
		// get all notifications from shared services

		// set adapter to the listview using the notification list
		initData();
		// set up the action bar
		setActionBar();
	}

	/**
	 * Set adapter using the given listview and Notification List
	 * 
	 * @author Yifei Gao
	 */
	public void initData() {
		list = ss.getAllNotifications();
		adapter = new NotificationItemAdapter(this, list);
		listView.setAdapter(adapter);

		// set the click event for each notification item
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(getApplicationContext(),
						NotificationDetailsActivity.class);
				it.putExtra("position", position);
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
		text.setText("Notifications");

		Button back = (Button) this.findViewById(R.id.actionbar_btn);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
