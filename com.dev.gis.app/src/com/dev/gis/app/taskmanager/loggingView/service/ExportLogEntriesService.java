package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.MessageBox;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.ProgressBarElement;
import com.dev.gis.app.xmlutils.FilterUtils;

public class ExportLogEntriesService implements Callable<String> {

	private static Logger logger = Logger.getLogger(ExportLogEntriesService.class);
	
	String[] keys = { "PhoneNumber" , "GivenName", "Surname", "AddressLine" , "Email" , "CityName", "PostalCode", "Password", 
			"CustomerNo", "CustomerNoExt", 
			"Name", "FirstName", "Street", "ZipCode", "City" , "PhoneArea", "PhoneExt", "EMail", "Driver", "DriverFirstName"};
	
	public ExportLogEntriesService() {

		readKeys();
	}


	private void readKeys() {
		File f = new File("shadowKeys.txt");
		if ( !f.exists() ) {
			logger.info("not shadowKeys file exists ");
			return;
		}
		logger.info("read shadow file  "+f.getAbsolutePath());
		try {
			List<String> shadowKeys = FileUtils.readLines(f);
			if (shadowKeys != null && shadowKeys.size() > 0 ) {
				logger.info(" read "+shadowKeys.size()+ " keys.");
				keys = shadowKeys.toArray(new String[0]);
				for ( String key : keys)
					logger.info(" shadow key : "+key);
			}
			else {
				logger.info(" no shodow keys found. ");
			}
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		
	}


	public void writeEntries() throws IOException {

		
		List<LogEntry> entries = LogEntryModel.getInstance().getLoggingEntries();
		

		String newFile = LoggingModelProvider.INSTANCE.outputFileName;

		File of = new File(newFile);

		logger.info("write entries to :"+newFile);

		ProgressBarElement.updateFileName(" ");
		

		List<String> list = new ArrayList<String>();
		for (LogEntry entry : entries) {
			List<String> logEntry = entry.getEntry();
			for ( String xmlString : logEntry) {
				if ( LoggingModelProvider.INSTANCE.useShadow)
					xmlString = FilterUtils.shadowKeyWords(xmlString, keys);
				list.add(xmlString);
			}
			
		}
		logger.info("write lines " + list.size()+ "to :"+newFile);
		FileUtils.writeLines(of, list);
		
		ProgressBarElement.updateFileName("write result in " + newFile);

	}

	@Override
	public String call() throws Exception {
		long start = System.currentTimeMillis();
		logger.info("start export session ");
		writeEntries();
		logger.info("end export in  " + (System.currentTimeMillis() - start) + " ms.");
		return null;
	}

}
