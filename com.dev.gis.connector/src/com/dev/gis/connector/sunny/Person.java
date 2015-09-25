package com.dev.gis.connector.sunny;

public class Person extends BasicProtocol {

	private String salutation;

	private String  gender;

	private String firstName = "";

	private String name = "";
	
	private String comment = "";
	
	
	private DayAndHour  birthday;
	
	
	public String getFirstName() {
		return firstName;
	}


	public String getName() {
		return name;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setName(String name) {
		this.name = name;
	}


	public DayAndHour getBirthday() {
		return birthday;
	}

	public void setBirthday(DayAndHour birthday) {
		this.birthday = birthday;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getSalutation() {
		return salutation;
	}


	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
}