package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class SplitFile implements Callable<String> {
	private static Logger logger = Logger.getLogger(SplitFile.class);

	private final File logFile;
	private final String outputDir;
	private long maxFileSize;
	
	private boolean canceled = false;

	public SplitFile(String fileName, String outputDir,
			String maxFileSizeText) {

		if (StringUtils.isNotEmpty(fileName)) {
			logFile = new File(fileName);
			logger.info("  size = " + FileUtils.sizeOf(logFile));
		} else {
			logFile = null;
		}

		this.outputDir = outputDir;
		
		
		if (StringUtils.isNotEmpty(maxFileSizeText))
			maxFileSize = Integer.valueOf(maxFileSizeText);
		else
			maxFileSize = 100;
		
		maxFileSize = maxFileSize * 1000000;
		
		canceled = false;

	}


	private void writeFile(File original, int counter,List<String> rows) 
			throws IOException {
//		String ext = FilenameUtils.getExtension(logFile.getAbsolutePath());
//		String path = FilenameUtils.getFullPath(logFile.getAbsolutePath());
		String name = FilenameUtils.getName(logFile.getAbsolutePath())+ "_" + counter + ".plog";
		
		String newFile = FilenameUtils.concat(outputDir,name);
		logger.info("write file "+newFile + " rows = "+ rows.size());

		LoggingSplittFileView.updateView("write file "+newFile + " rows = "+ rows.size());					

		File of = new File(newFile);
		
		FileUtils.writeLines(of, rows);
		
		logger.info("exit write file "+newFile + " ");
		
	}
	
	public int splitFile(File file) {
		
		int count = 0;
		int size = 0;
		int allsize = 0;
		int counter = 1;
		LineIterator li;
		long fileSize = file.length();
		List<String> rows = new ArrayList<String>(50000);
		try {
			li = FileUtils.lineIterator(file);
			while ( li.hasNext()) {
				if ( canceled )
					break;
				String s  = li.nextLine();
				rows.add(s);

				if ( ++count % 1000 == 0 ) {
					LoggingSplittFileView.updateProgressBar(allsize,fileSize);					
				}
				
				size = size + s.length();
				allsize += s.length();
				if ( size > maxFileSize ) {
					writeFile(file,counter,rows);
					rows.clear();
					size = 0;
					counter++;
				}
				
			}
			
			writeFile(file,counter,rows);
			LoggingSplittFileView.updateProgressBar(999,1000);					
			return count;
			
			
		} catch (IOException e2) {
			logger.error(e2.getMessage(), e2);
		}
		return -1;
		
		
	}


	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start splitt file  " + logFile.getAbsolutePath());
		splitFile(logFile);
		logger.info("end splitt in  " + (System.currentTimeMillis() - start) + " ms.");
		if ( canceled)
			LoggingSplittFileView.updateView("canceled");
		else
			LoggingSplittFileView.updateView("exit");					
		return null;
	}


	public boolean isCanceled() {
		return canceled;
	}


	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

}
