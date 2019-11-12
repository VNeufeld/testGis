package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;


public class SearchThreadListener implements SelectionListener {
	
	private final static Logger logger = Logger.getLogger(SearchThreadListener.class);
	

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public SearchThreadListener() {
		super();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		logger.info(" SessionService started ");
		
		SearchThreadService sessionService = new SearchThreadService();
		
		executor.submit(sessionService);
		
		logger.info(" SessionService finished ");


		
	}


}
