package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class SearchThreadTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SearchThreadTextControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new SearchThreadTextControl(parent);
	}

	public SearchThreadTextControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "Search Thread";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.searchThread = value;
		logger.info("Search Thread : "+value);
		
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
