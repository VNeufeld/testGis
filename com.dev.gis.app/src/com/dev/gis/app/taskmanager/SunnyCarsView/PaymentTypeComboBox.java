package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectsComboBox;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.Payment;
import com.dev.gis.connector.sunny.PaymentType;

public class PaymentTypeComboBox extends ObjectsComboBox{
	
	private static final String PREFERENCE_PROPERTY = "PAYMENT_PROPERTY";
	
	private static Logger logger = Logger.getLogger(PaymentTypeComboBox.class);
	
	private final static String items[] = { "CreditCard", "DebitNote", "Agency", " iDeal" };
	
	public PaymentTypeComboBox(Composite parent, int size) {
		super(parent, size, true);
	}

	public final String getSelectedLanguage() {
		return selectedValue;
	}


	@Override
	protected String getLabel() {
		return "PaymentType";
	}
	protected String getPropertyName() {
		return PREFERENCE_PROPERTY;
	}

	@Override
	protected String[] getItems() {
		return items;
	}

	@Override
	protected void logSelectedValue(int index) {
		logger.info("select payment type "+ index + " payment type = "+ selectedValue);
	}

	@Override
	protected void saveSelected(int index, String paymentTypeString) {
		
		SunnyModelProvider.INSTANCE.paymentType = index +1;

		selectedValue = paymentTypeString;

		saveProperty(PREFERENCE_PROPERTY,String.valueOf(index));
		
		if ( index > 0) {
			Payment payment = new Payment();
			if ( index == 1)
				payment.setPaymentType(PaymentType.DEBIT_PAYMENT);
			else if ( index == 2)
				payment.setPaymentType(PaymentType.AGENCY_PAYMENT);
			else if ( index == 3)
				payment.setPaymentType(PaymentType.IDEAL_PAYMENT);
		
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			VehicleHttpService service = serviceFactory.getVehicleJoiService();
			service.putPayment(payment);
		}
		
	}

}
