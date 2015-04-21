package com.spendwell.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
/**
 * 
 * @author Yifei Gao
 *
 */
public class OutGoingTransaction {
	private int id, fromAccount;
	private String toName, theDate, toSortCode, toIban, description;
	private double amount;

	public OutGoingTransaction() {
		super();
	}

	public OutGoingTransaction(int id, int fromAccount, String toName,
			String theDate, String toSortCode, String toIban,
			String description, double amount) {
		super();
		this.id = id;
		this.fromAccount = fromAccount;
		this.toName = toName;
		this.theDate = theDate;
		this.toSortCode = toSortCode;
		this.toIban = toIban;
		this.description = description;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(int fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getTheDate() {
		return theDate;
	}

	public void setTheDate(String theDate) {
		this.theDate = theDate;
	}

	public String getFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		// Construct date using the date format and the Date String
		SimpleDateFormat output = new SimpleDateFormat("MMM dd, yyyy",
				Locale.ENGLISH);
		try {
			Date date = sdf.parse(theDate.substring(0, 22));
			String format = output.format(date);
			return format;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return theDate.substring(0, 11);
	}

	public String getLongDate() {
		SimpleDateFormat input = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS");
		input.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// format the date into String
		try {
			Date date = input.parse(theDate.substring(0, 22));
			String format = output.format(date);
			return format;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return theDate.substring(0, 11);
	}

	public String getToSortCode() {
		return toSortCode;
	}

	public String getFormatSortCode() {
		return toSortCode.substring(0, 2) + "-" + toSortCode.substring(2, 4)
				+ "-" + toSortCode.substring(4, 6);
	}

	public void setToSortCode(String toSortCode) {
		this.toSortCode = toSortCode;
	}

	public String getToIban() {
		return toIban;
	}

	public void setToIban(String toIban) {
		this.toIban = toIban;
	}

	public String getBarIban() {
		String format_iban = toIban.substring(0, 4) + "-"
				+ toIban.substring(4, 8) + "-" + toIban.substring(8, 12) + "-"
				+ toIban.substring(12, 16) + "-" + toIban.substring(16, 20)
				+ "-" + toIban.substring(20, 22);
		return format_iban;
	}

	public String getFormatIban() {
		return toIban.substring(18, 22);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}