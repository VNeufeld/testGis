package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.AdacModelProvider;

public class AdacServerTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(AdacServerTextControl.class);
	private static final String PREFERENCE_PROPERTY = "ADAC_SERVER_PROPERTY";

	public AdacServerTextControl(Composite parent) {
		super(parent, 300);
	}

	@Override
	protected String getLabel() {
		return "ADAC Server URL";
	}


	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.serverUrl = value;
		logger.info("server URL: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}