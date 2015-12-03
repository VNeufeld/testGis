package com.dev.gis.app.taskmanager.loggingView.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;


public class LoggingSearchFilesSelectionListener implements SelectionListener {
	
	private final static Logger logger = Logger.getLogger(LoggingSearchFilesSelectionListener.class);
	

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public LoggingSearchFilesSelectionListener() {
		super();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		logger.info(" FindFilesService started ");
		
		FileNameEntryModel.getInstance().getEntries().clear();
		
		LogFileTableUpdater.updateFileList(null);					
		
		FindFilesService ff = new FindFilesService(LoggingModelProvider.INSTANCE.logDirName,LoggingModelProvider.INSTANCE.filePattern,
				null, null);  //searchCriteriaComposite.loggingFromDate,searchCriteriaComposite.loggingToDate);
		
		executor.submit(ff);
		
		logger.info(" FindFilesService finished ");


		
	}


}
