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

		String aptCode = ModelProvider.INSTANCE.airport;
		long cityId = ModelProvider.INSTANCE.cityId;
		
		if (cityId > 0 ) {
			pickUpLocation.setCityId(cityId);
		    dropOffLocation.setCityId(cityId);
		}
		else {
			pickUpLocation.setAirport(aptCode);
			dropOffLocation.setAirport(aptCode);
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

		// if ( buttonTruck.getSelection())
		// request.setModule(2);
		// else
		// request.setModule(1);
		// request.setPayment(PayType.PREPAID);
		
		OfferFilter offerFilter = new OfferFilter();
		String supplierFilter  = SunnyModelProvider.INSTANCE.supplierFilter;
		if ( StringUtils.isNotEmpty(supplierFilter)) {
			String[] parts = supplierFilter.split(",");
			List<Long> suppliers = new ArrayList<Long>();
			for ( String part : parts) {
				suppliers.add(Long.parseLong(part));
			}
			Long[] lsupp = suppliers.toArray(new Long[0]);
			offerFilter.setSuppliers(lsupp);
		}

		String servcatFilter  = SunnyModelProvider.INSTANCE.servcatFilter;
		if ( StringUtils.isNotEmpty(servcatFilter)) {
			String[] parts = servcatFilter.split(",");
			List<Long> servcats = new ArrayList<Long>();
			for ( String part : parts) {
				servcats.add(Long.parseLong(part));
			}
			Long[] lsupp = servcats.toArray(new Long[0]);
			offerFilter.setServiceCatalogId(lsupp[0]);
		}
		
		request.setFilter(offerFilter);

		return request;

	}

	private static DayAndHour createDate(Calendar cal) {
		String sday = String.format("%4d-%02d-%02d",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));
		String sTime = String.format("%02d:%02d",
				cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));

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
