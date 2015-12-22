package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class PaymentTypeControl extends ObjectTextControl {
	
	private static Logger logger = Logger.getLogger(PaymentTypeControl.class);
	
	private static final String PREFERENCE_PROPERTY = "PAYMENT_TYPE_PROPERTY";


	public static void createCityLocationSearch(Composite parent) {
		new PaymentTypeControl(parent);
	}

	public PaymentTypeControl(Composite parent) {
		super(parent, 100, true);
	}

	@Override
	protected String getLabel() {
		return "PaymentType : 1-KK, 8 - Paypals";
	}


	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.paymentType = value;
		logger.info("PaymentType: "+value);
		saveProperty(PREFERENCE_PROPERTY,value);
		
	}

	@Override
	protected String getDefaultValue() {
		return readProperty(PREFERENCE_PROPERTY);
	}

}
