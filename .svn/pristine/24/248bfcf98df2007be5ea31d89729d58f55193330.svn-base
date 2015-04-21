package com.spendwell.entity;

import java.util.Date;
/**
 * 
 * @author Yifei Gao
 *
 */
public class ReminderItem {

	public static final int OWE = 0;
	public static final int LEND = 1;

	public static final int MEAL = 0;
	public static final int DRINK = 1;
	public static final int UTILITIES_BILL = 2;
	public static final int ACCOMMODATION = 3;
	public static final int TRAVEL = 4;
	public static final int SOCIAL = 5;
	public static final int TRANSPORT = 6;
	public static final int ELSE = 7;

	private int id, type, payType;
	private double amount;
	private String targetName, description;
	private boolean isAlarm, isPaid;
	private String date;

	public ReminderItem() {
		super();
	}

	public ReminderItem(int type, int payType, int amount, String targetName,
			String description, boolean isAlarm, boolean isPaid, String date) {
		super();
		this.type = type;
		this.payType = payType;
		this.amount = amount;
		this.targetName = targetName;
		this.description = description;
		this.isAlarm = isAlarm;
		this.isPaid = isPaid;
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAlarm() {
		return isAlarm;
	}

	public void setAlarm(boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getIdInTable() {
		return id;
	}

	public void setIdInTable(int id) {
		this.id = id;
	}

	public Date getAlarmTime() {
		int y = Integer.parseInt(date.substring(0, 4));
		int m = Integer.parseInt(date.substring(6, 7));
		int d = Integer.parseInt(date.substring(8, 10));

		return new Date(y - 1900, m - 1, d);
	}

}
