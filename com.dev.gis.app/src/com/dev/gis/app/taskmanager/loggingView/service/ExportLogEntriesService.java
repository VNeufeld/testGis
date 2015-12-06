package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.ProgressBarElement;

public class ExportLogEntriesService implements Callable<String> {

	private static Logger logger = Logger.getLogger(ExportLogEntriesService.class);

	public ExportLogEntriesService() {

	}


	public void writeEntries() throws IOException {

		
		List<LogEntry> entries = LogEntryModel.getInstance().getLoggingEntries();
		

		String newFile = LoggingModelProvider.INSTANCE.outputFileName;

		File of = new File(newFile);

		logger.info("write sessions to :"+newFile);

		List<String> list = new ArrayList<String>();
		for (LogEntry entry : entries) {
			list.addAll(entry.getEntry());
		}
		FileUtils.writeLines(of, list);
		
		ProgressBarElement.updateFileName("write result in " + newFile);

	}

	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session ");
		writeEntries();
		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}

}
