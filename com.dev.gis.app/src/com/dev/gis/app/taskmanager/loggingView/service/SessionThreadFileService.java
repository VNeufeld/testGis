package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogEntryTableUpdater;
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;
import com.dev.gis.app.taskmanager.loggingView.ProgressBarElement;

class SessionThreadFileService extends SessionFileService {
	
	final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss,SSS";
	final String DATE_TIME_FORMAT_SUNNY = "yyyy-MM-dd HH:mm:ss,SSS";
	

	private final static Logger logger = Logger
			.getLogger(SessionThreadFileService.class);

	private Deque<String> savedLines = new ArrayDeque<String>();

	private final String threadText;

	public SessionThreadFileService(File file, String threadText, String sessionId, int index, int logEntryView) {
		super(file, sessionId, index, logEntryView);
		this.threadText = threadText;

	}

	@Override
	public List<LogEntry> call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start search thread session " + sessionId + "/"+ threadText+" in file "
				+ logFile.getAbsolutePath() + ". File size =  "+ logFile.length());
		
		//LogFileTableUpdater.updateFileStatus(logFile,"running");					
		
		ProgressBarElement.updateFileName("search in " + logFile.getName() + ".  File size "+logFile.length());
		
		List<LogEntry> logEntries = readLogEntries(logFile);
		
		//LogEntryTableUpdater.showResult(logEntries, logEntryView);
		
		//LogFileTableUpdater.updateFileStatus(logFile,"completed");					
		

		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return logEntries;

	}

	private List<LogEntry> readLogEntries(File file) {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		long count = 0;

		LineIterator li;
		try {
			li = FileUtils.lineIterator(file);
			LogEntry entry = null;
			boolean firstSessionIdNotFound = true;
			boolean searchNextLinewithTime = false;
			while (li.hasNext()) {
				String s = li.nextLine();

				boolean sessionFlagFound = s.contains(sessionId) && s.contains(threadText);
				Date time = getTimeString(s);

				if (firstSessionIdNotFound) {
					saveLineInQueue(s);
					if (sessionFlagFound) {
						if (time == null) {
							entry = tryFoundPreviousEntryWithDate();
						} else {
							entry = new LogEntry(time, s,index);
						}
						firstSessionIdNotFound = false;
						searchNextLinewithTime = true;
					}
				} else {
					if (searchNextLinewithTime && time == null) {
						if ( entry != null)
							entry.addString(s);
					}
					else if (time != null) {

						if (entry != null) // completed prev. entry
							//if ( matchSession(entry))
								entries.add(entry);
						

						if (sessionFlagFound) { // begin new entry
							entry = new LogEntry(time, s, index);
							searchNextLinewithTime = true;
						} else {
							entry = null;
							searchNextLinewithTime = false;
							firstSessionIdNotFound = true;
						}

					}

				}

			}
			if (entry != null)
				if ( matchSession(entry))
					entries.add(entry);

		} catch (Exception e2) {
			logger.error(e2.getMessage(), e2);
		}

		logger.info(" lines  = " + count);

		return entries;
	}

	private boolean matchSession(LogEntry entry) {
		for ( String s: entry.getEntry()) {
			if ( s.contains("sessionId=\"")){
				String ssid = StringUtils.substringBetween(s, "sessionId=\"", "\"");
				if ( ssid != null && ssid.equals(sessionId)) 
					return true;
				return false;
			}
			
		}
		return true;
	}

	private LogEntry tryFoundPreviousEntryWithDate() {
		List<String> lines = new ArrayList<String>();
		Date time = null;
		logger.info("tryFoundPreviousEntryWithDate");
		if ( !savedLines.isEmpty()) {
			String s = savedLines.pollFirst();
			if (s != null) {
				logger.info("saved s "+s);
				lines.add(s);
				time = getTimeString(s);
				while (time == null && s != null) {
					if ( savedLines.isEmpty())
						s = null;
					else {
						s = savedLines.pollFirst();
						logger.info("saved s "+s);
						if (s != null) {
							lines.add(s);
							time = getTimeString(s);
						}
					}
				}
			}
		}

		savedLines.clear();
		if (time == null) {
			time = createDateTime_2000_01_01();
		}

		LogEntry logEntry = null;
		if (lines.size() > 0) {
			int i = lines.size() - 1;
			while (i >= 0) {
				String s = lines.get(i);
				if (logEntry == null) {
					logEntry = new LogEntry(time, s, index);
					logger.info("create logEntry "+s);
				}
				else {
					logEntry.addString(s);
					logger.info("add logEntry "+s);
				}
					
				i--;
			}
		}

		return logEntry;
	}

	private Date createDateTime_2000_01_01() {
		Calendar cal = Calendar.getInstance();
		cal.set(2000, 0, 1);
		return cal.getTime();
	}

	private void saveLineInQueue(String s) {
		if (savedLines.size() > 5)
			savedLines.removeLast();
		savedLines.addFirst(s);
	}

	private Date getTimeString(String s) {
		if (s == null || s.length() <= 25) 
			return null;
		
		Date date = checkAdacFormat(s);
		if ( date == null)
			date = checkSunnyFormat(s);

		return date;

	}

	private Date checkAdacFormat(String s) {
		try {
			String time = s.substring(0, 25);
			if (time.charAt(2) == '.' && time.charAt(5) == '.') {
				SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);
				return stf.parse(time);
			}
		} catch (ParseException e) {
			logger.info(e.getMessage() + " " + s);
		}
		return null;
	}

	private Date checkSunnyFormat(String s) {
		try {
			String time = s.substring(0, 25);
			if (time.charAt(4) == '-' && time.charAt(7) == '-') {
				SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT_SUNNY);
				return stf.parse(time);
			}
		} catch (ParseException e) {
			logger.info(e.getMessage() + " " + s);
		}
		return null;
	}
	
}
