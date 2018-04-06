package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.model.ReservationInfo;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilReservationView;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.api.JoiHttpServiceFactory;


public class ClubMobilSelectReservationDoubleClickListener implements IDoubleClickListener {
	
	private static Logger logger = Logger.getLogger(ClubMobilSelectReservationDoubleClickListener.class);
	
	private final Shell shell;
	
	public ClubMobilSelectReservationDoubleClickListener(Shell shell) {
		super();
		logger.info("create ClubMobilSelectReservationDoubleClickListener. ");
		this.shell = shell;

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
			ReservationResponse reservationResponse = service.getReservation(reservation.getReservationNo());
			logger.info("select reservation " + reservationResponse.getReservationDetails().getReservationNo());
			ClubMobilModelProvider.INSTANCE.selectedReservation = reservationResponse.getReservationDetails();
			
			CheckOutDialog mpd = new CheckOutDialog(shell, null);
			if (mpd.open() == Dialog.OK) {
				executeRestService();
			}
			ClubMobilReservationView.updateCustomerControl(" selected reservation ");

		}
		catch(Exception err) {
			showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
		}			
		
	}
	
	protected void executeRestService() {
		try {
			JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
			ClubMobilHttpService service = serviceFactory.getClubMobilleJoiService();
			ReservationResponse reservationResponse = service.checkOutReservation();
			ClubMobilModelProvider.INSTANCE.selectedReservation = reservationResponse.getReservationDetails();
			MessageDialog.openInformation(null,"Info"," CheckOut successfull");
			
		}
		catch(Exception err) {
			ClubMobilUtils.showErrors(new com.dev.gis.connector.sunny.Error(1,1, err.getMessage()));
			
		}

	}


	protected void showErrors(com.dev.gis.connector.sunny.Error error) {
		
		String message = "";
		message = message + error.getErrorNumber()+ "  "+ error.getErrorText() + " " + error.getErrorType() + " ;";
		MessageDialog.openError(
				null,"Error",message);
		
	}

}
