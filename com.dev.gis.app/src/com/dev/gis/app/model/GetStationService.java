package com.dev.gis.app.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;

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

}
