package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyExtServcatFilterTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SunnyExtServcatFilterTextControl.class);

	public SunnyExtServcatFilterTextControl(Composite parent) {
		super(parent, 100, false);
	}

	@Override
	protected String getLabel() {
		return "Servcat Filter";
	}


	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.servcatFilter = value;
		logger.info("servcatFilter: "+value);
	
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
