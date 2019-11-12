package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.ModelProvider;

public class DropOffDateControl extends ObjectDateControl {
	private static Logger logger = Logger.getLogger(DropOffDateControl.class);
	
	private static final String PREFERENCE_PICKUPDATE_PROPERTY = "DROPOFF_PROPERTY_X";

	public DropOffDateControl(Composite parent) {
		super(parent);
	}


	@Override
	protected String getLabel() {
		return "DropoffDate";
	}


	@Override
	protected void saveDate() {
		ModelProvider.INSTANCE.dropoffDateTime = selectedValue;
	}


	@Override
	protected String getPropertyName() {
		return PREFERENCE_PICKUPDATE_PROPERTY;
	}
	

}
