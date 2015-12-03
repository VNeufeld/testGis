package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class LogDirControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(LogDirControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new LogDirControl(parent);
	}

	public LogDirControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "Log Dir";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.logDirName = value;
		logger.info("logDirName : "+value);
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY, value);
		
	}

	@Override
	protected String getDefaultValue() {
		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_INPUT_DIR_PROPERTY,"");
		return savedDir;
	}

}
