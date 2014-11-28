package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogViewUpdater;
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;

public class FindBookingService implements Callable<String> {

	private static Logger logger = Logger.getLogger(FindBookingService.class);

	private final String logDir;
	private final String bookingId;
	private final int maxThreads;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	private final String   filePattern;

	private boolean canceled = false;


	private List<LogEntry> entries = new ArrayList<LogEntry>();

	public FindBookingService(String dirName,
			String bookingId,
			String maxThreadsText,
			String   filePattern,
			Calendar loggingFromDate,
			Calendar loggingToDate) {

		if (StringUtils.isNotEmpty(dirName)) {
			logDir = dirName;
		} else {
			logDir = null;
		}

		this.filePattern = filePattern;
		this.bookingId = bookingId;
		this.loggingFromDate = loggingFromDate;
		this.loggingToDate = loggingToDate;
		if (StringUtils.isBlank(maxThreadsText))
			maxThreads = 1;
		else {
			int temp = Integer.valueOf(maxThreadsText);
			if ( temp == 0)
				maxThreads = 1;
			else if ( temp > 10)
				maxThreads = 10;
			else
				maxThreads = temp;
				
		}

	}


	private void searchBooking() {
		try {

			File[] files = LoggingUtils.getAllFilesRecurisive(logDir, filePattern, loggingFromDate,
					loggingToDate);
			
			if (files.length == 0 ) {
				LogViewUpdater.updateView("error: no files found for this search criteria.");					
				return;
			}
			
			FileNameEntryModel.getInstance().create(files);
			LogFileTableUpdater.showResult();					
			
			
			entries.clear();

			ExecutorService executor = Executors.newFixedThreadPool((int)maxThreads);
			
			List<SearchBookingFileService> services = new ArrayList<SearchBookingFileService>();
			
			for (File file : files) {
				logger.info("check file :"+file.getAbsolutePath());

				SearchBookingFileService ss = new SearchBookingFileService(file,this.bookingId);
				services.add(ss);
			}
				
			List<Future< List<LogEntry>>> results = executor.invokeAll(services);
			
			for (Future<List<LogEntry>> fc : results) {
				List<LogEntry> le = fc.get();
				entries.addAll(le);
			}
		
		
			Collections.sort(entries);

			//writeEntries();

			logger.info(" save " + entries.size()+ " entries successfull");
			
			String possibleSessionId = null;
			if (entries.size() > 0 && entries.get(0).getEntry().size() > 0) {
				possibleSessionId =  entries.get(0).getEntry().get(0);
				LogEntryModel.getInstance().clear();
				LogEntryModel.getInstance().getEntries().addAll(entries);
				LogEntryModel.getInstance().interprete();
				
			}
			else
				possibleSessionId = "booking not found";
			
			LogViewUpdater.updateView("session: "+possibleSessionId);					
			
			
		} catch (InterruptedException | ExecutionException ioe) {
			logger.info(ioe.getMessage(), ioe);
		}
	}

	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start searching booking " + bookingId);
		searchBooking();
		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}


	public boolean isCanceled() {
		return canceled;
	}


	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

}
