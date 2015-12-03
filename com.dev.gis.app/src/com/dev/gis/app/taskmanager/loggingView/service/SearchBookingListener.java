package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.dev.gis.app.task.model.FileNameEntryModel;


public class SearchBookingListener implements SelectionListener {
	
	private final static Logger logger = Logger.getLogger(SearchBookingListener.class);
	

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public SearchBookingListener() {
		super();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		logger.info(" FindBookingService started ");
		
		FileNameEntryModel.getInstance().getEntries().clear();
		
		FindBookingService findBooking = new FindBookingService();
		
		executor.submit(findBooking);
		
		logger.info(" FindBookingService finished ");
		
	}


}
