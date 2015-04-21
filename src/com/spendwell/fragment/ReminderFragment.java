package com.spendwell.fragment;

import java.util.List;

import com.example.budgetwell.R;
import com.google.gson.Gson;
import com.spendwell.activity.AddReminderActivity;
import com.spendwell.activity.ReminderDetailActivity;
import com.spendwell.adapter.ReminderItemAdapter;
import com.spendwell.entity.ReminderItem;
import com.spendwell.service.ReminderReceiver;
import com.spendwell.utils.ReminderSQLServiceImpl;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * Fragment that obtained in the MainActivity's View pager, display user's
 * reminders
 * 
 * @author Yifei Gao
 * 
 */
public class ReminderFragment extends Fragment {
	private ListView listView;
	private List<ReminderItem> list;
	private ImageButton add_btn;
	private ReminderSQLServiceImpl impl;
	private ReminderItemAdapter adapter;

	private ReminderItem delete_item;

	private static final int ADD_REQUEST = 100;
	private static final int CHANGE_DATA = 101;

	private static final int DELETE_RESULT = 200;
	private static final int CHANGE_RESULT = 300;

	private static final int REMOVE_REMINDER_ITEM = 1;
	private static final int INSERT_REMINDER_ITEM = 2;
	private static final int CONFRIM_DELETE_ITEM = 3;

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == REMOVE_REMINDER_ITEM) {
				ReminderItem item = (ReminderItem) msg.obj;
				delete_item = item;
				showAlerDialog();// ask user to confirm the deletion
			}

			if (msg.what == INSERT_REMINDER_ITEM) {
				ReminderItem item = (ReminderItem) msg.obj;
				impl.insertReminder(item);
				// add the item into database
				reloadData();
				// reload the list
			}

			if (msg.what == CONFRIM_DELETE_ITEM) {
				cancelAlarm(delete_item.getIdInTable());
				// cancel the alarm if there is one
				impl.deleteByItem(delete_item);
				// remove the item from the database
				reloadData();
				// reload the list
			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.reminder_list_layout, null);
		listView = (ListView) view.findViewById(R.id.reminder_list_view);
		add_btn = (ImageButton) view.findViewById(R.id.add_btn);
		impl = new ReminderSQLServiceImpl(getActivity());

		list = impl.getAll();

		adapter = new ReminderItemAdapter(getActivity(), list, mHandler);
		listView.setAdapter(adapter);
		// set onClick listener to each item in the list
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(getActivity(),
						ReminderDetailActivity.class);
				it.putExtra("item_id", list.get(position).getIdInTable());
				// put the item id into the intent
				startActivityForResult(it, CHANGE_DATA);
			}
		});

		add_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), AddReminderActivity.class);
				startActivityForResult(it, ADD_REQUEST);
			}
		});
		return view;
	}

	/**
	 * AlertDialog that ask user to confirm the transaction details
	 * 
	 * @author Yifei Gao
	 */
	public void showAlerDialog() {
		AlertDialog.Builder builder = new Builder(getActivity());

		builder.setMessage("Are you sure to delete this Reminder?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mHandler.sendEmptyMessage(CONFRIM_DELETE_ITEM);
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
	 * Handle the result from the addReminder and ReminderDetail Activity
	 * 
	 * @author Yifei Gao
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == DELETE_RESULT) {
			String json = data.getExtras().getString("item");
			Gson gson = new Gson();
			ReminderItem item = gson.fromJson(json, ReminderItem.class);
			// get the item from the result intent
			mHandler.obtainMessage(INSERT_REMINDER_ITEM, item).sendToTarget();
		}

		if (resultCode == CHANGE_RESULT) {
			// reload data
			reloadData();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// reload the data into the list from the database
	public void reloadData() {
		list = impl.getAll();
		listView.setAdapter(new ReminderItemAdapter(getActivity(), list,
				mHandler));
	}

	/**
	 * Cancel alarm using item's id
	 * 
	 * @author Yifei Gao
	 * @param item_id
	 *            Id of the item you want to cancel the alarm
	 */
	public void cancelAlarm(int item_id) {
		Intent it = new Intent(getActivity(), ReminderReceiver.class);

		it.setAction("com.spendwell.action.ReminderAlarm");

		it.putExtra("item_id", item_id);
		// Construct the pendingIntent for cancel
		PendingIntent pit = PendingIntent.getBroadcast(getActivity(), item_id,
				it, 0);
		AlarmManager am = (AlarmManager) getActivity().getSystemService(
				Context.ALARM_SERVICE);
		am.cancel(pit);
	}

}
