package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class InsuranceNoTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(InsuranceNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "INSURANCE_NO_PROPERTY";



	public InsuranceNoTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "InsuranceId";
	}


	@Override
	public void saveValue(String value) {
		logger.info("InsuranceId: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		ClubMobilModelProvider.INSTANCE.insuranceId = value;
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
