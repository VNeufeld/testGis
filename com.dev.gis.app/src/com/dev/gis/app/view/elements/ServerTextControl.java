package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.SunnyModelProvider;

public class ServerTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(ServerTextControl.class);
	private static final String PREFERENCE_PROPERTY = "SERVER_PROPERTY";

	public static void createCityLocationSearch(Composite parent) {
		new ServerTextControl(parent);
	}

	public ServerTextControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "Server URL";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.serverUrl = value;
		logger.info("server URL: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
