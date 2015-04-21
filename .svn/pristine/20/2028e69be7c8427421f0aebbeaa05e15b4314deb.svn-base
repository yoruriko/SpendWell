package com.spendwell.service;

import com.example.budgetwell.R;
import com.spendwell.activity.ReminderDetailActivity;
import com.spendwell.entity.ReminderItem;
import com.spendwell.utils.ReminderSQLServiceImpl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Notification.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Audio;

/**
 * Receiver that handle the reminder alarms, shows notification to reminder user
 * 
 * @author Yifei Gao
 * 
 */
public class ReminderReceiver extends BroadcastReceiver {

	private ReminderSQLServiceImpl impl;
	private Context mContext;
	private Builder builder = null;
	private SharedService ss;
	private Notification notify = null;
	private NotificationManager notificationManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		impl = new ReminderSQLServiceImpl(context);
		mContext = context;
		ss = new SharedService(context);

		int item_id = intent.getExtras().getInt("item_id");
		// obtain item's id from the intent

		notificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);

		showNotification(item_id);

	}

	/**
	 * Set the notification contents of the reminder item using the given id
	 * 
	 * @author Yifei Gao
	 * @param item_id
	 *            the id of the alarm
	 */
	public void showNotification(int item_id) {
		ReminderItem item = impl.getItemById(item_id);
		impl.changeAlarm(item_id, false);

		Intent it = new Intent(mContext, ReminderDetailActivity.class);

		it.putExtra("item_id", item_id);
		// Construct the pending intent using the Remidner item
		PendingIntent pit = PendingIntent.getActivity(mContext, 0, it,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		// Construct the notification using the reminder item details
		builder = new Notification.Builder(mContext);
		builder.setAutoCancel(true);

		builder.setAutoCancel(true);

		builder.setTicker("You have new Reminder");
		builder.setSmallIcon(R.drawable.ic_launcher);

		if (item.getPayType() == ReminderItem.OWE) {
			builder.setContentTitle("You need to pay " + item.getTargetName()
					+ " today");
			builder.setContentText("Need to pay" + item.getTargetName() + " £ "
					+ item.getAmount() + " today(Click to view more details)");
		} else if (item.getPayType() == ReminderItem.LEND) {
			builder.setContentTitle(item.getTargetName()
					+ " need to pay you today");
			builder.setContentText(item.getTargetName() + " need to pay you £ "
					+ item.getAmount() + " today(Click to view more details)");
		}

		builder.setContentIntent(pit);

		if (ss.getVibrate()) {
			// if vibrate flag is on, show the vibrate effect
			builder.setVibrate(new long[] { 500, 200, 500, 200 });
		}

		if (ss.getSound()) {
			// if sound flag is on, show the sound effect
			builder.setSound(Uri.withAppendedPath(
					Audio.Media.INTERNAL_CONTENT_URI, "6"));
		}

		notify = builder.build();
		notificationManager.notify(item_id, notify);
	}
}
