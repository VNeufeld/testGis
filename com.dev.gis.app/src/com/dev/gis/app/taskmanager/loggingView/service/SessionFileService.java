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
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;

class SessionFileService implements Callable<List<LogEntry>> {

	private final static Logger logger = Logger
			.getLogger(SessionFileService.class);

	private Deque<String> savedLines = new ArrayDeque<String>();

	private final File logFile;
	private final String sessionId;

	public SessionFileService(File file, String sessionId) {
		this.logFile = file;
		this.sessionId = sessionId;

	}

	@Override
	public List<LogEntry> call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session " + sessionId + " in file "
				+ logFile.getAbsolutePath());
		logger.info("File size = " + logFile.length());
		
		FileNameEntryModel.getInstance().setStatus(logFile, "running");
		LoggingAppView.updateFileName("search in " + logFile.getName() + ".  File size "+logFile.length());
		

		List<LogEntry> r = readLogEntries(logFile, this.sessionId);
		
		FileNameEntryModel.getInstance().setStatus(logFile, "completed");
		LoggingAppView.updateFileModel();					

		logger.info("end splitt in  " + (System.currentTimeMillis() - start)
				+ " ms.");
		return r;

	}

	private List<LogEntry> readLogEntries(File file, String sessionId2) {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		long count = 0;

		long fileSize = file.length();

		long readedSize = 0;

		LineIterator li;
		try {
			li = FileUtils.lineIterator(file);
			LogEntry entry = null;
			boolean firstSessionIdNotFound = true;
			boolean searchNextLinewithTime = false;
			while (li.hasNext()) {
				String s = li.nextLine();

				readedSize = readedSize + s.length();
				if (++count % 1000 == 0) {
					//logger.info("check " + count + " lines");
					LoggingAppView.updateProgressBar(readedSize,fileSize);
					
				}

				boolean sessionFlagFound = s.contains(sessionId);
				Date time = getTimeString(s);

				if (firstSessionIdNotFound) {
					saveLineInQueue(s);
					if (sessionFlagFound) {
						if (time == null) {
							entry = tryFoundPreviousEntryWithDate();
						} else {
							entry = new LogEntry(time, s);
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
							if ( matchSession(entry))
								entries.add(entry);

						if (sessionFlagFound) { // begin new entry
							entry = new LogEntry(time, s);
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
					logEntry = new LogEntry(time, s);
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
		final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss,SSS";

		SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);

		if (s != null && s.length() > 25) {
			String time = s.substring(0, 25);
			try {
				Date d = null;
				if (time.charAt(2) == '.' && time.charAt(5) == '.') {
					d = stf.parse(time);
					return d;
				}
			} catch (ParseException e) {
				logger.info(e.getMessage() + " " + s);
			}
		}
		return null;

	}

}
