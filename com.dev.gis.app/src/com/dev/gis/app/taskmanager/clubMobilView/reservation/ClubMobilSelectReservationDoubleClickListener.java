package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationView;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;


public class ClubMobilSelectReservationDoubleClickListener implements IDoubleClickListener {
	
	private static Logger logger = Logger.getLogger(ClubMobilSelectReservationDoubleClickListener.class);
	
	public ClubMobilSelectReservationDoubleClickListener() {
		super();
		logger.info("create ClubMobilSelectReservationDoubleClickListener. ");

	}


	@Override
	public void doubleClick(DoubleClickEvent event) {

		TableViewer viewer = (TableViewer) event.getViewer();
		IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
		
		Object selectedNode = thisSelection.getFirstElement();

		ReservationInfo reservation = (ReservationInfo) selectedNode;

		logger.info("selectedNode " + reservation.getReservationNo());
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();

		try {
			ReservationResponse reservationResponse = service.getReservation(reservation.getRentalId());
			logger.info("select reservation " + reservationResponse.getReservationDetails().getRentalId());
			ClubMobilModelProvider.INSTANCE.selectedReservation = reservationResponse.getReservationDetails();
			ClubMobilReservationView.updateCustomerControl(" selected reservation ");

		}
		catch(Exception err) {
			showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
		}			
		
	}

	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				null,"Error",message);
		
	}

}
