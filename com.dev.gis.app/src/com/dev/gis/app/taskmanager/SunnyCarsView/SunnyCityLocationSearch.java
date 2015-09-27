package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyCityLocationSearch extends CityLocationSearch {

	private static Logger logger = Logger.getLogger(SunnyCityLocationSearch.class);
	private boolean isPickup = true;

	public static void createPickupCityLocationSearch(Composite parent) {
		new SunnyCityLocationSearch(parent,"Pickup City").create();
	}

	public static void createDropoffCityLocationSearch(Composite parent) {
		SunnyCityLocationSearch bb = new SunnyCityLocationSearch(parent,"Dropoff City");
		bb.isPickup = false;
		bb.create();
	}
	
	private SunnyCityLocationSearch(Composite parent, String label) {
		super(parent, label);

	}

	@Override
	protected String getLabel() {
		return "Search";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell, Text text) {
		SelectionListener selectionListener = new SunnySearchCitySelectionListener(
				shell, text, this);
		return selectionListener;
	}

	@Override
	public void saveValue(String value) {
		if (!StringUtils.isEmpty(value) && StringUtils.isNumeric(value)) {
			if ( isPickup ) {
				SunnyModelProvider.INSTANCE.cityId = Long.valueOf(value);
			}
			else {
				SunnyModelProvider.INSTANCE.dropoffCityId = Long.valueOf(value);
			}

		}
		else {
			if ( isPickup ) {
				SunnyModelProvider.INSTANCE.cityId = 0;
			}
			else {
				SunnyModelProvider.INSTANCE.dropoffCityId = 0;
			}
		}
		
		if ( isPickup ) {
			logger.info("selected pickup city : " + SunnyModelProvider.INSTANCE.cityId);
			saveProperty("SUNNY_PICKUP_CITY", value);
		}
		else {
			logger.info("selected dropoff city : " + SunnyModelProvider.INSTANCE.dropoffCityId);
			saveProperty("SUNNY_DROPOFF_CITY", value);
		}

	}
	
	@Override
	protected String getDefaultValue() {
		String city = "1234"; 
		if ( isPickup ) 
			city = readProperty("SUNNY_PICKUP_CITY");
		
		else
			city = readProperty("SUNNY_DROPOFF_CITY");
		return city;
	}


}
