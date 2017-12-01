package com.dev.gis.app.taskmanager.clubMobilView;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;

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

}
