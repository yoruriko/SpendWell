package com.spendwell.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Used for creating the SQLite database when user first excuse the application
 * 
 * @author Yifei Gao
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "spendwell.bd";
	private static final int DB_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(DB_NAME, "is created");
		db.execSQL("create table reminder(id integer NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ "type,payType,targetName,amount,description,date,isAlarm,isPaid)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// used for database update and version control
	}

}
