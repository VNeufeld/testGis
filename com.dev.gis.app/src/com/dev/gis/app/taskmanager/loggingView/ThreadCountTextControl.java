package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class ThreadCountTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(ThreadCountTextControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new ThreadCountTextControl(parent);
	}

	public ThreadCountTextControl(Composite parent) {
		super(parent, 100, true);
	}

	@Override
	protected String getLabel() {
		return "Thread Count";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.threadCounts = Integer.valueOf(value);
		logger.info("ThreadCount : "+value);
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_MAX_THREADS_PROPERTY, value);
		
	}

	@Override
	protected String getDefaultValue() {
		String maxThreadsSaved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_MAX_THREADS_PROPERTY,"1");
		return maxThreadsSaved;
	}

}
