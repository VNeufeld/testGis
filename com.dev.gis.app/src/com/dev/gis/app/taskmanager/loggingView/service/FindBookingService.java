package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogBookingEntryView;
import com.dev.gis.app.taskmanager.loggingView.LogViewUpdater;

public class FindBookingService implements Callable<String> {

	private static Logger logger = Logger.getLogger(FindBookingService.class);

	private final String bookingId;
	private int maxThreads;

	private boolean canceled = false;


	private List<LogEntry> entries = new ArrayList<LogEntry>();

	public FindBookingService(){

		this.bookingId = LoggingModelProvider.INSTANCE.searchBooking;
		maxThreads = LoggingModelProvider.INSTANCE.getThreadcounts();

	}


	private void searchBooking() {
		try {
			
			entries.clear();

			ExecutorService executor = Executors.newFixedThreadPool((int)maxThreads);
			
			List<SearchBookingFileService> services = new ArrayList<SearchBookingFileService>();
			
			LogEntryModel.getInstance().setProcessRunning();
			
			//List<File> files = 	FileNameEntryModel.getInstance().getFiles();	
			List<FileNameEntry> fileEntries = FileNameEntryModel.getInstance().getEntries();
			
			
			for (FileNameEntry fileNameEntry : fileEntries) {
				File file = fileNameEntry.getFile();
				logger.info("check file :"+file.getAbsolutePath());

				SearchBookingFileService ss = new SearchBookingFileService(file,this.bookingId, fileNameEntry.getNumber());
				services.add(ss);
			}
				
			List<Future< List<LogEntry>>> results = executor.invokeAll(services);
			
			for (Future<List<LogEntry>> fc : results) {
				List<LogEntry> le = fc.get();
				entries.addAll(le);
			}
		
		
			Collections.sort(entries);

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
			LogBookingEntryView.updateView();					
			
			
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
