package com.dev.gis.connector.sunny;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;





public class DayAndHour extends BasicProtocol {

	private final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");

	private final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");

	
	
	private String date = "";

	private String time = "";

	private MutableDateTime dateAndTime = null;

	private LocalTime timeOnly = null;

	
	
	public DayAndHour() {
		// empty
	}

	public DayAndHour(DateTime initialTime) {
		time = timeFormatter.print(initialTime);
		date = dateFormatter.print(initialTime);
		dateAndTime = initialTime.toMutableDateTime();
		timeOnly = initialTime.toLocalTime();
	}

	public DayAndHour(String date, String time) {
		setDate(date);
		setTime(time);
	}

	public DayAndHour(String date) {
		setDate(date);
	}
	
	private void addTime() {
		dateAndTime.addHours(timeOnly.getHourOfDay());
		dateAndTime.addMinutes(timeOnly.getMinuteOfHour());
	}

	public String getDate() {
		return date;
	}

	public DateTime getDateTime() {
		return dateAndTime.toDateTime();
	}

	public String getTime() {
		return time;
	}
	

	public void setDate(String date) {
		this.date = date;
		//dateAndTime = MutableDateTime..parse(date, dateFormatter);
		if(timeOnly != null) {
			addTime();
		}
	}

	public void setTime(String time) {
		this.time = time;
		timeOnly = timeFormatter.parseDateTime(time).toLocalTime();
		if(dateAndTime != null) {
			addTime();
		}
	}
	public static int getDiffDays(DayAndHour day1, DayAndHour day2) {
		
		LocalDate ld1 = new LocalDate(day1.getDateTime());

		LocalDate ld2 = new LocalDate(day2.getDateTime());
		
		Days days = Days.daysBetween(ld1, ld2);
		
		return days.getDays();
	}

	@Override
	public String toString() {
		return "DayAndHour [date=" + date + ", time=" + time + "]";
	}
}