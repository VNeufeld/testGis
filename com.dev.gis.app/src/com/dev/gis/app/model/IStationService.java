package com.dev.gis.app.model;

import java.util.List;

import com.dev.gis.connector.joi.protocol.Station;

public interface IStationService {
	
	public List<StationModel> executeService(int type, String search, String offerId);
	
	public List<StationModel> getDropoffStationService(int type, String search, String offerId, String stationId);

	public List<StationModel> getStations(int locationType, String locationId, String searchText);

	public Station getStation(int station_id);
	
}
