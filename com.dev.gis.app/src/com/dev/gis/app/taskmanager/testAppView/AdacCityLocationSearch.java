package com.dev.gis.app.taskmanager.testAppView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;

public class AdacCityLocationSearch extends CityLocationSearch {

	private static Logger logger = Logger.getLogger(AdacCityLocationSearch.class);
	private boolean isPickup = true;
	

	public static void createPickupCityLocationSearch(Composite parent) {
		AdacCityLocationSearch xx = new AdacCityLocationSearch(parent,"Pickup City : ");
		xx.isPickup = true;
		xx.create();
		
	}

	public static void createDropoffCityLocationSearch(Composite parent) {
		AdacCityLocationSearch xx = new AdacCityLocationSearch(parent,"Dropoff City : ");
		xx.isPickup = false;
		xx.create();
		
	}
	
	private AdacCityLocationSearch(Composite parent, String label) {
		super(parent, label);

	}


	@Override
	protected SelectionListener getSelectionListener(Shell shell, Text text) {
		SelectionListener selectionListener = new AdacSearchCitySelectionListener(
				shell, text, this);
		return selectionListener;
	}

	@Override
	public void saveValue(String value) {
		
		if (!StringUtils.isEmpty(value) && StringUtils.isNumeric(value)) {
			if ( isPickup ) {
				AdacModelProvider.INSTANCE.cityId = Long.valueOf(value);
			}
			else {
				AdacModelProvider.INSTANCE.dropoffCityId = Long.valueOf(value);
			}

		}
		else {
			if ( isPickup ) {
				AdacModelProvider.INSTANCE.cityId = 0;
			}
			else {
				AdacModelProvider.INSTANCE.dropoffCityId = 0;
			}
		}
		
		if ( isPickup ) {
			logger.info("selected pickup city : " + AdacModelProvider.INSTANCE.cityId);
			saveProperty("ADAC_PICKUP_CITY", value);
		}
		else {
			logger.info("selected dropoff city : " + AdacModelProvider.INSTANCE.dropoffCityId);
			saveProperty("ADAC_DROPOFF_CITY", value);
		}

	}
	@Override
	protected String getDefaultValue() {
		String city = "1234"; 
		if ( isPickup ) 
			city = readProperty("ADAC_PICKUP_CITY");
		
		else
			city = readProperty("ADAC_DROPOFF_CITY");
		return city;
	}

}
