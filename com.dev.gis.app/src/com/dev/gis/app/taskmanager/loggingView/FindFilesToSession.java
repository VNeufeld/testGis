package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.util.Calendar;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.FileNameEntryModel;

public class FindFilesToSession implements Callable<String> {

	private static Logger logger = Logger.getLogger(FindFilesToSession.class);

	private final String logDir;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	

	public FindFilesToSession(String dirName,	
			Calendar loggingFromDate,
			Calendar loggingToDate) {

		if (StringUtils.isNotEmpty(dirName)) {
			logDir = dirName;
		} else {
			logDir = null;
		}

		this.loggingFromDate = loggingFromDate;
		this.loggingToDate = loggingToDate;

	}


	private void findFileToSession() {

			File[] files = LoggingUtils.getAllFilesRecurisive(logDir, loggingFromDate,
					loggingToDate);
			
			FileNameEntryModel.getInstance().create(files);
			
	}


	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start FindFiles ");
		findFileToSession();
		LoggingAppView.updateFileModel();					

		logger.info("end FindFiles  " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}


}
