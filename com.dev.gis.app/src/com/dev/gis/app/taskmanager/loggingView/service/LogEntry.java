package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogEntry implements Comparable<LogEntry> {
	
	private Date date;
	private String sessionId;
	private String bookingId;
	private String price;
	private String demandedObject;
	
	private List<String> entry = new ArrayList<String>();
	
	public LogEntry(Date time) {
		this.date = time;
	}
	public LogEntry(Date time, String s) {
		this.date = time;
		addString(s);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<String> getEntry() {
		return entry;
	}
	public void setEntry(List<String> entry) {
		this.entry = entry;
	}
	public void addString(String s) {
		entry.add(s);
	}
	@Override
	public int compareTo(LogEntry arg) {
		// TODO Auto-generated method stub
		return date.compareTo(arg.date);
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDemandedObject() {
		return demandedObject;
	}
	public void setDemandedObject(String demandedObject) {
		this.demandedObject = demandedObject;
	}
	
	
	

}
