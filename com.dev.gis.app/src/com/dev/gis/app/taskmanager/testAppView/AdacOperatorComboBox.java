package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectsComboBox;
import com.dev.gis.connector.api.AdacModelProvider;

public class AdacOperatorComboBox extends ObjectsComboBox{
	private static final String PREFERENCE_PROPERTY = "ADAC_OPERATOR_PROPERTY";

	private static Logger logger = Logger.getLogger(AdacOperatorComboBox.class);
	
	private final static String items[] = { "deu ( 1 )", "Int (152573) "};

	private final static String values[] = { "1", "152573" };
	
	
	public AdacOperatorComboBox(Composite parent, int size) {
		super(parent, size, true);
	}

	@Override
	protected String[] getItems() {
		return items;
	}
	
	protected String getPropertyName() {
		return PREFERENCE_PROPERTY;
	}


	@Override
	protected String getLabel() {
		return "Operator";
	}

	@Override
	protected void saveSelected(int index, String st) {
		String op = values[index];
		selectedValue = op;
		AdacModelProvider.INSTANCE.operatorId = Long.valueOf(op);
		saveProperty(PREFERENCE_PROPERTY,String.valueOf(index));
		
	}

	@Override
	protected void logSelectedValue(int index) {
		logger.info("select Operator "+ selectedValue);
	}


}
