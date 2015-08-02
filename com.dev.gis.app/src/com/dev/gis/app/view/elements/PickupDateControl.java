package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.ModelProvider;

public class PickupDateControl extends ObjectDateControl {
	private static Logger logger = Logger.getLogger(PickupDateControl.class);
	
	private static final String PREFERENCE_PICKUPDATE_PROPERTY = "PICKUPDATE_PROPERTY_X";

	public PickupDateControl(Composite parent) {
		super(parent);
	}


	@Override
	protected String getLabel() {
		return "PickupDate";
	}


	@Override
	protected void saveDate() {
		ModelProvider.INSTANCE.pickupDateTime = selectedValue;
	}


	@Override
	protected String getPropertyName() {
		return PREFERENCE_PICKUPDATE_PROPERTY;
	}
	

}
