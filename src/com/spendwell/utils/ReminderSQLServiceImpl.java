package com.spendwell.utils;

import java.util.ArrayList;
import java.util.List;

import com.spendwell.entity.ReminderItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Implementation of the SQL query
 * 
 * @author Yifei Gao
 * 
 */
public class ReminderSQLServiceImpl {
	private static final String TAG = ReminderSQLServiceImpl.class
			.getSimpleName();
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;
	private String tableName = "reminder";

	public ReminderSQLServiceImpl(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	/**
	 * Insert ReminderItem into the table
	 * 
	 * @author Yifei Gao
	 * @param item
	 *            Reminder item that created by user's input
	 * @return status of the insertion
	 */
	public synchronized int insertReminder(ReminderItem item) {
		db = dbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("type", item.getType());
		cv.put("payType", item.getPayType());
		cv.put("targetName", item.getTargetName());
		cv.put("amount", item.getAmount());
		cv.put("description", item.getDescription());
		cv.put("date", item.getDate());

		if (item.isAlarm()) {
			cv.put("isAlarm", 1);
		} else {
			cv.put("isAlarm", 0);
		}

		if (item.isPaid()) {
			cv.put("isPaid", 1);
		} else {
			cv.put("isPaid", 0);
		}

		long result = db.insertWithOnConflict(tableName, null, cv,
				SQLiteDatabase.CONFLICT_IGNORE);
		db.close();
		if (result < 0) {
			Log.i(TAG, "Insert ReminderItem Fail");
			return -1;
		} else {
			Log.i(TAG, "Insert ReminderItem Sucess");
			return (int) result;
		}

	}

	/**
	 * Delete given item from the table
	 * 
	 * @author Yifei Gao
	 * @param item
	 *            target item to delete
	 * @return status of the deletion
	 */
	public synchronized int deleteByItem(ReminderItem item) {
		db = dbHelper.getWritableDatabase();
		try {
			db.execSQL(
					"delete from "
							+ tableName
							+ " where type=? and payType=? and targetName=? and amount=? and description=? and date=?",
					new Object[] { item.getType(), item.getPayType(),
							item.getTargetName(), item.getAmount(),
							item.getDescription(), item.getDate() });
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("StudentSQLHelper", "Delete item " + item + "fail");
			return -1;

		}
	}

	/**
	 * Get all reminder items from the table
	 * 
	 * @author Yifei Gao
	 * @return List of ReminderItem in the table
	 */
	public synchronized List<ReminderItem> getAll() {
		db = dbHelper.getWritableDatabase();
		List<ReminderItem> list = new ArrayList<ReminderItem>();
		ReminderItem item = null;
		Cursor cursor = null;

		cursor = db.rawQuery("select * from " + tableName + " order by id",
				null);

		while (cursor.moveToNext()) {
			item = new ReminderItem();
			item.setType(cursor.getInt(cursor.getColumnIndex("type")));
			item.setPayType(cursor.getInt(cursor.getColumnIndex("payType")));
			item.setTargetName(cursor.getString(cursor
					.getColumnIndex("targetName")));
			item.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
			item.setDescription(cursor.getString(cursor
					.getColumnIndex("description")));
			item.setDate(cursor.getString(cursor.getColumnIndex("date")));
			item.setAlarm(cursor.getInt(cursor.getColumnIndex("isAlarm")) == 1);
			item.setPaid(cursor.getInt(cursor.getColumnIndex("isPaid")) == 1);
			item.setIdInTable(cursor.getInt(cursor.getColumnIndex("id")));

			list.add(item);
			Log.i(TAG, "Read ReminderItem " + item.getIdInTable()
					+ " sucessfully");
		}
		cursor.close();
		return list;

	}

	/**
	 * Get ReminderItem with given id
	 * 
	 * @author Yifei Gao
	 * @param id
	 *            id of Reminder item
	 * @return null if id not exists, reminder item if exists
	 */
	public synchronized ReminderItem getItemById(int id) {
		db = dbHelper.getWritableDatabase();
		ReminderItem item = null;
		Cursor cursor = null;

		cursor = db.rawQuery("select * from " + tableName + " where id=" + id,
				null);

		while (cursor.moveToNext()) {
			item = new ReminderItem();

			item = new ReminderItem();
			item.setType(cursor.getInt(cursor.getColumnIndex("type")));
			item.setPayType(cursor.getInt(cursor.getColumnIndex("payType")));
			item.setTargetName(cursor.getString(cursor
					.getColumnIndex("targetName")));
			item.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
			item.setDescription(cursor.getString(cursor
					.getColumnIndex("description")));
			item.setDate(cursor.getString(cursor.getColumnIndex("date")));
			item.setAlarm(cursor.getInt(cursor.getColumnIndex("isAlarm")) == 1);
			item.setPaid(cursor.getInt(cursor.getColumnIndex("isPaid")) == 1);
			item.setIdInTable(cursor.getInt(cursor.getColumnIndex("id")));

			return item;

		}

		return null;
	}

	/**
	 * Get all unPaid ReminderItem and calculate their amount's sum
	 * 
	 * @author Yifei Gao
	 * @return the total amount unpaid
	 */
	public synchronized double getUnpaid() {
		db = dbHelper.getWritableDatabase();
		double unpaid = 0;
		Cursor cursor = db.rawQuery("select * from " + tableName
				+ " where isPaid=0", null);
		while (cursor.moveToNext()) {
			unpaid += cursor.getDouble(cursor.getColumnIndex("amount"));
		}
		cursor.close();
		return unpaid;
	}

	/**
	 * Change the isPaid value of the ReminderItem in the table
	 * 
	 * @author Yifei Gao
	 * @param id
	 *            id of the item you want to change
	 */
	public synchronized void changePay(int id) {
		db = dbHelper.getWritableDatabase();
		db.execSQL("update " + tableName + " set isPaid=1 where id=" + id);

	}

	/**
	 * Change the isAlarm value of the ReminderItem in the table
	 * 
	 * @author Yifei Gao
	 * @param id
	 *            id of the item you want to change
	 * @param isAlarm
	 *            boolean you want to set
	 */
	public synchronized void changeAlarm(int id, boolean isAlarm) {
		db = dbHelper.getWritableDatabase();
		int state = 0;
		if (isAlarm)
			state = 1;
		db.execSQL("update " + tableName + " set isAlarm=" + state
				+ " where id=" + id);
	}
}
