package com.dev.gis.app.taskmanager.clubMobilView;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;

import com.bpcs.mdcars.json.protocol.CheckOutRequest;
import com.bpcs.mdcars.json.protocol.DispositionListResponse;
import com.bpcs.mdcars.model.ServiceError;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Error;
import com.dev.gis.connector.joi.protocol.Response;
import com.dev.gis.connector.joi.protocol.VehicleDescription;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClubMobilUtils {
	
	public static void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = error.getErrorText()+ "\n";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				null,"Error",message);
		
	}

	public static  void showErrors(String message) {
		MessageDialog.openError(
				null,"Error",message);
		
	}


	public static <T> T convertJsonStringToObject(String jsonString, Class<T> claszz)  throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.readValue(jsonString, claszz);
		return result;
	}

	public static void showErrors(Response response) {
		String message = "Error : ";
		boolean isError = false;
		if ( response.getErrors() != null) {
			for ( Error error : response.getErrors()) {
				message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
				isError = true;
			}
			
		}
		if (isError ) {
			MessageDialog.openError(
					null,"Error",message);
			
		}
		
	}

	public static void showErrors(DispositionListResponse response) {
		String message = "Error : ";
		boolean isError = false;
		if ( response.getErrors() != null) {
			for ( ServiceError error : response.getErrors()) {
				message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
				isError = true;
			}
			
		}
		if (isError ) {
			MessageDialog.openError(
					null,"Error",message);
			
		}
		
	}

	public static void showErrors(ServiceError serviceError) {
		String message = "Error : ";
		message = message + serviceError.getErrorNumber()+ "  "+ serviceError.getErrorText() + " " + serviceError.getErrorType() + " ;";
			MessageDialog.openError(
					null,"Error",message);
		
	}
	
	public static CheckOutRequest createCheckOutRequest() {
		CheckOutRequest checkOutRequest = new CheckOutRequest();
		if ( ClubMobilModelProvider.INSTANCE.getSelectedOffer() != null ) {
			com.dev.gis.connector.joi.protocol.Offer offer = ClubMobilModelProvider.INSTANCE.getSelectedOffer().getOffer();
			VehicleDescription vehicle = ClubMobilModelProvider.INSTANCE.getSelectedOffer().getModel().getVehicle();
			if ( vehicle != null && StringUtils.isNotEmpty(vehicle.getCarId())) {
				checkOutRequest.setCarId(Integer.valueOf(vehicle.getCarId()));
			}
			checkOutRequest.setBusinessSegmentId((int)offer.getBusinessSegmentId());
			offer.getPrice();
			
		}
		if ( ClubMobilModelProvider.INSTANCE.selectedReservation != null) {
			checkOutRequest.setCheckOutDate(ClubMobilModelProvider.INSTANCE.selectedReservation.getCheckOutDate());
			checkOutRequest.setCheckInDate(ClubMobilModelProvider.INSTANCE.selectedReservation.getCheckInDate());
			checkOutRequest.setCheckOutStationId(ClubMobilModelProvider.INSTANCE.selectedReservation.getCheckOutStationId());
			checkOutRequest.setCheckInStationId(ClubMobilModelProvider.INSTANCE.selectedReservation.getCheckInStationId());

			checkOutRequest.setReservationNo(ClubMobilModelProvider.INSTANCE.selectedReservation.getReservationNo());
			
		}

		return checkOutRequest;
	}

	
}
