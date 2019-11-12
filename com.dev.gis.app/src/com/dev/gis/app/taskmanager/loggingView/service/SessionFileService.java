package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.io.IOException;
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
import com.dev.gis.app.taskmanager.loggingView.StopButtonListener;

public class SessionFileService implements Callable<List<LogEntry>> {
	
	
	
	final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss,SSS";
	final String ADAC_DATE_TIME_SHORT_FORMAT = "dd.MM.yyyy HH:mm:ss";
	final String DATE_TIME_FORMAT_SUNNY = "yyyy-MM-dd HH:mm:ss,SSS";
	final String WEBJOI_TIME_FORMAT = "HH:mm:ss,SSS";
	

	private final static Logger logger = Logger
			.getLogger(SessionFileService.class);

	private Deque<String> savedLines = new ArrayDeque<String>();

	protected final File logFile;
	protected final String sessionId;
	protected final int index;
	protected final int logEntryView;

	public SessionFileService(File file, String sessionId, int index, int logEntryView) {
		this.logFile = file;
		this.sessionId = sessionId;
		this.index = index;
		this.logEntryView = logEntryView;

	}

	@Override
	public List<LogEntry> call() throws Exception {
		if(StopButtonListener.terminate) {
			return new ArrayList<LogEntry>();
		}
		if ( !LogEntryModel.getInstance().isProcessRunning())
		{
			return  new ArrayList<LogEntry>();
		}
		
		long start = System.currentTimeMillis();
		logger.info("start search session " + sessionId + " in file "
				+ logFile.getAbsolutePath() + ". File size =  "+ logFile.length());
		
		LogFileTableUpdater.updateFileStatus(logFile,"running");					
		
		ProgressBarElement.updateFileName("search in " + logFile.getName() + ".  File size "+logFile.length());
		
		List<LogEntry> logEntries = readLogEntries(logFile, this.sessionId);
		
		LogEntryTableUpdater.showResult(logEntries, logEntryView);
		
		LogFileTableUpdater.updateFileStatus(logFile,"completed");					
		

		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return logEntries;

	}

	private List<LogEntry> readLogEntries(File file, String sessionId2) throws IOException {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		long count = 0;

		long fileSize = file.length();

		long readedSize = 0;

		LineIterator li = null;
		try {
			li = FileUtils.lineIterator(file);
			LogEntry entry = null;
			boolean firstSessionIdNotFound = true;
			boolean searchNextLinewithTime = false;
			while (li.hasNext()) {
				String s = li.nextLine();

				readedSize = readedSize + s.length();
				if (++count % 1000 == 0) {
					ProgressBarElement.updateProgressBar(readedSize,fileSize);
					if ( !LogEntryModel.getInstance().isProcessRunning())
						break;
				}

				boolean sessionFlagFound = StringUtils.containsIgnoreCase(s,sessionId);
				
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
		} finally {
			if ( li != null)
				li.close();
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

	protected LogEntry tryFoundPreviousEntryWithDate() {
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

	protected Date createDateTime_2000_01_01() {
		Calendar cal = Calendar.getInstance();
		cal.set(2000, 0, 1);
		return cal.getTime();
	}

	protected void saveLineInQueue(String s) {
		if (savedLines.size() > 100)
			savedLines.removeLast();
		savedLines.addFirst(s);
	}

	protected Date getTimeString(String s) {
		if (s == null ) 
			return null;
		
		Date date1 = checkWebJoiTimeFormat(s);
		if ( date1 != null)
			return date1; 

		if ( s.length() < 25) 
			return null;
		
		Date date = checkAdacFormat(s);
		if ( date == null)
			date = checkSunnyFormat(s);

		return date;

	}
	private Date checkWebJoiTimeFormat(String s) {
		try {
			if ( s.length() < 12)
				return null;
			String time = s.substring(0, 12);
			if (time.charAt(2) == ':' && time.charAt(5) == ':') {
				SimpleDateFormat stf = new SimpleDateFormat(WEBJOI_TIME_FORMAT);
				Date d = stf.parse(time);
				return d;
			}
		}
		catch(Exception err) {
			
		}
		return null;
	}

	protected Date checkAdacFormat(String s) {
		Date date = checkAdacLongFormat(s);
		if ( date != null)
			return date;
		return checkAdacShortFormat(s);
	}
	

	protected Date checkAdacLongFormat(String s) {
		try {
			String time = s.substring(0, 25);
			if (time.charAt(2) == '.' && time.charAt(5) == '.') {
				SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);
				return stf.parse(time);
			}
		} catch (ParseException e) {
			logger.debug(e.getMessage() + " " + s);
		}
		return null;
	}
	protected Date checkAdacShortFormat(String s) {
		try {
			String time = s.substring(0, 19);
			if (time.charAt(2) == '.' && time.charAt(5) == '.') {
				SimpleDateFormat stf = new SimpleDateFormat(ADAC_DATE_TIME_SHORT_FORMAT);
				return stf.parse(time);
			}
		} catch (ParseException e) {
			logger.debug(e.getMessage() + " " + s);
		}
		return null;
	}
	

	protected Date checkSunnyFormat(String s) {
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
