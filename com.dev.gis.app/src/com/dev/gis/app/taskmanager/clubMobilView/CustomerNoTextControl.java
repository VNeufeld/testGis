package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class CustomerNoTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(CustomerNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_CUSTOMER_NO_PROPERTY";



	public CustomerNoTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "CustomerNo";
	}


	@Override
	public void saveValue(String value) {
		logger.info("CustomerNo: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
