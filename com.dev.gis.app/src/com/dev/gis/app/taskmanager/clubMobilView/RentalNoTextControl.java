package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class RentalNoTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(RentalNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "RENTAL_NO_PROPERTY";



	public RentalNoTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "RentalNo";
	}


	@Override
	public void saveValue(String value) {
		logger.info("RentalNo: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		ClubMobilModelProvider.INSTANCE.rentalNo = value;
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
