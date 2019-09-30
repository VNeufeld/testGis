package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import com.bpcs.mdcars.json.protocol.CheckOutRequest;
import com.dev.gis.app.taskmanager.clubMobilView.ClubMobilUtils;
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class CheckOutReservationListener extends AbstractReservationListener{
	private final Shell shell;

	public CheckOutReservationListener(Shell shell) {
		this.shell = shell;
	}
	
	protected void callService(ClubMobilHttpService service) {
		CheckOutRequest checkOutRequest = ClubMobilUtils.createCheckOutRequest();
		ClubMobilModelProvider.INSTANCE.getSelectedOffer();
		checkOutRequest.setRentalNo("X");
		logger.info("call checkOutRequest " + checkOutRequest);
		service.checkOutReservation(checkOutRequest);
		MessageDialog.openInformation(null,"Info"," CheckOut successfull");
	}
	
	protected void executeEvent(SelectionEvent e) {
		CheckOutDialog mpd = new CheckOutDialog(shell);
		if (mpd.open() == Dialog.OK) {
			executeRestService();
			
		}

	}

}
