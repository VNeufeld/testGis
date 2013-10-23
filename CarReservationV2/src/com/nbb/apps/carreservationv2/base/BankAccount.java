package com.nbb.apps.carreservationv2.base;



public class BankAccount extends BasicProtocol {

	private String account = "";

	private String bankCode = "";

	private String bankName = "";

	private String ownerName = "";

	
	
	public String getAccount() {
		return account;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
