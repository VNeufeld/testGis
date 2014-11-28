package com.dev.gis.app.taskmanager.loggingView.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

class LoggingUtils {
	
	private static Logger logger = Logger.getLogger(LoggingUtils.class);

	private static final String DATE_TIME_FORMAT = "yyyy_MM_dd_HH";

	public static File[] getAllFiles(String dirName, Calendar from, Calendar to) {
		// TODO Auto-generated method stub
		List<File> list = new ArrayList<File>();
		File dir = new File(dirName);
		if ( dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			for ( File f : files) {
				if ( f.isDirectory()) {
					
				}
				else {
					String fileName = f.getName();
					if ( fileNameMatch(fileName, from, to))
						list.add(f);
				}
			}
			
		}
		return list.toArray(new File[0]);
	}
	
	public static File[] getAllFilesRecurisive(String dirName, String filePattern, Calendar from, Calendar to) {
		List<File> list = new ArrayList<File>();
		File dir = new File(dirName);
		if ( dir.exists() && dir.isDirectory()) {
			Collection<File> files = FileUtils.listFiles(dir,null, true);
			for ( File f : files) {
				String fileName = f.getName();
				boolean flag = true;
				if ( StringUtils.isNotBlank(filePattern)) {
					if (StringUtils.containsIgnoreCase(fileName,filePattern))
						flag = true;
					else
						flag = false;
					
				}
				if ( flag ) {
					if ( fileNameMatch2(fileName, from, to))
						list.add(f);
				}
			}
			
		}
		return list.toArray(new File[0]);
	}


	private static boolean fileNameMatch(String fileName, Calendar from, Calendar to) {
		SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);

		int pos = fileName.indexOf(".log.");
		if ( pos > 0 ) {
			String log_ext = fileName.substring(pos+5);
			Date logDate = null;
			try {
				logDate = stf.parse(log_ext);
			}
			catch(ParseException e) {
				logger.info("can't parse "+e.getMessage());
			}
			
			if (logDate != null ) {
				Calendar c = Calendar.getInstance();
				c.setTime(logDate);
				c.set(Calendar.MINUTE,10);
				from.set(Calendar.MINUTE,0);
				from.set(Calendar.SECOND,0);
				to.set(Calendar.MINUTE,0);
				to.set(Calendar.SECOND,0);
				
				//logger.info("file : "+stf.format(c.getTime())+ " from : "+stf.format(from.getTime())+ " to : "+ stf.format(to.getTime()));
				
				if ( c.before(to) && c.after(from))
					return true;
			}
			
		}
		return false;
	}
	
	private static boolean fileNameMatch2(String fileName, Calendar from, Calendar to) {
		SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);
		SimpleDateFormat stf2 = new SimpleDateFormat("yyyyMMdd");
		
		if ( StringUtils.endsWithIgnoreCase(fileName, ".log")) {
			try {
				String matchDate = stf2.format(from.getTime());
				if (StringUtils.contains(fileName, matchDate))
					return true;
				else
					return false;
			}
			catch(Exception err) {
				logger.error(err.getMessage(),err);
			}
			return true;
		}
		int pos = fileName.indexOf(".log.");
		if ( pos > 0 ) {
			String log_ext = fileName.substring(pos+5);
			Date logDate = null;
			try {
				logDate = stf.parse(log_ext);
			}
			catch(ParseException e) {
				logger.info("can't parse "+e.getMessage());
			}
			
			if (logDate != null ) {
				Calendar c = Calendar.getInstance();
				c.setTime(logDate);
				c.set(Calendar.MINUTE,10);
				from.set(Calendar.MINUTE,0);
				from.set(Calendar.SECOND,0);
				to.set(Calendar.MINUTE,0);
				to.set(Calendar.SECOND,0);
				
				//logger.info("file : "+stf.format(c.getTime())+ " from : "+stf.format(from.getTime())+ " to : "+ stf.format(to.getTime()));
				
				if ( c.before(to) && c.after(from))
					return true;
			}
			
		}
		return false;
	}


}
