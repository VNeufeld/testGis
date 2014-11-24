package com.dev.gis.app.taskmanager.loggingView;

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

public class SplitterFileService implements Callable<List<LogEntry>> {
	
	private final static Logger logger = Logger.getLogger(SplitterFileService.class);
	
	
	private final File  logFile;
	private final String sessionId;

	public SplitterFileService(File file, String sessionId) {
		this.logFile = file;
		this.sessionId = sessionId;
		
	}

	@Override
	public List<LogEntry> call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session " + sessionId+ " in file "+logFile.getAbsolutePath());
		logger.info("File size = " + logFile.length());
		
		List<LogEntry> r = readLogEntries(logFile, this.sessionId);

		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return r;
		
	}

	private List<LogEntry> readLogEntries(File file, String sessionId2) {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		long count = 0;
		long size = 0;
		boolean sessionFound = false;

		LineIterator li;
		try {
			li = FileUtils.lineIterator(file);
			LogEntry entry = null;

			while (li.hasNext()) {
				String s = li.nextLine();
				if (++count % 10000 == 0) {
					logger.info("check "+count + " lines");
					LoggingAppView.updateView("check "+count + " lines");					
				}
				
				Date time = getTimeString(s);
				if (time != null) {
					sessionFound = s.contains(sessionId);

					if (sessionFound ) {
						if ( entry != null)
							entries.add(entry);
						
						entry = new LogEntry(time,s);
					}
					
				} else if (entry != null && sessionFound) {
					entry.addString(s);
				}

			}
			if ( entry != null)
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

		if ( s != null && s.length() > 25) {
			String time = s.substring(0, 25);
			try {
				Date d = null;
				if ( time.charAt(2) == '.' && time.charAt(5) == '.') {
					d = stf.parse(time);
					return d;
				}
			} catch (ParseException e) {
				logger.info(e.getMessage()+ " "+s);
			}
		}
		return null;

	}
	
}
