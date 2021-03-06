package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;
import com.dev.gis.app.taskmanager.loggingView.ProgressBarElement;
import com.dev.gis.app.taskmanager.loggingView.StopButtonListener;

class SearchBookingFileService implements Callable<List<LogEntry>> {

	private final static Logger logger = Logger.getLogger(SearchBookingFileService.class);

	private final File logFile;
	private final String bookingId;
	private final int index;

	public SearchBookingFileService(File file, String bookingId, int index) {
		this.logFile = file;
		this.bookingId = bookingId;
		this.index = index;

	}

	@Override
	public List<LogEntry> call() throws Exception {
		if (StopButtonListener.terminate) {
			return new ArrayList<LogEntry>();
		}
		if (!LogEntryModel.getInstance().isProcessRunning()) {
			return new ArrayList<LogEntry>();
		}

		long start = System.currentTimeMillis();
		logger.info("start search booking " + bookingId + " in file " + logFile.getAbsolutePath());
		logger.info("File size = " + logFile.length());

		LogFileTableUpdater.updateFileStatus(logFile, "running");

		ProgressBarElement.updateFileName("search in " + logFile.getName() + ".  File size " + logFile.length());

		List<LogEntry> r = readLogEntries(logFile, this.bookingId);

		LogFileTableUpdater.updateFileStatus(logFile, "completed");

		logger.info("end search booking in  " + (System.currentTimeMillis() - start) + " ms.");
		return r;

	}

	private List<LogEntry> readLogEntries(File file, String bookingId) {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		long count = 0;

		long fileSize = file.length();

		long readedSize = 0;

		boolean sessionFound = false;

		LineIterator li;
		try {
			li = FileUtils.lineIterator(file);
			LogEntry entry = null;

			while (li.hasNext()) {

				String s = li.nextLine();
				readedSize = readedSize + s.length();
				if (++count % 10000 == 0) {
					ProgressBarElement.updateProgressBar(readedSize, fileSize);
					if (!LogEntryModel.getInstance().isProcessRunning())
						break;

				}

				Date time = getTimeString(s);
				if (time != null) {
					sessionFound = s.contains("<Booking_id>") && s.contains("TDVAWorker") && s.contains(bookingId);

					if (sessionFound) {
						if (entry != null)
							entries.add(entry);

						entry = new LogEntry(time, s, index);

						LogEntryModel.getInstance().addTempEntry(entry);
					}

				} else if (entry != null && sessionFound) {
					entry.addString(s);
				}

			}
			if (entry != null)
				entries.add(entry);

		} catch (IOException e2) {
			e2.printStackTrace();
		}

		logger.info(" lines  = " + count);

		return entries;
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
