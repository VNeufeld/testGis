package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.ModelProvider;

public class SessionIdControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(SessionIdControl.class);

	public SessionIdControl(Composite parent) {
		super(parent, 200, false);
	}

	@Override
	protected String getLabel() {
		return "SessionID";
	}


	@Override
	public void saveValue(String value) {
		ModelProvider.INSTANCE.sessionID = value;
		logger.info("sessionID : "+value);
	}

	protected String getDefaultValue() {
		return "";
	}
	
}
