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
import com.dev.gis.app.taskmanager.loggingView.StopButtonListener;

public class SearchThreadService implements Callable<String> {

	private static Logger logger = Logger.getLogger(SearchThreadService.class);

	private  String searchText;
	private  String sessionText;
	private int maxThreads;

	private List<LogEntry> entries = new ArrayList<LogEntry>();

	
	public SearchThreadService() {
		this.searchText = LoggingModelProvider.INSTANCE.searchThread;
		this.sessionText = LoggingModelProvider.INSTANCE.searchSession;
		maxThreads = LoggingModelProvider.INSTANCE.getThreadcounts();

	}
	
	private void searchSession() {
		try {
			entries.clear();
			
			List<FileNameEntry> fileEntries = FileNameEntryModel.getInstance().getSelectedFiles();

			ExecutorService executor = Executors.newFixedThreadPool((int)maxThreads);
			
			List<SessionThreadFileService> splitterServicers = new ArrayList<SessionThreadFileService>();
			
			for (FileNameEntry entry : fileEntries) {
				File file = entry.getFile();
				logger.info("check file :"+file.getAbsolutePath());

				SessionThreadFileService ss = new SessionThreadFileService(file,this.searchText, sessionText, entry.getNumber(),2);
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
			
			LogEntryTableUpdater.showResultTable(entries, 2);
			
			
		} catch (InterruptedException | ExecutionException ioe) {
			logger.info(ioe.getMessage(), ioe);
		}
	}


	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session " + searchText);
		searchSession();
		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		StopButtonListener.terminate = false;
		return null;
	}

}
