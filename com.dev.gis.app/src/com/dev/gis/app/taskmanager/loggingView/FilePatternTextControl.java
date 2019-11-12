package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class FilePatternTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(FilePatternTextControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new FilePatternTextControl(parent);
	}

	public FilePatternTextControl(Composite parent) {
		super(parent, 300, true);
	}

	@Override
	protected String getLabel() {
		return "file pattern";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.filePattern = value;
		logger.info("filePattern : "+value);
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_FILE_MATCH, value);
		
	}

	@Override
	protected String getDefaultValue() {
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_FILE_MATCH,"request");
		return saved;
	}

}
