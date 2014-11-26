package com.dev.gis.app.task.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dev.gis.app.taskmanager.loggingView.LogEntry;

public class LogEntryModel  extends ModelObject {
	private static LogEntryModel  instance = null;
	
	private List<LogEntry> entries = new ArrayList<LogEntry>();

	public static LogEntryModel getInstance() {
		if (instance == null) {
			instance = new LogEntryModel();
		}
		return instance;
	}
	
	public void clear() {
		entries.clear();
	}

	public List<LogEntry> getEntries() {
		return entries;
	}

	public void interprete() {
		for (LogEntry entry : entries ) {
			if (entry.getEntry().size() > 0 ) {
				entry.setBookingId(getBooking(entry.getEntry().get(0)));
				entry.setSessionId(getSession(entry.getEntry().get(0)));
				entry.setPrice(getPrice(entry.getEntry().get(0)));
			}
		}
		
	}

	private String getSession(String text) {
		return StringUtils.substringBetween(text, "][", "]");
	}

	private String getBooking(String text) {
		String bookingid = "";
		bookingid = StringUtils.substringBetween(text, "<Booking_id>", "</Booking_id>");
		return bookingid;
	}
	private String getPrice(String text) {
		return StringUtils.substringBetween(text, "<TotalPrice>", "</TotalPrice>");
	}

}
