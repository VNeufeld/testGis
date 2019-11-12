package com.dev.gis.app.taskmanager.SunnyCarsView;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.testAppView.AdacDropDownTextControl;
import com.dev.gis.app.view.elements.DropDownTextControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyDropDownControl extends DropDownTextControl{

	public SunnyDropDownControl(Composite parent) {
		super(parent,300,true);
		// TODO Auto-generated constructor stub
	}
	private static Logger logger = Logger.getLogger(AdacDropDownTextControl.class);
	private static final String PREFERENCE_PROPERTY = "SUNNY_SERVER_PROPERTY_LIST";
	private static final String PREFERENCE_PROPERTY_LIST = "SUNNY_SERVER_PROPERTY_LIST_SELECT";
	@Override
	public void saveValue(String value) {
		if(value.isEmpty())
			return;
		SunnyModelProvider.INSTANCE.serverUrl = value;
		logger.info("server URL: "+value);


		if(!super.elements.contains(value)) {
		super.elements.add(value);
		super.updateView(super.elements);
		}
		
		String v = String.join(";", super.elements);
		System.out.println(v);
		logger.info(v);
		saveProperty(PREFERENCE_PROPERTY,v);
		
	}

	@Override
	protected String getDefaultValue() {
		String defaultValue = readProperty(PREFERENCE_PROPERTY);
		String ret = "";
		if ( StringUtils.isEmpty(defaultValue)) {
			defaultValue = "http://localhost:8080/web-joi/joi";
		
		}
		String[] elements = StringUtils.split(defaultValue,";");
		
		super.elements.addAll(Arrays.asList(elements));
		super.updateView(super.elements);
		ret = elements[0];
		return ret;
	}

	@Override
	protected String getPropertyName() {
		// TODO Auto-generated method stub
		return PREFERENCE_PROPERTY_LIST;
	}
}
