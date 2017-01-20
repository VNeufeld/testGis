package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.SunnyModelProvider;

public class AgencyBookingCodeControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(AgencyBookingCodeControl.class);
	
	private static final String PREFERENCE_PROPERTY = "AGENCY_BOOKING_CODE_PROPERTY";


	public static void createCityLocationSearch(Composite parent) {
		new AgencyBookingCodeControl(parent);
	}

	public AgencyBookingCodeControl(Composite parent) {
		super(parent, 200, true);
	}

	@Override
	protected String getLabel() {
		return "AgencyBookingCode";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.agencyBookingCode = value;
		logger.info("agencyBookingCode: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
