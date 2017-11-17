package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ChangePasswordTextControl extends ObjectTextControl{
	private static Logger logger = Logger.getLogger(ChangePasswordTextControl.class);

	public ChangePasswordTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
	}
	
	@Override
	protected String getLabel() {
		return "New Password";
	}


	@Override
	public void saveValue(String value) {
		logger.info("Neu password: "+value);
		ClubMobilModelProvider.INSTANCE.changePasword = value;
		
	}

	@Override
	protected String getDefaultValue() {
		return "";
	}

}
