package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class LogOutputDirControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(LogOutputDirControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new LogOutputDirControl(parent);
	}

	public LogOutputDirControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "Log Output Dir";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.outputDirName = value;
		logger.info("log output dir name : "+value);
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY, value);
		
	}

	@Override
	protected String getDefaultValue() {
		String savedDir = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_OUTPUT_DIR_PROPERTY,"");
		return savedDir;
	}

}
