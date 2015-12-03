package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class SearchBookingIdTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SearchBookingIdTextControl.class);


	public static void createCityLocationSearch(Composite parent) {
		new SearchBookingIdTextControl(parent);
	}

	public SearchBookingIdTextControl(Composite parent) {
		super(parent, 300, false);
	}

	@Override
	protected String getLabel() {
		return "Search Booking";
	}


	@Override
	public void saveValue(String value) {
		LoggingModelProvider.INSTANCE.searchBooking = value;
		logger.info("Search Booking : "+value);
		LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_BOOKING_PROPERTY, value);
		
	}

	@Override
	protected String getDefaultValue() {
		String saved = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_BOOKING_PROPERTY,"");
		return saved;
	}

}
