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

	private static final String DATE_TIME_FORMAT_SUNNY = "yyyy-MM-dd-HH";

	private static final String DATE_FORMAT_SUNNY = "yyyy-MM-dd";
	
	public static File[] getAllFilesRecurisive(String dirName, String filePattern, Calendar from, Calendar to) {
		List<File> list = new ArrayList<File>();
		if ( StringUtils.isBlank(filePattern)) {
			filePattern = "*";
		}
		
		File dir = new File(dirName);
		if ( dir.exists() && dir.isDirectory()) {
			Collection<File> files = FileUtils.listFiles(dir,null, true);
			for ( File f : files) {
				String fileName = f.getName();
				if ( matchPattern(fileName,filePattern)) {
					if (from != null && to != null ) {
						if ( fileNameMatch2(f, from, to))
							list.add(f);
					}
					else
						list.add(f);
				}
			}
			
		}
		return list.toArray(new File[0]);
	}

	private static boolean matchPattern(String fileName, String filePattern) {
		if ( "*".equals(filePattern))
			return true;
		if ( filePattern.endsWith("*")) {
			String pattern = StringUtils.removeEnd(filePattern,"*");
			if ( StringUtils.startsWithIgnoreCase(fileName, pattern))
				return true;
		}
		if ( filePattern.length() > 3 && filePattern.startsWith("%") && filePattern.endsWith("%")) {
			String pattern = StringUtils.substringBetween(filePattern,"%", "%");
			if ( pattern.length() > 0 && StringUtils.containsIgnoreCase(fileName,pattern))
				return true;
			
		}
		
		return StringUtils.containsIgnoreCase(fileName,filePattern);
	}
	
	private static boolean fileNameMatch2(File f, Calendar from, Calendar to) {
		SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);
		SimpleDateFormat stf2 = new SimpleDateFormat("yyyyMMdd");
		String fileName = f.getName();
		Date fileDate = getSunnyLogFileDate(f);
		if ( fileDate != null) {
			if ( checkBetween(fileDate,from, to))
				return true;
		}
		
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
				logger.debug("can't parse "+e.getMessage());
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

	private static boolean checkBetween(Date fileDate, Calendar from,
			Calendar to) {
		Calendar c = Calendar.getInstance();
		c.setTime(fileDate);
		c.set(Calendar.MINUTE,10);
		from.set(Calendar.MINUTE,0);
		from.set(Calendar.SECOND,0);
		to.set(Calendar.MINUTE,0);
		to.set(Calendar.SECOND,0);
		return ( c.before(to) && c.after(from));
	}

	private static Date getSunnyLogFileDate(File f) {
		String fileName = f.getName();
		SimpleDateFormat stf = new SimpleDateFormat(DATE_FORMAT_SUNNY);
		SimpleDateFormat stf2 = new SimpleDateFormat(DATE_TIME_FORMAT_SUNNY);
		String dateSuffix = StringUtils.substringAfter(fileName, "log.");
		Date d = null;
		try {
			if (dateSuffix != null ) {
				if ( dateSuffix.length() == 10)
					d = stf.parse(dateSuffix);
				else if ( dateSuffix.length() == 13)
					d = stf2.parse(dateSuffix);
			}
		}
		catch(ParseException e) {
			logger.info("can't parse "+e.getMessage());
		}
		if ( d != null)
			return d;
		if ( StringUtils.endsWithIgnoreCase(fileName,".log")) {
			Date dx = new Date(f.lastModified());
			return dx;
		}
		return null;
	}


}
