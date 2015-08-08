package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.SunnyModelProvider;

public class AgencyNoTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(AgencyNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "AGENCY_NO_PROPERTY";


	public static void createCityLocationSearch(Composite parent) {
		new AgencyNoTextControl(parent);
	}

	public AgencyNoTextControl(Composite parent) {
		super(parent, 100, true);
	}

	@Override
	protected String getLabel() {
		return "Agency";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.agencyNo = value;
		logger.info("agency: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
