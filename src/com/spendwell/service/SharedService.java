package com.spendwell.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spendwell.entity.Account;
import com.spendwell.entity.BankAccount;
import com.spendwell.entity.NotificaitonItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Media that uses to store objects and details for different activity to use
 * 
 * @author Yifei Gao
 * 
 */
public class SharedService {
	private Context context;
	private String sp_name = "SpendWellPref";
	private SharedPreferences sp;

	public SharedService(Context context) {
		this.context = context;
		sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
	}

	// save the userName,longin token and save name option into the preference
	public void save(String user_name, String user_token, boolean isNameSave) {
		Editor editor = sp.edit();
		editor.putString("user_name", user_name);
		editor.putString("user_token", user_token);
		editor.putBoolean("is_name_saved", isNameSave);
		editor.commit();
	}

	// save the login token into the preference
	public void saveRequestToken(String req_token) {
		Editor editor = sp.edit();
		editor.putString("user_token", req_token);
		editor.commit();
	}

	// get login token from preference, reutrn "" if not exists
	public String getRequestToken() {
		return sp.getString("user_token", "");
	}

	// save Account details into the preference
	public void saveAccount(Account account) {
		Editor editor = sp.edit();
		Gson gson = new Gson();
		String json = gson.toJson(account);
		editor.putString("Account", json);
		editor.commit();
	}

	// save List of Account details into the preference
	public void saveAccountList(List<BankAccount> list) {
		Editor editor = sp.edit();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		editor.putString("AccountList", json);
		editor.commit();
	}

	// obtain all account details from the preference
	public List<BankAccount> readAccountList() {
		String json = sp.getString("AccountList", "");
		if (json.equals("")) {
			return null;
		}
		Gson gson = new Gson();
		List<BankAccount> list = new ArrayList<BankAccount>();
		list = gson.fromJson(json, new TypeToken<List<BankAccount>>() {
		}.getType());

		return list;
	}

	public String readUserName() {

		return sp.getString("user_name", "");
	}

	public String readUserToken() {
		return sp.getString("user_token", "");
	}

	public Account readAccount() {
		Gson gson = new Gson();
		String json = sp.getString("Account", "");
		if (json.equals("")) {
			return null;
		}
		Account account = gson.fromJson(json, Account.class);
		return account;
	}

	public boolean getIsNameSave() {
		return sp.getBoolean("is_name_saved", false);
	}

	// save list of notificationItem into the preference
	public void setNotificationList(List<NotificaitonItem> list) {
		Editor editor = sp.edit();
		Gson gson = new Gson();
		String json = gson.toJson(list);
		editor.putString("NotificationList", json);
		editor.commit();
	}

	// return all notification items stored in the preference
	public List<NotificaitonItem> getAllNotifications() {
		String data = sp.getString("NotificationList", "");
		if (data.equals("")) {
			return null;
		} else {
			Gson gson = new Gson();
			List<NotificaitonItem> list = gson.fromJson(data,
					new TypeToken<List<NotificaitonItem>>() {
					}.getType());
			return list;
		}
	}

	// save the last update time for notification into preference
	public void setLastUpdateTime(Long time) {
		Editor editor = sp.edit();
		editor.putLong("lastUpdateTime", time);
		editor.commit();
	}

	// obtain the last update time from preference
	public long getLastUpdateTime() {
		long lastUpdateTime = sp.getLong("lastUpdateTime", 0);

		return lastUpdateTime;
	}

	// set the vibrate flag in preference
	public void setVibrate(boolean isVibrate) {
		Editor editor = sp.edit();
		editor.putBoolean("isVibrate", isVibrate);
		editor.commit();
	}

	// get the vibrate flag from preference
	public boolean getVibrate() {
		boolean isVibrate = sp.getBoolean("isVibrate", true);
		return isVibrate;
	}

	// set the sound flag in preference
	public void setSound(boolean isSound) {
		Editor editor = sp.edit();
		editor.putBoolean("isSound", isSound);
		editor.commit();
	}

	// get the vibrate flag from preference
	public boolean getSound() {
		boolean isSound = sp.getBoolean("isSound", true);
		return isSound;
	}

	// set the alarm time into the preference
	public void setReminderTime(long reminderTime) {
		Editor editor = sp.edit();

		editor.putLong("reminderTime", reminderTime);

		editor.commit();
	}

	// return the alarm time from the preference
	public long getReminderTime() {

		long def = 1000 * 60 * 60 * 9;

		return sp.getLong("reminderTime", def);
	}
}
