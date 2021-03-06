package com.dev.gis.app.taskmanager.clubMobilView;

import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.ObjectsComboBox;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.TaskProperties;

public class ClubMobilOperatorComboBox extends ObjectsComboBox{
	private static final String PREFERENCE_PROPERTY = "CLUBMOBIL_OPERATOR_PROPERTY";

	private static Logger logger = Logger.getLogger(ClubMobilOperatorComboBox.class);
	
	private static Map<String,String> operators = new java.util.TreeMap<String,String>();
	
	static {
		createOperator();
	}
	
	public static void createOperator() {
		String ops = ""; //TaskProperties.getTaskProperties().getAdacOperators();
		logger.info("create operator list "+ops);
		operators.clear();
		
		if ( ops.isEmpty()) {
			ops = "DE:152573";
		}
		if ( !ops.isEmpty())
		{
			String[] opp = ops.split(";");
			for ( String op:opp) {
				String[] opd = op.split(":");
				if (opd.length > 1 ) {
					operators.put(opd[0], opd[1].trim());
				}
				else
					operators.put(opd[0], opd[0].trim());
			}
		}
		if ( operators.isEmpty() ) {
			operators.put("Default Operator(1)","1");
		}
		
	}
	
	
	public ClubMobilOperatorComboBox(Composite parent, int size) {
		super(parent, size, false);
		
	}

	@Override
	protected String[] getItems() {
		return operators.keySet().toArray(new String[0]);
//		return items;
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
		//String op = values[index];
		String op = operators.get(st);
		selectedValue = op;
		ClubMobilModelProvider.INSTANCE.operatorId = Long.valueOf(op);
		saveProperty(PREFERENCE_PROPERTY,String.valueOf(index));
		
	}

	@Override
	protected void logSelectedValue(int index) {
		logger.info("select Operator "+ selectedValue);
	}


}
