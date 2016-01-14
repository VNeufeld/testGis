package com.dev.gis.app.view.sunny.requestUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.connector.sunny.Administration;
import com.dev.gis.connector.sunny.Agency;
import com.dev.gis.connector.sunny.DayAndHour;
import com.dev.gis.connector.sunny.Location;
import com.dev.gis.connector.sunny.OfferFilter;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.TravelInformation;
import com.dev.gis.connector.sunny.VehicleRequest;

public class CreateVehicleRequestUtils {

	public static VehicleRequest createVehicleRequest() {
		
		VehicleRequest request = createRequest();

		Administration admin = createAdministrator();
		
		request.setAdministration(admin);
		
		Agency agency = new Agency();
		agency.setAgencyNo(SunnyModelProvider.INSTANCE.agencyNo);
		request.setAgency(agency);
		return request;
		
		
	}
	
	
	private static VehicleRequest createRequest() {

		VehicleRequest request = new VehicleRequest();
		TravelInformation ti = new TravelInformation();
		
		

		Location pickUpLocation = new Location();
		Location dropOffLocation = new Location();

		String aptCode = StringUtils.trimToEmpty(SunnyModelProvider.INSTANCE.airport);
		String dropOffAptCode = StringUtils.trimToEmpty(SunnyModelProvider.INSTANCE.dropoffAirport);
		
		long cityId = SunnyModelProvider.INSTANCE.cityId;
		long dropoffCityId = SunnyModelProvider.INSTANCE.dropoffCityId;
		
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
		
		ti.setPickUpLocation(pickUpLocation);
		ti.setDropOffLocation(dropOffLocation);
		
		Calendar pickupDate = ModelProvider.INSTANCE.pickupDateTime;
		Calendar dropoffDate = ModelProvider.INSTANCE.dropoffDateTime;
		
		DayAndHour dh = createDate(pickupDate);
		ti.setPickUpTime(dh);

		dh = createDate(dropoffDate);
		ti.setDropOffTime(dh);

		request.setTravel(ti);
		
		OfferFilter offerFilter = createFilter();
		if (offerFilter != null )
			request.setFilter(offerFilter);
		
		
		if ( !locationExist) {
			createStationLocations(request);
		}
		

		return request;

	}

	private static void createStationLocations(VehicleRequest request) {

		String stationFilter  = SunnyModelProvider.INSTANCE.stationFilter;
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
			Long[] suppliers = request.getFilter().getSuppliers();
			if (suppliers.length > 0)
				pickupSupplierId = suppliers[0];
			if (suppliers.length > 1)
				dropoffSupplierId = suppliers[1];
			if ( dropoffSupplierId == null)
				dropoffSupplierId = pickupSupplierId;
		}
		
		if (pickupStationId != null && pickupSupplierId != null) {
			Location pickUpLocation = new Location();
			pickUpLocation.setStationId(pickupStationId);
			
			Station s = new Station(pickupStationId);
			s.setSupplierId(pickupSupplierId);
			s.setId(pickupStationId);
			pickUpLocation.setStation(s);
			
			request.getTravel().setPickUpLocation(pickUpLocation);
		}
		
		if ( dropofStationId != null && dropoffSupplierId != null) {
			
			
			Location dropOffLocation = new Location();
			dropOffLocation.setStationId(dropofStationId);
	
			Station s = new Station(dropofStationId);
			s.setId(dropofStationId);
			s.setSupplierId(dropoffSupplierId);
			dropOffLocation.setStation(s);
			
			request.getTravel().setDropOffLocation(dropOffLocation);
			
		}
		
		
		
		
	}


	private static OfferFilter createFilter() {
		OfferFilter offerFilter = new OfferFilter();

		String supplierFilter  = SunnyModelProvider.INSTANCE.supplierFilter;
		String servcatFilter  = SunnyModelProvider.INSTANCE.servcatFilter;
		
		boolean filterEnable = false;
		
		if ( SunnyModelProvider.INSTANCE.onlyAirportFlag || SunnyModelProvider.INSTANCE.onlyCityFlag) {
			List<String> list = new ArrayList<String>();
			if ( SunnyModelProvider.INSTANCE.onlyAirportFlag )
				list.add("APT");
			if ( SunnyModelProvider.INSTANCE.onlyCityFlag )
				list.add("CTY");
			if ( list.size() > 0) 
				offerFilter.setStationLocTypeCodes(list.toArray(new String[0]));
			filterEnable = true;;			
		}

		
		if ( StringUtils.isNotEmpty(supplierFilter)) {
			String[] parts = supplierFilter.split(",");
			List<Long> suppliers = new ArrayList<Long>();
			for ( String part : parts) {
				suppliers.add(Long.parseLong(part));
			}
			Long[] lsupp = suppliers.toArray(new Long[0]);
			offerFilter.setSuppliers(lsupp);
			
			filterEnable = true;;			
		}

		if ( StringUtils.isNotEmpty(servcatFilter)) {
			String[] parts = servcatFilter.split(",");
			List<Long> servcats = new ArrayList<Long>();
			for ( String part : parts) {
				servcats.add(Long.parseLong(part));
			}
			Long[] lsupp = servcats.toArray(new Long[0]);
			offerFilter.setServiceCatalogId(lsupp[0]);
			
			filterEnable = true;;			
			
		}
		if ( filterEnable)
			return offerFilter;
		else
			return null;
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
		
		admin.setLanguage(ModelProvider.INSTANCE.languageCode);
		admin.setOperator(SunnyModelProvider.INSTANCE.operatorId);

		admin.setSalesChannel(TaskProperties.SALES_CHANNEL);
		admin.setCalledFrom(5);
		admin.setBroker(false);
		admin.setProviderId(1l);
		admin.setProvider("Internet");
		return admin;
	}



}
