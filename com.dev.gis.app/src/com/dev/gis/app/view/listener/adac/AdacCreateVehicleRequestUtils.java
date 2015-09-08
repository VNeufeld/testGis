package com.dev.gis.app.view.listener.adac;

import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.connector.joi.protocol.Administration;
import com.dev.gis.connector.joi.protocol.DayAndHour;
import com.dev.gis.connector.joi.protocol.Location;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;

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
		
		if ( cityId <= 0 && StringUtils.isEmpty(aptCode)) {
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



}
