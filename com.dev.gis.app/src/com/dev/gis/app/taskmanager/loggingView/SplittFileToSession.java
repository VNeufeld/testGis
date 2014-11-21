package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SplittFileToSession {
	
	private final File   logFile;
	private final String outputDir;
	private final String sessionId;
	private final String bookingId;
	private final long   maxFileSize;
	private final Calendar loggingFromDate;
	private final Calendar loggingToDate;
	

	private static Logger logger = Logger.getLogger(SplittFileToSession.class);

	public SplittFileToSession(String fileName, String dirName, String outputDir,
			String sessionId, String bookingId, String maxFileSizeText, Calendar loggingFromDate,
			Calendar loggingToDate) {
		
		if ( StringUtils.isNotEmpty(fileName)) {
			logFile = new File(fileName);
			logger.info("  size = "+FileUtils.sizeOf(logFile));
		}
		else if ( StringUtils.isNotEmpty(dirName))
			logFile = new File(dirName);
		else
			logFile = null;
		
		this.outputDir = outputDir;
		this.sessionId = sessionId;
		this.bookingId = bookingId;
		this.loggingFromDate = loggingFromDate;
		this.loggingToDate = loggingToDate;
		if ( StringUtils.isNotEmpty(maxFileSizeText))
			maxFileSize = Integer.valueOf(maxFileSizeText);
		else
			maxFileSize = 100;
		
			
	}

	public void splitToSession() {

		int count = 0;
		int size = 0;
		int counter = 1;
		long maxSize = maxFileSize * 1000000;
		LineIterator li;
		List<String> rows = new ArrayList<String>();
		try {
			li = FileUtils.lineIterator(logFile);
			while ( li.hasNext()) {
				String s  = li.nextLine();
				if (s.contains(sessionId) )
				{
					rows.add(s);
					count++;
					size = size + s.length();
				}
				
			}
			
			writeFile(logFile,sessionId,rows);
			
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		logger.info(" lines  = "+ count );
		
	}

	private void writeFile(File original, String sessionId, List<String> rows) throws IOException {
		String ext = FilenameUtils.getExtension(original.getAbsolutePath());

		String name = FilenameUtils.getName(original.getAbsolutePath())+ "_" + sessionId + ".plog";
		
		String newFile = FilenameUtils.concat(outputDir,name);

		File of = new File(newFile);
		
		FileUtils.writeLines(of, rows);
		
	}

}
