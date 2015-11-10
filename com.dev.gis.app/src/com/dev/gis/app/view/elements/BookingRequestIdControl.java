package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

public class BookingRequestIdControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(BookingRequestIdControl.class);

	public static void createCityLocationSearch(Composite parent) {
		new BookingRequestIdControl(parent);
	}

	public BookingRequestIdControl(Composite parent) {
		super(parent, 100, true);
	}

	public BookingRequestIdControl(Composite parent, int size,  boolean span) {
		super(parent, size, span);
	}

	@Override
	protected String getLabel() {
		return "Booking R ID";
	}


	@Override
	public void saveValue(String value) {
		logger.info("Boking ID : "+value);
		
	}

}
