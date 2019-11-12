package com.dev.gis.app.taskmanager.clubMobilView.dispo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilStationSearch;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.Station;

public class ClubMobilDispoStationSearch extends ClubMobilStationSearch {

	private static Logger logger = Logger.getLogger(ClubMobilDispoStationSearch.class);
	
	private static final String PREFERENCE_PROPERTY_DISPO_STATION = "CM_DISPO_STATION";
	


	public static void createDispoStationSearch(Composite parent) {
		ClubMobilDispoStationSearch xx = new ClubMobilDispoStationSearch(parent,"Station : ");
		xx.create();
	}
	
	private ClubMobilDispoStationSearch(Composite parent, String label) {
		super(parent, label);
	}

	@Override
	public void saveValue(String value) {
		logger.info("saveValue : " +value);

		if (StringUtils.isNotEmpty(value) && StringUtils.isNumeric(value)) {

			logger.info("selected dispo station : " + value);
			saveProperty(PREFERENCE_PROPERTY_DISPO_STATION, value);
			
			String stationId = value;
			stationId = setStation(stationId);
			text.setText(stationId);
		}

	}
	@Override
	protected String getDefaultValue() {
		String station_id = "0001"; 
		station_id = readProperty(PREFERENCE_PROPERTY_DISPO_STATION);
		logger.info("saved dispo station_id "+ station_id );
		
		station_id = setStation(station_id);

		return station_id;
		
	}

	private String setStation(String station_id) {
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
		try {
			if (StringUtils.isNotEmpty(station_id) && StringUtils.isNumeric(station_id)) {
				int stationId = Integer.parseInt(station_id);
				Station station= service.getStationInfo(stationId);
				if ( station != null) {
					ClubMobilModelProvider.INSTANCE.dispoStation = station;

					station_id = station.getId()+ " "+station.getStationName();
					
					logger.info("save dispo station "+ station_id );
					
				}
			}
		}
		catch(Exception ex) {
			logger.info(ex.getMessage(),ex);
		}
		return station_id;
	}

}
