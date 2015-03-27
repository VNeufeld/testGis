package com.dev.gis.connector.sunny;

public class Person extends BasicProtocol {

	private Salutation salutation;

	private Gender gender;

	private String firstName = "";

	private String name = "";
	
	private String comment = "";
	
	
	private DayAndHour  birthday;
	
	
	public String getFirstName() {
		return firstName;
	}

	public Gender getGender() {
		return gender;
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

	public void setGender(Gender gender) {
		this.gender = gender;
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
}