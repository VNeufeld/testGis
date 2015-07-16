package com.dev.gis.connector.sunny;

public class Person extends BasicProtocol {

	private Salutation salutation;

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

	public Salutation getSalutation() {
		return salutation;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
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
}