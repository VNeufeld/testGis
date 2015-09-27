package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class AdacAirportLocationSearch extends AirportLocationSearch {
	private static Logger logger = Logger.getLogger(AdacAirportLocationSearch.class);
	private boolean isPickup = true;

	private AdacAirportLocationSearch(Composite parent, String label) {
		super(parent, label);
	}

	public static void createPickupAirportLocationSearch(Composite parent) {
		AdacAirportLocationSearch xx = new AdacAirportLocationSearch(parent, "Pickup Airport:");
		xx.isPickup = true;
		xx.create();
	}

	public static void createDropoffAirportLocationSearch(Composite parent) {
		AdacAirportLocationSearch xx = new AdacAirportLocationSearch(parent, "Dropoff Airport :");
		xx.isPickup = false;
		xx.create();
		
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell,
			Text text) {
		SelectionListener selectionListener = new AdacSearchAirportSelectionListener(shell, text, this);
		return selectionListener;
	}
	
	@Override
	public void saveValue(String value) {
		if ( isPickup) {
			AdacModelProvider.INSTANCE.airport = value;
			logger.info("selected pickup airport : "+AdacModelProvider.INSTANCE.airport);
		}
		else {
			AdacModelProvider.INSTANCE.dropoffAirport = value;
			logger.info("selected dropoffAirport airport : "+AdacModelProvider.INSTANCE.dropoffAirport);
		}
		
		if ( isPickup)
			saveProperty("ADAC_PICKUP_APT", value);
		else
			saveProperty("ADAC_DROPOFF_APT", value);
		
	}

	@Override
	protected String getDefaultValue() {
		String apt = "PMI";
		if ( isPickup)
			apt = readProperty("ADAC_PICKUP_APT");
		else
			apt = readProperty("ADAC_DROPOFF_APT");
		return apt;
		
	}

}
