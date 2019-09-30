package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;

public class DispoStationNoTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(DispoStationNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_DISPO_STATION_NO_PROPERTY";



	public DispoStationNoTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "StationNo";
	}


	@Override
	public void saveValue(String value) {
		logger.info("StationNo: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
