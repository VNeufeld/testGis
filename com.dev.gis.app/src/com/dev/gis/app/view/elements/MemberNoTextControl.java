package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class MemberNoTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(MemberNoTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "MEMBER_NO_PROPERTY";


	public static void createCityLocationSearch(Composite parent) {
		new MemberNoTextControl(parent);
	}

	public MemberNoTextControl(Composite parent) {
		super(parent, 100, true);
	}

	@Override
	protected String getLabel() {
		return "MemberNo";
	}


	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.memberNo = value;
		logger.info("memberNo: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
