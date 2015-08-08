package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

public class CountryTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(CountryTextControl.class);

	public CountryTextControl(Composite parent) {
		super(parent, 100, false);
	}

	@Override
	protected String getLabel() {
		return "country";
	}


	@Override
	public void saveValue(String value) {
		logger.info("Country : "+value);
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}
	
	
}
