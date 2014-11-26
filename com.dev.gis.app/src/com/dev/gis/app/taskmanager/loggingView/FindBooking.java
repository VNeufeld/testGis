package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.task.model.LogEntryModel;

public class FindBooking implements Callable<String> {

	private static Logger logger = Logger.getLogger(FindBooking.class);

	private final String outputDir;
	private final String logDir;
	private final String bookingId;
	private final int maxThreads;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	
	private boolean canceled = false;


	private List<LogEntry> entries = new ArrayList<LogEntry>();

	public FindBooking(String dirName,
			String outputDir, String bookingId,
			String maxThreadsText, Calendar loggingFromDate,
			Calendar loggingToDate) {

		if (StringUtils.isNotEmpty(dirName)) {
			logDir = dirName;
		} else {
			logDir = null;
		}

		this.outputDir = outputDir;
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

			File[] files = LoggingUtils.getAllFilesRecurisive(logDir, loggingFromDate,
					loggingToDate);
			
			if (files.length == 0 ) {
				LoggingAppView.updateView("error: no files found for this search criteria.");					
				return;
			}
			
			FileNameEntryModel.getInstance().create(files);
			LoggingAppView.updateFileModel();					
			
			entries.clear();

			ExecutorService executor = Executors.newFixedThreadPool((int)maxThreads);
			
			List<FindBookingService> services = new ArrayList<FindBookingService>();
			
			for (File file : files) {
				logger.info("check file :"+file.getAbsolutePath());

				FindBookingService ss = new FindBookingService(file,this.bookingId);
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
			
			LoggingAppView.updateView("session: "+possibleSessionId);					
			
			
		} catch (InterruptedException | ExecutionException ioe) {
			logger.info(ioe.getMessage(), ioe);
		}
	}

	private void writeEntries() throws IOException {

		String name = FilenameUtils.getName("log_hsgw_" + bookingId + ".plog");

		String newFile = FilenameUtils.concat(outputDir, name);

		File of = new File(newFile);

		logger.info("write sessions to :"+newFile);

		List<String> list = new ArrayList<String>();
		for (LogEntry entry : entries) {
			list.addAll(entry.getEntry());
		}
		FileUtils.writeLines(of, list);

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
