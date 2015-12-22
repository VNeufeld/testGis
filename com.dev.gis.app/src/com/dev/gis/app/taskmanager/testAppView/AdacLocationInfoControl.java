package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.LocationInfoOutputControl;
import com.dev.gis.connector.joi.protocol.TravelInformation;

public class AdacLocationInfoControl extends LocationInfoOutputControl {

	public AdacLocationInfoControl(Composite parent) {
		super(parent);
	}

	@Override
	public void update(Object param) {
		if ( param instanceof TravelInformation) {
			TravelInformation travelInformation = (TravelInformation) param;

			pickUpCity.setValue(String.valueOf(travelInformation.getPickUpLocation().getCityId()));
			dropOffCity.setValue(String.valueOf(travelInformation.getDropOffLocation().getCityId()));
			
		}
		
	}
	
}
