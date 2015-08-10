package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.sunny.HitType;

public class SelectHitTypeComboBox extends ObjectsComboBox{
	
	private static Logger logger = Logger.getLogger(SelectHitTypeComboBox.class);

	private final static String items[] = { "","All", "City", "Airport","City + Airports", "Country" };
	
	private final static String values[] = { "0", "1", "2", "3", "4","5" };

	public SelectHitTypeComboBox(Composite parent, int size) {
		super(parent, size, false);
	}


	@Override
	protected String getLabel() {
		return "filter :";
	}
	protected String getPropertyName() {
		return "NO";
	}

	@Override
	protected String[] getItems() {
		return items;
	}

	@Override
	protected void saveSelected(int index, String st) {
		String op = values[index];
		selectedValue = op;
		
	}
	
	public HitType getHitType() {
		int filterIndex = Integer.valueOf(selectedValue);
		HitType type = HitType.UNINITIALIZED;
		if ( filterIndex == 2)
			type = HitType.CITY;
		if ( filterIndex == 3)
			type = HitType.AIRPORT;
		if ( filterIndex == 5)
			type = HitType.COUNTRY;

		if ( filterIndex == 4)
			type = HitType.RAILWAY_STATION;
		
		return type;

	}


	@Override
	protected int getSavedSelectedId() {
		return 2;
	}

}
