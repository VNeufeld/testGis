package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;

public class ReservationNoTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(ReservationNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_RESERVATION_NO_PROPERTY";



	public ReservationNoTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "ReservationNo";
	}


	@Override
	public void saveValue(String value) {
		logger.info("ReservationNo: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
