package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

public class SearchStringTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SearchStringTextControl.class);

	public SearchStringTextControl(Composite parent) {
		super(parent, 100, false);
	}

	@Override
	protected String getLabel() {
		return "search";
	}


	@Override
	public void saveValue(String value) {
		logger.info("Search String : "+value);
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}
}
