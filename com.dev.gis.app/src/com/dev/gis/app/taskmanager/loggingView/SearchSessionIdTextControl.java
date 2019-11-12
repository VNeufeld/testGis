package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class SearchSessionIdTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SearchSessionIdTextControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new SearchSessionIdTextControl(parent);
	}

	public SearchSessionIdTextControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "Search Session";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.searchSession = value;
		logger.info("Search Session : "+value);
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY, value);
		
	}

	@Override
	protected String getDefaultValue() {
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_SESSION_PROPERTY,"");
		return saved;
	}

}
