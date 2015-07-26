package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.task.execution.api.ModelProvider;

public class OperatorComboBox extends ObjectsComboBox{
	
	private static Logger logger = Logger.getLogger(OperatorComboBox.class);
	
	private final static String items[] = { "deu ( 1 )", "int ( 158888) ", " sunny (56 )" };

	private final static String values[] = { "1", "158888", "56" };
	
	
	public OperatorComboBox(Composite parent, int size) {
		super(parent, size);
	}

	@Override
	protected String[] getItems() {
		return items;
	}

	public final String getOperator() {
		return selectedValue;
	}
	
	public final long getOperatorId() {
		if ( selectedValue != null)
			return Long.valueOf(selectedValue);
		else 
			return 0;
	}
	

	@Override
	protected int getSavedSelectedId() {
		Long opid = TaskProperties.getTaskProperties().getOperator();
		if ( opid != null) 
			return opid.intValue();
		return 0;
	}


	@Override
	protected String getSelectedValue(int index, String st) {
		String op = values[index];

		ModelProvider.INSTANCE.operatorId = Long.valueOf(op);
		return op;
	}

	@Override
	protected void saveProperties(int index) {
		TaskProperties.getTaskProperties().setOperator((long)index);
	}

	@Override
	protected String getLabel() {
		return "Operator";
	}



}
