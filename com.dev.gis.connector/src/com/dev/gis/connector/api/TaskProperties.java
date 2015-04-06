package com.dev.gis.connector.api;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;

public class TaskProperties {
	private static final String PREFERENCE_SERVER_PROPERTY = "SERVER_PROPERTY";
	private static final String PREFERENCE_OPERATOR_PROPERTY = "OPERATOR_PROPERT";
	private static final String PREFERENCE_LANGUAGE_PROPERTY = "LANGUAGE_PROPERTY";
	private static String PREFERENCE_PATH = "TASK_PREFERENCE";
	public static String VEHICLE_REQUEST_PARAM = "/vehicleRequest?pageSize=200";
	public static String LANGUAGE_CODE = "de-DE";
	public static Integer SALES_CHANNEL = 7;
	public static Integer CALLED_FROM = 9;
	
	
	private static TaskProperties taskProperties;
	
	public static TaskProperties getTaskProperties() {
		if ( taskProperties == null)
			taskProperties = new TaskProperties();
		return taskProperties;
	}
	
	private String serverProperty;
	private Long   operator = 152573l;
	private int   language = 2;
	
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

	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}
	

}
