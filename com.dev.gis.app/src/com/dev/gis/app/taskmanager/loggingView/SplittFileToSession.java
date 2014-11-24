package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SplittFileToSession implements Callable<String> {

	private static Logger logger = Logger.getLogger(SplittFileToSession.class);

	private final File logFile;
	private final String outputDir;
	private final String logDir;
	private final String sessionId;
	private final String bookingId;
	private final long maxFileSize;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	

	private List<LogEntry> entries = new ArrayList<LogEntry>();

	public SplittFileToSession(String fileName, String dirName,
			String outputDir, String sessionId, String bookingId,
			String maxFileSizeText, Calendar loggingFromDate,
			Calendar loggingToDate) {

		if (StringUtils.isNotEmpty(fileName)) {
			logFile = new File(fileName);
			logger.info("  size = " + FileUtils.sizeOf(logFile));
			logDir = null;
		} else if (StringUtils.isNotEmpty(dirName)) {
			logDir = dirName;
			logFile = null;
		} else {
			logDir = null;
			logFile = null;
		}

		this.outputDir = outputDir;
		this.sessionId = sessionId;
		this.bookingId = bookingId;
		this.loggingFromDate = loggingFromDate;
		this.loggingToDate = loggingToDate;
		if (StringUtils.isNotEmpty(maxFileSizeText))
			maxFileSize = Integer.valueOf(maxFileSizeText);
		else
			maxFileSize = 100;

	}


	private void splitToSession() {
		try {

			File[] files = LoggingUtils.getAllFilesRecurisive(logDir, loggingFromDate,
					loggingToDate);
			entries.clear();

			ExecutorService executor = Executors.newFixedThreadPool((int)maxFileSize);
			
			List<SplitterFileService> splitterServicers = new ArrayList<SplitterFileService>();
			
			for (File file : files) {
				logger.info("check file :"+file.getAbsolutePath());

				SplitterFileService ss = new SplitterFileService(file,this.sessionId);
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

	}

	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitter session " + sessionId);
		splitToSession();
		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}

}
