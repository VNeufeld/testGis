package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.SunnyModelProvider;

public class OperatorComboBox extends ObjectsComboBox{

	private static final String PREFERENCE_PROPERTY = "SUNNY_OPERATOR_PROPERTY";

	private static Logger logger = Logger.getLogger(OperatorComboBox.class);
	
	private final static String items[] = { "deu ( 1 )", "CH ( 29118) ", " NL (26896 )" };

	private final static String values[] = { "1", "29118", "26896" };
	
	
	public OperatorComboBox(Composite parent, int size) {
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
		SunnyModelProvider.INSTANCE.operatorId = Long.valueOf(op);
		saveProperty(PREFERENCE_PROPERTY,String.valueOf(index));
		
	}

}
