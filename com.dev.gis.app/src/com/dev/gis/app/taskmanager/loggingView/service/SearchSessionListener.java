package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;


public class SearchSessionListener implements SelectionListener {
	
	private final static Logger logger = Logger.getLogger(SearchSessionListener.class);
	

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public SearchSessionListener() {
		super();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		logger.info(" SessionService started ");
		
		WriteSessionService sessionService = new WriteSessionService(LoggingModelProvider.INSTANCE.searchSession);
		
		executor.submit(sessionService);
		
		logger.info(" SessionService finished ");


		
	}


}
