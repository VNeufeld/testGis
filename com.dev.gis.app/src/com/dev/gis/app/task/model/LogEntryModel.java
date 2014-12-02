package com.dev.gis.app.task.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dev.gis.app.taskmanager.loggingView.LogViewUpdater;
import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;

public class LogEntryModel  extends ModelObject {
	private static LogEntryModel  instance = null;
	
	private List<LogEntry> entries = new ArrayList<LogEntry>();
	private List<LogEntry> tempEntries = new ArrayList<LogEntry>();

	private List<LogEntry> loggingEntries = new ArrayList<LogEntry>();
	
	private boolean processRunning = false;

	public static LogEntryModel getInstance() {
		if (instance == null) {
			instance = new LogEntryModel();
		}
		return instance;
	}
	
	public void clear() {
		entries.clear();
		tempEntries.clear();
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

	private String getDamandedObject(String text) {
		return StringUtils.substringBetween(text, "<DemandedObject>", "</DemandedObject>");
	}
	
	private String getPrice(String text) {
		return StringUtils.substringBetween(text, "<TotalPrice>", "</TotalPrice>");
	}

	public void addTempEntry(LogEntry entry) {
		String s = getBooking(entry.getEntry().get(0));
		if ( StringUtils.isNotBlank(s)) {
			entry.setBookingId(s);
			entry.setSessionId(getSession(entry.getEntry().get(0)));
			entry.setPrice(getPrice(entry.getEntry().get(0)));
			tempEntries.add(entry);
			
			LogViewUpdater.updateTempView();
		}
			
	}

	public List<LogEntry> getTempEntries() {
		return tempEntries;
	}

	public void setProcessRunning() {
		processRunning = true;		
	}

	public boolean isProcessRunning() {
		return processRunning;
	}

	public void stopProcess() {
		processRunning =false;;
	}

	public List<LogEntry> getLoggingEntries() {
		return loggingEntries;
	}

	public void updateLoggingEntries(List<LogEntry> entries) {
		
		for (LogEntry entry : entries ) {
			String demandedObject = "-";
			for ( String str : entry.getEntry()) {
				String demObj = getDamandedObject(str);
				if (StringUtils.isNotBlank(demObj)) {
					demandedObject = demObj;
					break;
				}
			}
			entry.setDemandedObject(demandedObject);
		}
		
		loggingEntries.addAll(entries);
		Collections.sort(loggingEntries);
		
	}

}
