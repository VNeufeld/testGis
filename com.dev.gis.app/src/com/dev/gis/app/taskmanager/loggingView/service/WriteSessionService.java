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
import com.dev.gis.app.taskmanager.loggingView.LogEntryTableUpdater;
import com.dev.gis.app.taskmanager.loggingView.LogViewUpdater;

public class WriteSessionService implements Callable<String> {

	private static Logger logger = Logger.getLogger(WriteSessionService.class);

	private  String sessionId;
	private int maxThreads;
	
	private boolean canceled = false;


	private List<LogEntry> entries = new ArrayList<LogEntry>();

	
	public WriteSessionService(	String sessionId) {
		this.sessionId = sessionId;
		maxThreads = LoggingModelProvider.INSTANCE.getThreadcounts();

	}
	
	private void searchSession() {
		try {
			entries.clear();
			
			LogEntryModel.getInstance().getLoggingEntries().clear();

			LogEntryTableUpdater.showResult(null,1);
			
			List<FileNameEntry> fileEntries = FileNameEntryModel.getInstance().getSelectedFiles();

			ExecutorService executor = Executors.newFixedThreadPool((int)maxThreads);
			
			List<SessionFileService> splitterServicers = new ArrayList<SessionFileService>();
			
			for (FileNameEntry entry : fileEntries) {
				File file = entry.getFile();
				logger.info("check file :"+file.getAbsolutePath());

				SessionFileService ss = new SessionFileService(file,this.sessionId, entry.getNumber(), 1);
				splitterServicers.add(ss);
			}
				
			List<Future< List<LogEntry>>> results = executor
					.invokeAll(splitterServicers);
			
			for (Future<List<LogEntry>> fc : results) {
				List<LogEntry> le = fc.get();
				entries.addAll(le);
			}
		
		
			logger.info(" sort " + entries.size()+ " entries");
			Collections.sort(entries);
			
			LogViewUpdater.updateView("exit");					
			
			
		} catch (InterruptedException | ExecutionException ioe) {
			logger.info(ioe.getMessage(), ioe);
		}
	}


	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session " + sessionId);
		searchSession();
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
