package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.task.model.FileNameEntryModel;
import com.dev.gis.app.taskmanager.loggingView.LoggingAppView;

public class WriteSessionService implements Callable<String> {

	private static Logger logger = Logger.getLogger(WriteSessionService.class);

	private final String outputDir;
	private final String logDir;
	private final String sessionId;
	private final int maxThreads;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	
	private boolean canceled = false;


	private List<LogEntry> entries = new ArrayList<LogEntry>();

	public WriteSessionService(String dirName,
			String outputDir, String sessionId,
			String maxThreadsText, Calendar loggingFromDate,
			Calendar loggingToDate) {

		if (StringUtils.isNotEmpty(dirName)) {
			logDir = dirName;
		} else {
			logDir = null;
		}

		this.outputDir = outputDir;
		this.sessionId = sessionId;
		this.loggingFromDate = loggingFromDate;
		this.loggingToDate = loggingToDate;
		if (StringUtils.isBlank(maxThreadsText))
			maxThreads = 1;
		else {
			int temp = Integer.valueOf(maxThreadsText);
			if ( temp == 0)
				maxThreads = 1;
			else if ( temp > 10)
				maxThreads = 10;
			else
				maxThreads = temp;
				
		}

	}


	private void splitToSession() {
		try {

			File[] files = LoggingUtils.getAllFilesRecurisive(logDir, loggingFromDate,
					loggingToDate);
			entries.clear();

			FileNameEntryModel.getInstance().create(files);
			LoggingAppView.updateFileModel();					

			ExecutorService executor = Executors.newFixedThreadPool((int)maxThreads);
			
			List<SessionFileService> splitterServicers = new ArrayList<SessionFileService>();
			
			for (File file : files) {
				logger.info("check file :"+file.getAbsolutePath());

				SessionFileService ss = new SessionFileService(file,this.sessionId);
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

			logger.info(" save " + entries.size()+ " entries");
			writeEntries();
			logger.info(" save " + entries.size()+ " entries successfull");
			
			LoggingAppView.updateView("exit");					
			
			
		} catch (IOException | InterruptedException | ExecutionException ioe) {
			logger.info(ioe.getMessage(), ioe);
		}
	}

	private void writeEntries() throws IOException {

		String name = FilenameUtils.getName("log_hsgw_" + sessionId + ".plog");

		String newFile = FilenameUtils.concat(outputDir, name);

		File of = new File(newFile);

		logger.info("write sessions to :"+newFile);

		List<String> list = new ArrayList<String>();
		for (LogEntry entry : entries) {
			list.addAll(entry.getEntry());
		}
		FileUtils.writeLines(of, list);
		
		LoggingAppView.updateFileName("write result in " + newFile);


	}

	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session " + sessionId);
		splitToSession();
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
