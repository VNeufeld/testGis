package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogEntryTableUpdater;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;
import com.dev.gis.app.taskmanager.loggingView.ProgressBarElement;
import com.dev.gis.app.taskmanager.loggingView.StopButtonListener;

class SessionThreadFileService extends SessionFileService {

	final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss,SSS";
	final String DATE_TIME_FORMAT_SUNNY = "yyyy-MM-dd HH:mm:ss,SSS";

	private final static Logger logger = Logger.getLogger(SessionThreadFileService.class);

	private final String threadText;

	public SessionThreadFileService(File file, String threadText, String sessionId, int index, int logEntryView) {
		super(file, sessionId, index, logEntryView);
		this.threadText = threadText;

	}

	@Override
	public List<LogEntry> call() throws Exception {
		if (StopButtonListener.terminate) {
			return new ArrayList<LogEntry>();
		}
		if ( !LogEntryModel.getInstance().isProcessRunning())
		{
			return  new ArrayList<LogEntry>();
		}
		
		long start = System.currentTimeMillis();
		logger.info("start search thread session " + sessionId + "/" + threadText + " in file "
				+ logFile.getAbsolutePath() + ". File size =  " + logFile.length());

		LogFileTableUpdater.updateFileStatus(logFile,"running");					
		
		ProgressBarElement.updateFileName("search in " + logFile.getName() + ".  File size " + logFile.length());

		List<LogEntry> logEntries = readLogEntries(logFile);
		
		LogEntryTableUpdater.showResult(logEntries, logEntryView);
		
		LogFileTableUpdater.updateFileStatus(logFile,"completed");					
		

		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return logEntries;

	}

	private List<LogEntry> readLogEntries(File file) throws IOException {
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
					ProgressBarElement.updateProgressBar(readedSize, fileSize);
					if (!LogEntryModel.getInstance().isProcessRunning())
						break;

				}
				boolean sessionFlagFound = StringUtils.containsIgnoreCase(s, sessionId)
						&& StringUtils.containsIgnoreCase(s, threadText);
				Date time = getTimeString(s);

				if (firstSessionIdNotFound) {
					saveLineInQueue(s);
					if (sessionFlagFound) {
						if (time == null) {
							entry = tryFoundPreviousEntryWithDate();
						} else {
							entry = new LogEntry(time, s, index);
						}
						firstSessionIdNotFound = false;
						searchNextLinewithTime = true;
					}
				} else {
					if (searchNextLinewithTime && time == null) {
						if (entry != null)
							entry.addString(s);
					} else if (time != null) {

						if (entry != null) // completed prev. entry
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
				if (matchSession(entry))
					entries.add(entry);

		} catch (Exception e2) {
			logger.error(e2.getMessage(), e2);
		} finally {
			if (li != null)
				li.close();
		}

		logger.info(" lines  = " + count);

		return entries;
	}

	private boolean matchSession(LogEntry entry) {
		for (String s : entry.getEntry()) {
			if (s.contains("sessionId=\"")) {
				String ssid = StringUtils.substringBetween(s, "sessionId=\"", "\"");
				if (ssid != null && ssid.equals(sessionId))
					return true;
				return false;
			}

		}
		return true;
	}

}
