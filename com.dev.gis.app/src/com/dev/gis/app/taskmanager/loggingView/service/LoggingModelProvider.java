package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.Calendar;

public enum LoggingModelProvider {
	INSTANCE;
	
	public int threadCounts = 1;

	public String filePattern;
	
	public String searchSession;

	public String searchBooking;
	
	public String logDirName;

	public String outputDirName;
	
	public Calendar loggingFromDate ;
	public Calendar loggingToDate;

	
	public boolean useDates;
	
	public int getThreadcounts() {
		if ( threadCounts <= 0)
			return 1;
		if ( threadCounts > 10)
			return 10;
		return threadCounts;
		
	}

}
