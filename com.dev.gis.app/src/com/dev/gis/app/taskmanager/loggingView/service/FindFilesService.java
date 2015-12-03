package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.dev.gis.app.taskmanager.loggingView.LogFileTableUpdater;

public class FindFilesService implements Callable<String> {

	private static Logger logger = Logger.getLogger(FindFilesService.class);

	private final String logDir;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	private final String filePattern;

	public FindFilesService() {

		logDir = LoggingModelProvider.INSTANCE.logDirName;
		filePattern  = LoggingModelProvider.INSTANCE.filePattern;
		if ( LoggingModelProvider.INSTANCE.useDates) {
			loggingFromDate = LoggingModelProvider.INSTANCE.loggingFromDate;
			loggingToDate = LoggingModelProvider.INSTANCE.loggingToDate;
		}
		else {
			loggingFromDate = null;
			loggingToDate = null;
		}
	}

	private void findFileToSession() {

		File[] files = LoggingUtils.getAllFilesRecurisive(logDir, filePattern,
				loggingFromDate, loggingToDate);

		LogFileTableUpdater.updateFileList(files);

	}

	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start FindFiles ");
		findFileToSession();

		logger.info("end FindFiles  " + (System.currentTimeMillis() - start)
				+ " ms.");
		return null;
	}

}
