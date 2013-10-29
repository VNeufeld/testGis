package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="bankAccount")
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
