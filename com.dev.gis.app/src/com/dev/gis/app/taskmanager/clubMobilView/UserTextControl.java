package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class UserTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(UserTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_LOGIN_USER_PROPERTY";



	public UserTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "User";
	}


	@Override
	public void saveValue(String value) {
		ClubMobilModelProvider.INSTANCE.loginUser = value;
		logger.info("Login user: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
