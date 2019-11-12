package com.dev.gis.app.model;

import java.util.ArrayList;
import java.util.List;

import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.StationResponse;

public class AdacGetStationService implements IStationService{

	private final OfferDo selectedOffer;
	public AdacGetStationService(OfferDo offer) {
		selectedOffer = offer;
	}

	public List<StationModel> executeService(int type, String search, String offerId) {
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
		StationResponse response  = service.getPickupStations(selectedOffer);
		
		
		List<StationModel> stations = new ArrayList<StationModel>();
		
		for ( Station station : response.getStations()) {
			StationModel stationModel = new StationModel(station.getId(), station.getStationName());
			
			stationModel.setSupplier(station.getSupplierId() + ":"+ station.getSupplierGroupId());
			stationModel.setSupplierId(station.getSupplierId());
			stations.add(stationModel);
		}
		
		return stations;
		
	}

	@Override
	public List<StationModel> getDropoffStationService(int type, String search, String offerId, String stationId) {

		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
		
		StationResponse response  = service.getDropoffStations(selectedOffer,stationId);
		
		
		List<StationModel> stations = new ArrayList<StationModel>();
		
		for ( Station station : response.getStations()) {
			StationModel stationModel = new StationModel(station.getId(), station.getStationName());
			
			stationModel.setSupplier(station.getSupplierId() + ":"+ station.getSupplierGroupId());
			stationModel.setStation(station);
			stations.add(stationModel);
		}
		
		return stations;
	}

	@Override
	public List<StationModel> getStations(int locationType, String locationId, String searchText) {
		final long operator = AdacModelProvider.INSTANCE.operatorId;

		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();

		StationResponse response  = service.getStations(operator,locationType,locationId, searchText);

		List<StationModel> stations = new ArrayList<StationModel>();
		
		for ( Station station : response.getStations()) {
			StationModel stationModel = new StationModel(station.getId(), station.getStationName());
			
			stationModel.setSupplier(station.getSupplierId() + ":"+ station.getSupplierGroupId());
			stationModel.setSupplierId(station.getSupplierId());
			stationModel.setStation(station);
			stations.add(stationModel);
		}
		
		return stations;
		
	}

	@Override
	public Station getStation(int stationId) {
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();

		Station response  = service.getStationInfo(stationId);
		
		return response;
	}

}
