package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.AdacModelProvider;

public class PromotionCodeTextControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(PromotionCodeTextControl.class);
	
	private static final String PREFERENCE_PROPERTY = "PROMOTION_CODE_PROPERTY";


	public static void createPromotionCodeControl(Composite parent) {
		new PromotionCodeTextControl(parent);
	}

	public PromotionCodeTextControl(Composite parent) {
		super(parent, 200, true);
	}

	@Override
	protected String getLabel() {
		return "PromotionCode";
	}


	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.promotionCode = value;
		logger.info("promotionCode: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
