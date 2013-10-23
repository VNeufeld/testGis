package com.nbb.apps.carreservationv2.base;

public class Customer extends BasicProtocol {

	private String customerNo = "";

	private String externalCustomerNo;

	private Person person;

	private Address address;

	private PhoneNumber phone;

	private PhoneNumber officePhone;

	private PhoneNumber mobile;

	private PhoneNumber fax;

	private String eMail;

	private boolean sendDocToEndCustomer;

	private String partnerProgram;
	
	
	
	public Customer() {
	}

	public Customer(String customerNo, String externalCustomerNo, Person person, Address address, PhoneNumber phone,
			PhoneNumber officePhone, PhoneNumber mobile, PhoneNumber fax, String eMail, boolean sendDocToEndCustomer,
			String partnerProgram) {
		this.customerNo = customerNo;
		this.externalCustomerNo = externalCustomerNo;
		this.person = person;
		this.phone = phone;
		this.officePhone = officePhone;
		this.mobile = mobile;
		this.fax = fax;
		this.sendDocToEndCustomer = sendDocToEndCustomer;
		this.partnerProgram = partnerProgram;
	}

	
	
	public Address getAddress() {
		return address;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public String getEMail() {
		return eMail;
	}

	public String getExternalCustomerNo() {
		return externalCustomerNo;
	}

	public PhoneNumber getFax() {
		return fax;
	}

	public PhoneNumber getMobile() {
		return mobile;
	}

	public PhoneNumber getOfficePhone() {
		return officePhone;
	}

	public String getPartnerProgram() {
		return partnerProgram;
	}

	public Person getPerson() {
		return person;
	}

	public PhoneNumber getPhone() {
		return phone;
	}

	public boolean getSendDocToEndCustomer() {
		return sendDocToEndCustomer;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public void setExternalCustomerNo(String externalCustomerNo) {
		this.externalCustomerNo = externalCustomerNo;
	}

	public void setFax(PhoneNumber fax) {
		this.fax = fax;
	}

	public void setMobile(PhoneNumber mobile) {
		this.mobile = mobile;
	}

	public void setOfficePhone(PhoneNumber officePhone) {
		this.officePhone = officePhone;
	}
	
	public void setPartnerProgram(String partnerProgram) {
		this.partnerProgram = partnerProgram;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void setPhone(PhoneNumber phone) {
		this.phone = phone;
	}
	
	public void setSendDocToEndCustomer(boolean sendDocToEndCustomer) {
		this.sendDocToEndCustomer = sendDocToEndCustomer;
	}
}
