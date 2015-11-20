package com.dev.gis.app.view.listener.adac;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.connector.joi.protocol.Administration;
import com.dev.gis.connector.joi.protocol.DayAndHour;
import com.dev.gis.connector.joi.protocol.Location;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleRequestFilter;

public class AdacCreateVehicleRequestUtils {
	private static Logger logger = Logger.getLogger(AdacCreateVehicleRequestUtils.class);

	public static VehicleRequest createVehicleRequest() {
		
		VehicleRequest request = createRequest();

		Administration admin = createAdministrator();
		
		request.setAdministration(admin);
		return request;
		
	}
	
	
	private static VehicleRequest createRequest() {

		VehicleRequest request = new VehicleRequest();
		TravelInformation ti = new TravelInformation();

		Location pickUpLocation = new Location();
		Location dropOffLocation = new Location();

		String aptCode = StringUtils.trimToEmpty(AdacModelProvider.INSTANCE.airport);
		String dropOffAptCode = StringUtils.trimToEmpty(AdacModelProvider.INSTANCE.dropoffAirport);
		
		long cityId = AdacModelProvider.INSTANCE.cityId;
		long dropoffCityId = AdacModelProvider.INSTANCE.dropoffCityId;
		
		
		
		boolean locationExist = false;
		if (cityId > 0 ) {
			pickUpLocation.setCityId(cityId);
		}
		if (dropoffCityId > 0)
			dropOffLocation.setCityId(dropoffCityId);
		
		if ( StringUtils.isNotEmpty(aptCode)) {
			pickUpLocation.setAirport(aptCode);
		}			
		if ( StringUtils.isNotEmpty(dropOffAptCode))
			dropOffLocation.setAirport(dropOffAptCode);
		
		if ((cityId > 0 || StringUtils.isNotEmpty(aptCode) ) && ( dropoffCityId >0 && StringUtils.isNotEmpty(dropOffAptCode))) {
		    locationExist = true;		    
		}
		
		if ( !locationExist) {
			logger.warn(" NO Location is selected ");
		}
		

		ti.setPickUpLocation(pickUpLocation);
		ti.setDropOffLocation(dropOffLocation);
		
		Calendar pickupDate = ModelProvider.INSTANCE.pickupDateTime;
		Calendar dropoffDate = ModelProvider.INSTANCE.dropoffDateTime;
		
		DayAndHour dh = createDate(pickupDate);
		ti.setPickUpTime(dh);

		dh = createDate(dropoffDate);
		ti.setDropOffTime(dh);

		request.setTravel(ti);

		request.setModule(AdacModelProvider.INSTANCE.module);
		
		request.setPayment(1);
		
		VehicleRequestFilter filter = createFilter();
		if ( filter != null)
			request.setFilter(filter);
		
		if ( !locationExist) {
			createStationLocations(request);
		}
		

		return request;

	}

	private static DayAndHour createDate(Calendar cal) {
		String sday = String.format("%4d-%02d-%02d",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));
		String sTime = String.format("%02d:%02d",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

		DayAndHour dh = new DayAndHour();
		dh.setDate(sday);
		dh.setTime(sTime);
		return dh;
	}
	
	private static Administration createAdministrator() {
		Administration admin = new Administration();
		String lang = "de-DE";
		if ( "EN".equalsIgnoreCase(ModelProvider.INSTANCE.languageCode))
			lang = "en-EN";
		admin.setLanguage(lang);

		admin.setOperator(AdacModelProvider.INSTANCE.operatorId);
		admin.setSalesChannel(TaskProperties.SALES_CHANNEL);
		admin.setCalledFrom(TaskProperties.CALLED_FROM_RENTFOX);
		
		return admin;
	}


	private static VehicleRequestFilter createFilter() {
		VehicleRequestFilter offerFilter = new VehicleRequestFilter();

		String supplierFilter  = AdacModelProvider.INSTANCE.supplierFilter;
		String servcatFilter  = AdacModelProvider.INSTANCE.servcatFilter;
		
		boolean filterEnable = false;
		
		if ( StringUtils.isNotEmpty(supplierFilter)) {
			String[] parts = supplierFilter.split(",");
			Set<Long> suppliers = new HashSet<Long>();
			for ( String part : parts) {
				suppliers.add(Long.parseLong(part));
			}
			offerFilter.setSuppliers(suppliers);
			
			filterEnable = true;;			
		}

		if ( StringUtils.isNotEmpty(servcatFilter)) {
			String[] parts = servcatFilter.split(",");
			Set<Long> servcats = new HashSet<Long>();
			for ( String part : parts) {
				servcats.add(Long.parseLong(part));
			}
			offerFilter.setServiceCatalogs(servcats);
			
			filterEnable = true;;			
			
		}
		if ( filterEnable)
			return offerFilter;
		else
			return null;
	}
	
	private static void createStationLocations(VehicleRequest request) {

		String stationFilter  = AdacModelProvider.INSTANCE.stationFilter;
		Long pickupStationId = null;
		Long dropofStationId = null;
		Long pickupSupplierId = null;
		Long dropoffSupplierId = null;
		if ( StringUtils.isNotEmpty(stationFilter)) {
			String[] parts = stationFilter.split(",");
			
			for ( String part : parts) {
				if(pickupStationId == null )
					pickupStationId =Long.parseLong(part);
				else
					if(dropofStationId == null )
						dropofStationId =Long.parseLong(part);
			}
			if ( dropofStationId == null)
				dropofStationId = pickupStationId;
		}
		
		if ( request.getFilter() != null && request.getFilter().getSuppliers() != null) {
			Set<Long> suppliers = request.getFilter().getSuppliers();
			Long[] lsupp = suppliers.toArray(new Long[0]);
			
			if (lsupp.length > 0)
				pickupSupplierId = lsupp[0];
			if (lsupp.length > 1)
				dropoffSupplierId = lsupp[1];
			if ( dropoffSupplierId == null)
				dropoffSupplierId = pickupSupplierId;
		}
		
		if (pickupStationId != null && pickupSupplierId != null) {
			Location pickUpLocation = new Location();
			pickUpLocation.setStationId(pickupStationId);
			
			Station s = new Station(pickupStationId.intValue());
			s.setSupplierId(pickupSupplierId);
			s.setId(pickupStationId);
			pickUpLocation.setStation(s);
			
			request.getTravel().setPickUpLocation(pickUpLocation);
		}
		
		if ( dropofStationId != null && dropoffSupplierId != null) {
			
			
			Location dropOffLocation = new Location();
			dropOffLocation.setStationId(dropofStationId);
	
			Station s = new Station(dropofStationId.intValue());
			s.setId(dropofStationId);
			s.setSupplierId(dropoffSupplierId);
			dropOffLocation.setStation(s);
			
			request.getTravel().setDropOffLocation(dropOffLocation);
			
		}
		
		
		
		
	}

}
