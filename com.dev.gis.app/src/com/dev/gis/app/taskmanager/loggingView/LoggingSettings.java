package com.dev.gis.app.taskmanager.loggingView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class LoggingSettings {
	private final static Logger logger = Logger.getLogger(LoggingSettings.class);

	private static final String DATE_TIME_FORMAT = "yyyy_MM_dd_HH_mm_ss";

	public static final String PREFERENCE_PATH = "com.dev.gis.app/logging";
	
	public static final String PREFERENCE_MAX_THREADS_PROPERTY = "maxThreads"; 
	public static final String PREFERENCE_MAX_FILE_SIZE_PROPERTY = "maxFileSize"; 
	public static final String PREFERENCE_OUTPUT_DIR_PROPERTY = "logging.output.dir"; 
	public static final String PREFERENCE_INPUT_DIR_PROPERTY = "logging.input.dir";
	public static final String PREFERENCE_INPUT_FILE_PROPERTY = "logging.input.file";
	public static final String PREFERENCE_SESSION_PROPERTY = "logging.input.session"; 
	public static final String PREFERENCE_BOOKING_PROPERTY = "logging.booking.session"; 
	public static final String PREFERENCE_FILE_MATCH = "logging.input.matching"; 
	public static final String PREFERENCE_USE_DATES = "logging.input.usedates"; 
	public static final String PREFERENCE_USE_SHADOW = "logging.input.useshadow"; 
	
	
	public static final String PREFERENCE_START_TIME_PROPERTY = "startTimeProperty"; 
	public static final String PREFERENCE_END_TIME_PROPERTY = "endTimeProperty"; 
	public static final String PREFERENCE_ZIP_PROPERTY = "packAndGo.zip"; 
	public static final String PREFERENCE_EMPTY_DIR_PROPERTY = "packAndGo.makeTempDir"; 
	
	public static void saveProperty(final String property, String value) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);

		preferences.put(property, value);
		
		try {
			preferences.flush();
		} catch (BackingStoreException e1) {
			logger.error(e1);
		}
	}
	
	public static void saveTimeProperty(final Calendar from, final Calendar to) {
		
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);

		SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);

		preferences.put(PREFERENCE_START_TIME_PROPERTY, stf.format(from.getTime()));
		preferences.put(PREFERENCE_END_TIME_PROPERTY, stf.format(to.getTime()));
		
		try {
			preferences.flush();
		} catch (BackingStoreException e1) {
			logger.error(e1);
		}
	}

	public static Calendar[] readTimeProperty() {
		
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		
		Calendar[] result = new Calendar[] {
				Calendar.getInstance(),Calendar.getInstance() 
		};

		SimpleDateFormat stf = new SimpleDateFormat(DATE_TIME_FORMAT);

		String time = preferences.get(PREFERENCE_START_TIME_PROPERTY,"");
		if ( StringUtils.isNotBlank(time)) {
			try {
				Date d1 = stf.parse(time);
				result[0].setTime(d1);
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}

		}
		time = preferences.get(PREFERENCE_END_TIME_PROPERTY,"");
		if ( StringUtils.isNotBlank(time)) {
			try {
				Date d1 = stf.parse(time);
				result[1].setTime(d1);
			} catch (ParseException e) {
				logger.info(e.getMessage());
			}
		}
		return result;
	}
	
	
	public static String readProperty(final String property, String defaultValue) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		String dir = preferences.get(property, defaultValue);
		
		return dir;
		
		
		
	}

}
