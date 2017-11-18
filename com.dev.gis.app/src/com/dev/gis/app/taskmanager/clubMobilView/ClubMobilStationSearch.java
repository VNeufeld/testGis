package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.model.AdacGetStationService;
import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.joi.protocol.Station;

public class ClubMobilStationSearch extends CityLocationSearch {

	private static Logger logger = Logger.getLogger(ClubMobilStationSearch.class);
	
	private static final String PREFERENCE_PROPERTY_PICKUP = "CM_PICKUP_STATION";
	private static final String PREFERENCE_PROPERTY_DROPOFF = "CM_DROPOFF_STATION";
	
	
	private boolean isPickup = true;
	private Text text;


	public static void createPickupCityLocationSearch(Composite parent) {
		ClubMobilStationSearch xx = new ClubMobilStationSearch(parent,"Pickup Station : ");
		xx.isPickup = true;
		xx.create();
		
	}

	public static void createDropoffCityLocationSearch(Composite parent) {
		ClubMobilStationSearch xx = new ClubMobilStationSearch(parent,"DropoffStation : ");
		xx.isPickup = false;
		xx.create();
		
	}
	
	private ClubMobilStationSearch(Composite parent, String label) {
		super(parent, label);

	}
	
	
	protected void create() {
		Composite parent = getParent();
		int size = 1600;
		boolean spannAll = true;
		
		String label = searchLabel;

		new Label(parent, SWT.NONE).setText(label);
		
		GridLayout gd = (GridLayout)parent.getLayout();
		int col = gd.numColumns;
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		
		if ( spannAll)
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).span(col-1, 1).hint(size, 28).applyTo(composite);
		else
			GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
			.grab(false, false).hint(size, 24).applyTo(composite);

		text = new Text(composite, SWT.BORDER | SWT.SINGLE);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false).hint(600, 24).applyTo(text);

		Button buttonSearch = new Button(composite, SWT.PUSH | SWT.LEFT);
		buttonSearch.setText("Station Search");
		buttonSearch.addSelectionListener(getSelectionListener(parent.getShell(), text));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING)
		.grab(false, true).applyTo(buttonSearch);

		text.setText(getDefaultValue());
		
		//saveValue(text.getText());
		
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				if (arg0.character == '\r') {
					
					String value = text.getText();
					saveValue(value);
				}
			}


			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		text.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				String value = text.getText();
				saveValue(value);
			}
		});
		
	}


	@Override
	protected SelectionListener getSelectionListener(Shell shell, Text text) {
		SelectionListener selectionListener = new ClubMobilSearchStationsSelectionListener(
				shell, text, this);
		return selectionListener;
	}

	@Override
	public void saveValue(String value) {
		logger.info("saveValue : " +value);

		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value)) {


			if ( isPickup ) {
				logger.info("selected pickup station : " + value);
				saveProperty(PREFERENCE_PROPERTY_PICKUP, value);
			}
			else {
				logger.info("selected dropoff station : " + value);
				saveProperty(PREFERENCE_PROPERTY_DROPOFF, value);
			}
			
			String stationId = value;
			stationId = setStation(stationId);
			text.setText(stationId);
		}

	}
	@Override
	protected String getDefaultValue() {
		String station_id = "0001"; 
		if ( isPickup ) 
			station_id = readProperty(PREFERENCE_PROPERTY_PICKUP);
		else
			station_id = readProperty(PREFERENCE_PROPERTY_DROPOFF);

		if ( isPickup)
			logger.info("saved pickup station_id "+ station_id );
		else
			logger.info("saved dropoff station_id "+ station_id );
		
		station_id = setStation(station_id);

		return station_id;
		
	}

	private String setStation(String station_id) {
		IStationService stationService = new AdacGetStationService(null);
		try {
			if (StringUtils.isNotEmpty(station_id) && StringUtils.isNumeric(station_id)) {
				int stationId = Integer.parseInt(station_id);
				Station station= stationService.getStation(stationId);
				if ( station != null) {
					if ( isPickup )
						ClubMobilModelProvider.INSTANCE.pickupStation = station;
					else
						ClubMobilModelProvider.INSTANCE.dropoffStation = station;

					station_id = station.getId()+ " "+station.getStationName();
					
					logger.info("save station "+ station_id );
					
				}
			}
		}
		catch(Exception ex) {
			logger.info(ex.getMessage(),ex);
		}
		return station_id;
	}

	public boolean isPickup() {
		return isPickup;
	}

}
