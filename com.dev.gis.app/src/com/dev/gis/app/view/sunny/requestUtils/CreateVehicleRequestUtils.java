package com.dev.gis.app.view.sunny.requestUtils;

import java.util.Calendar;

import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.connector.sunny.Administration;
import com.dev.gis.connector.sunny.Agency;
import com.dev.gis.connector.sunny.DayAndHour;
import com.dev.gis.connector.sunny.Location;
import com.dev.gis.connector.sunny.TravelInformation;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.task.execution.api.ModelProvider;

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
		
		admin.setLanguage(SunnyModelProvider.INSTANCE.languageCode);
		admin.setOperator(ModelProvider.INSTANCE.operatorId);

		admin.setSalesChannel(TaskProperties.SALES_CHANNEL);
		admin.setCalledFrom(5);
		admin.setBroker(false);
		admin.setProviderId(1l);
		admin.setProvider("Internet");
		return admin;
	}



}
