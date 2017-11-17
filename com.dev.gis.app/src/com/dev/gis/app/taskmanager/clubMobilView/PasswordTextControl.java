package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class PasswordTextControl extends ObjectTextControl{
	private static Logger logger = Logger.getLogger(PasswordTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_LOGIN_PWD_PROPERTY";

	public PasswordTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
	}
	
	@Override
	protected String getLabel() {
		return "Password";
	}


	@Override
	public void saveValue(String value) {
		ClubMobilModelProvider.INSTANCE.loginPassword = value;
		logger.info("Login password: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
