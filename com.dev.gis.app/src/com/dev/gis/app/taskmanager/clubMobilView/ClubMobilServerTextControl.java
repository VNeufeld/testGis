package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilServerTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(ClubMobilServerTextControl.class);
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_SERVER_PROPERTY";

	public ClubMobilServerTextControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "ClubMobil Server URL";
	}


	@Override
	public void saveValue(String value) {
		ClubMobilModelProvider.INSTANCE.serverUrl = value;
		logger.info("server URL: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		String defaultValue = readProperty(PREFERENCE_PROPERTY);
		if ( StringUtils.isEmpty(defaultValue)) {
			defaultValue = "http://localhost:8080/cm-web-joi/clubmobil";
		}
		return defaultValue;
	}

}
