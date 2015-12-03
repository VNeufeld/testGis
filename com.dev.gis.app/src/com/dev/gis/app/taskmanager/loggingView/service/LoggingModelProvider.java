package com.dev.gis.app.taskmanager.loggingView.service;

public enum LoggingModelProvider {
	INSTANCE;
	
	public int threadCounts = 1;

	public String filePattern;
	
	public String searchSession;

	public String searchBooking;
	
	public String logDirName;

	public String outputDirName;
	
	
	public int getThreadcounts() {
		if ( threadCounts <= 0)
			return 1;
		if ( threadCounts > 10)
			return 10;
		return threadCounts;
		
	}

}
