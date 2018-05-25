package com.dev.gis.app.taskmanager.clubMobilView.reservation;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.connector.api.ClubMobilHttpService;

public class CheckInReservationListener extends AbstractReservationListener{
	private final Shell shell;
	private ClubMobilReservationListControl clubMobilResControl;

	public CheckInReservationListener(Shell shell, ClubMobilReservationListControl clubMobilResControl) {
		this.shell = shell;
		this.clubMobilResControl = clubMobilResControl;
	}
	
	protected void callService(ClubMobilHttpService service) {
		service.checkInReservation();
		MessageDialog.openInformation(null,"Info"," CheckIn successfull");
	}
	
	protected void executeEvent(SelectionEvent e) {
		CheckInDialog mpd = new CheckInDialog(shell, clubMobilResControl);
		if (mpd.open() == Dialog.OK) {
			executeRestService();
		}

	}

}
