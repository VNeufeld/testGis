package com.dev.gis.app.taskmanager.testAppView;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.DropDownTextControl;
import com.dev.gis.connector.api.AdacModelProvider;

public class AdacDropDownTextControl extends DropDownTextControl{
	
	private static Logger logger = Logger.getLogger(AdacDropDownTextControl.class);
	private static final String PREFERENCE_PROPERTY = "ADAC_SERVER_PROPERTY_LIST";
	private static final String PREFERENCE_PROPERTY_SELECT = "ADAC_SERVER_PROPERTY_LIST_SELECT";
	public AdacDropDownTextControl(Composite parent) {
		super(parent, 300, false);

	}
	@Override
	public void saveValue(String value) {
		if(value.isEmpty())
			return;
		AdacModelProvider.INSTANCE.serverUrl = value;
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
		super.elements.clear();
		super.elements.addAll(Arrays.asList(elements));
		super.updateView(super.elements);
		ret = elements[0];
		return ret;
	}
	@Override
	protected String getPropertyName() {
		// TODO Auto-generated method stub
		return PREFERENCE_PROPERTY_SELECT;
	}
}
