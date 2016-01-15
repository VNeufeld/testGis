package com.dev.gis.app.model;

import java.util.List;

public interface IStationService {
	
	public List<StationModel> executeService(int type, String search, String offerId);
	
	public List<StationModel> getDropoffStationService(int type, String search, String offerId, String stationId);

}
