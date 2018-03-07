package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class BusinessSegmentTextControl extends ObjectTextControl{
	
	private static Logger logger = Logger.getLogger(BusinessSegmentTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "BUSINESS_SEGMENT_ID_PROPERTY";



	public BusinessSegmentTextControl(Composite parent, int size, boolean span) {
		super(parent, size, span);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected String getLabel() {
		return "BusinessSegmentId";
	}


	@Override
	public void saveValue(String value) {
		logger.info("BusinessSegmentId: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		ClubMobilModelProvider.INSTANCE.businessSegmentId = value;
		
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}


}
