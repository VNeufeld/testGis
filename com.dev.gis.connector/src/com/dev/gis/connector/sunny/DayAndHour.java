package com.dev.gis.connector.sunny;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;





public class DayAndHour extends BasicProtocol {

	private final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");

	private final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	
	
	private String date = "";

	private String time = "";
	
	
	public DayAndHour() {
		// empty
	}


	public DayAndHour(String date, String time) {
		setDate(date);
		setTime(time);
	}

	public DayAndHour(String date) {
		setDate(date);
	}
	
	public String getDate() {
		return date;
	}


	public String getTime() {
		return time;
	}
	

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "DayAndHour [date=" + date + ", time=" + time + "]";
	}
}