package com.dev.gis.connector.sunny;

import java.util.Calendar;

import org.joda.time.DateTime;
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
	
	public Calendar toCalendar() {
		Calendar cal = Calendar.getInstance();
		if ( this.date != null && this.date.length() == 10) {
			DateTime s = dateFormatter.parseDateTime(date);
			cal.set(s.getYear(), s.getMonthOfYear()-1, s.getDayOfMonth());
		}

		if ( this.time != null && this.time.length() == 5) {
			DateTime s = timeFormatter.parseDateTime(time);
			cal.set(Calendar.HOUR, s.getHourOfDay());
			cal.set(Calendar.MINUTE, s.getMinuteOfHour());
			cal.set(Calendar.SECOND,0);
		}
		return cal;
	}

	@Override
	public String toString() {
		return "DayAndHour [date=" + date + ", time=" + time + "]";
	}
}