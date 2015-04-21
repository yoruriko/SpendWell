package com.spendwell.entity;
/**
 * 
 * @author Yifei Gao
 *
 */
public class BankAccount {
	private int id;
	private double amount, overdraft;
	private String accountName, accountNumber, sortCode, iban, owner;

	public BankAccount() {
		super();
	}

	public BankAccount(int id, String accountName, String accountNumber,
			String sortCode, String iban, long amount, long overdraft,
			String owner) {
		super();
		this.id = id;
		this.amount = amount;
		this.overdraft = overdraft;
		this.accountName = accountName;
		this.accountNumber = accountNumber;
		this.sortCode = sortCode;
		this.iban = iban;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSortCode() {
		String format_sortCode = sortCode.substring(0, 2) + "-"
				+ sortCode.substring(2, 4) + "-" + sortCode.substring(4, 6);
		return format_sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getIban() {
		String format_iban = iban.substring(0, 4) + "-" + iban.substring(4, 8)
				+ "-" + iban.substring(8, 12) + "-" + iban.substring(12, 16)
				+ "-" + iban.substring(16, 20) + "-" + iban.substring(20, 22);
		return format_iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public double getTotalBalance() {
		return amount + overdraft;
	}

}
