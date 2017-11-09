package com.dev.gis.app.model;

import java.util.ArrayList;
import java.util.List;

import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.StationResponse;

public class GetStationService implements IStationService{

	public List<StationModel> executeService(int type, String search, String offerId) {
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		VehicleHttpService service = serviceFactory.getVehicleJoiService();

		StationResponse response = service.getPickupStations(type,search, offerId);
		
		List<StationModel> stations = new ArrayList<StationModel>();
		
		for ( Station station : response.getStations()) {
			StationModel stationModel = new StationModel(station.getId(), station.getIdentifier());
			
			stationModel.setSupplier(station.getSupplierId() + ":"+ station.getSupplierGroupId());
			stations.add(stationModel);
		}
		
		return stations;
		
	}

	public List<StationModel> getDropoffStationService(int type, String search, String offerId, String stationId) {
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		VehicleHttpService service = serviceFactory.getVehicleJoiService();

		StationResponse response = service.getDropOffStations(type,search, offerId, stationId);
		
		List<StationModel> stations = new ArrayList<StationModel>();
		
		for ( Station station : response.getStations()) {
			StationModel stationModel = new StationModel(station.getId(), station.getIdentifier());
			
			stationModel.setSupplier(station.getSupplierId() + ":"+ station.getSupplierGroupId());
			stations.add(stationModel);
		}
		
		return stations;
		
	}


	@Override
	public com.dev.gis.connector.joi.protocol.Station getStation(int station_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StationModel> getStations(int locationType, String locationId, String searchText) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
