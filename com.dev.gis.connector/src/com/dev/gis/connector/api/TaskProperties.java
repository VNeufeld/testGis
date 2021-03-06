package com.dev.gis.connector.api;

import java.util.Calendar;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

import com.dev.gis.connector.sunny.DayAndHour;

public class TaskProperties {
	private static final String PREFERENCE_SERVER_PROPERTY = "SERVER_PROPERTY";
	private static final String PREFERENCE_OPERATOR_PROPERTY = "OPERATOR_PROPERT";
	private static final String PREFERENCE_LANGUAGE_PROPERTY = "LANGUAGE_PROPERTY";
	private static final String PREFERENCE_USE_DUMMY_PROPERTY = "USE_DUMMY_PROPERTY";
	private static final String PREFERENCE_APTCODE_PROPERTY = "APTCODE_PROPERTY";
	private static final String PREFERENCE_PICKUPDATE_PROPERTY = "PICKUPDATE_PROPERTY";
	private static final String PREFERENCE_DROPOFFDATE_PROPERTY = "DROPOFFDATE_PROPERTY";
	private static final String PREFERENCE_AGENCY_PROPERTY = "AGENCY_PROPERTY";
	private static final String PREFERENCE_ONLY_LOGGING_PROPERTY = "ONLY_LOGGING_PROPERTY";

	private static final String PREFERENCE_ADAC_OPERATOR_PROPERTY = "ADAC_OPERATOR_PROPERTY";
	
	private static String PREFERENCE_PATH = "TASK_PREFERENCE";
	public static String VEHICLE_REQUEST_PARAM = "/vehicleRequest?pageSize=200";
	public static String LANGUAGE_CODE = "EN";
	public static Integer SALES_CHANNEL = 3;
	public static Integer CALLED_FROM_ANDROID = 9;
	public static Integer CALLED_FROM_RENTFOX = 10;
	
	
	private static TaskProperties taskProperties;
	
	public static TaskProperties getTaskProperties() {
		if ( taskProperties == null)
			taskProperties = new TaskProperties();
		return taskProperties;
	}
	
	private String serverProperty;
	private Long   operator = 152573l;
	private String agencyNo = "10002";

	private String  adacOperatos = "";
	
	private int   language = 2;
	private boolean  useDummy = false;
	private String aptCode="PMI";
	
	private String pickupDate = "";
	private String dropoffDate = "";
	private boolean  onlyLogging = true;
	
	public boolean isOnlyLogging() {
		return onlyLogging;
	}

	public void setOnlyLogging(boolean onlyLogging) {
		this.onlyLogging = onlyLogging;
	}

	private TaskProperties() {
		serverProperty = "http://localhost:8080/joi";
	}

	public String getServerProperty() {
		return serverProperty;
	}

	public void setServerProperty(String serverProperty) {
		this.serverProperty = serverProperty;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}
	
	public void saveProperty() {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);

		preferences.put(PREFERENCE_SERVER_PROPERTY, serverProperty);
		preferences.putLong(PREFERENCE_OPERATOR_PROPERTY, operator);
		preferences.putInt(PREFERENCE_LANGUAGE_PROPERTY, language);
		preferences.putBoolean(PREFERENCE_USE_DUMMY_PROPERTY, useDummy);
		preferences.put(PREFERENCE_APTCODE_PROPERTY, aptCode);
		preferences.put(PREFERENCE_PICKUPDATE_PROPERTY, pickupDate);
		preferences.put(PREFERENCE_DROPOFFDATE_PROPERTY, dropoffDate);
		preferences.put(PREFERENCE_AGENCY_PROPERTY, agencyNo);
		preferences.put(PREFERENCE_ADAC_OPERATOR_PROPERTY, adacOperatos);
		preferences.putBoolean(PREFERENCE_ONLY_LOGGING_PROPERTY, onlyLogging);

		try {
			preferences.flush();
		} catch (BackingStoreException e1) {
			//logger.error(e1);
		}
	}
	
	public void readProperty() {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(PREFERENCE_PATH);
		serverProperty = preferences.get(PREFERENCE_SERVER_PROPERTY, serverProperty);
		operator = preferences.getLong(PREFERENCE_OPERATOR_PROPERTY, operator);
		language = preferences.getInt(PREFERENCE_LANGUAGE_PROPERTY, language);
		useDummy = preferences.getBoolean(PREFERENCE_USE_DUMMY_PROPERTY, useDummy);
		aptCode = preferences.get(PREFERENCE_APTCODE_PROPERTY, aptCode);
		pickupDate = preferences.get(PREFERENCE_PICKUPDATE_PROPERTY, pickupDate);
		dropoffDate = preferences.get(PREFERENCE_DROPOFFDATE_PROPERTY, dropoffDate);
		agencyNo = preferences.get(PREFERENCE_AGENCY_PROPERTY, agencyNo);
		onlyLogging = preferences.getBoolean(PREFERENCE_ONLY_LOGGING_PROPERTY, true);
		
		adacOperatos = preferences.get(PREFERENCE_ADAC_OPERATOR_PROPERTY,"1");

		

	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	public boolean isUseDummy() {
		return useDummy;
	}

	public void setUseDummy(boolean useDummy) {
		this.useDummy = useDummy;
	}

	public String getAptCode() {
		return aptCode;
	}

	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}

	public void setAdacOperators(String ops) {
		this.adacOperatos = ops;
	}

	public String getAdacOperators() {
		return adacOperatos;
	}
	

	public void setDropoffDate(DayAndHour dh) {
		String dateProperty = dh.getDate()+"T"+dh.getTime();

		this.dropoffDate = dateProperty;
	}

	public DayAndHour getDropoffDate() {
		String[] parts = this.dropoffDate.split("T");
		DayAndHour dh = new DayAndHour();
		dh.setDate(parts[0]);
		if ( parts.length > 1)
			dh.setTime(parts[1]);
		return dh;
	}

	public String getAgencyNo() {
		return agencyNo;
	}

	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}

	public void setPickupDate(String sDate) {
		this.pickupDate = sDate;
		
	}
	
	public String getPickupDate() {
		return this.pickupDate;
	}

}
