package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.connector.api.SunnyModelProvider;

public class SunnyAirportLocationSearch extends AirportLocationSearch {
	private static Logger logger = Logger.getLogger(SunnyAirportLocationSearch.class);
	private boolean isPickup = true;

	private SunnyAirportLocationSearch(Composite parent, String label) {
		super(parent, label);

	}

	public static void createPickupAirportLocationSearch(Composite parent) {
		new SunnyAirportLocationSearch(parent, "Pickup :").create();;
		
	}

	public static void createDropoffAirportLocationSearch(Composite parent) {
		SunnyAirportLocationSearch xx = new SunnyAirportLocationSearch(parent, "Dropoff :");
		xx.isPickup = false;
		xx.create();
	}
	

	@Override
	protected String getLabel() {
		return "Search Sunny Airport";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell,
			Text text) {
		SelectionListener selectionListener = new SunnySearchAirportSelectionListener(shell, text, this);
		return selectionListener;
	}
	
	@Override
	public void saveValue(String value) {
		if ( isPickup) {
			SunnyModelProvider.INSTANCE.airport = value;
			logger.info("selected pickup airport : "+SunnyModelProvider.INSTANCE.airport);
		}
		else {
			SunnyModelProvider.INSTANCE.dropoffAirport = value;
			logger.info("selected dropoffAirport airport : "+SunnyModelProvider.INSTANCE.dropoffAirport);
		}
		
		if ( isPickup)
			saveProperty("SUNNY_PICKUP_APT", value);
		else
			saveProperty("SUNNY_DROPOFF_APT", value);
		
	}

	@Override
	protected String getDefaultValue() {
		String apt = "PMI";
		if ( isPickup)
			apt = readProperty("SUNNY_PICKUP_APT");
		else
			apt = readProperty("SUNNY_DROPOFF_APT");
		return apt;
	}

}
