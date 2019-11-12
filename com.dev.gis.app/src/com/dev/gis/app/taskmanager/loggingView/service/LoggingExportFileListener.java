package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;


public class LoggingExportFileListener implements SelectionListener {
	
	private final static Logger logger = Logger.getLogger(LoggingExportFileListener.class);
	

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public LoggingExportFileListener() {
		super();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		logger.info(" ExportLogEntriesService started ");
		
		ExportLogEntriesService ff = new ExportLogEntriesService();
		
		executor.submit(ff);
		
		logger.info(" ExportLogEntriesService finished ");


		
	}


}
